package com.lachesis.support.auth.cache;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.auth.model.Token;

@Component
public class TokenSynchronizerManager implements InitializingBean {
	private static final Logger LOG = LoggerFactory.getLogger(TokenSynchronizerManager.class);

	@Autowired
	@Qualifier("tokenTaskExecutor")
	private TaskExecutor taskExecutor;
	
	@Autowired
	private TokenQueueBroker tokenQueueBroker;
	
	@Autowired
	private TokenService tokenService;
	private int putSynchronizerNum = 1;
	private int updateSynchronizerNum = 1;
	private int removeSynchronizerNum = 1;
	private int evictSynchronizerNum = 1;
	private int expireSynchronizerNum = 1;

	private List<TokenSynchronizer> putSynchronizers = new LinkedList<TokenSynchronizer>();
	private List<TokenSynchronizer> updateSynchronizers = new LinkedList<TokenSynchronizer>();
	private List<TokenSynchronizer> removeSynchronizers = new LinkedList<TokenSynchronizer>();
	private List<TokenSynchronizer> evictSynchronizers = new LinkedList<TokenSynchronizer>();
	private List<TokenSynchronizer> expireSynchronizers = new LinkedList<TokenSynchronizer>();

	public void start() {
		if (LOG.isDebugEnabled()) {
			LOG.debug(TokenSynchronizerManager.class.getSimpleName() + " start");
		}

		doStart();
	}

	protected void doStart() {
		List<TokenSynchronizer> allSynchronizers = new LinkedList<TokenSynchronizer>();
		allSynchronizers.addAll(putSynchronizers);
		allSynchronizers.addAll(updateSynchronizers);
		allSynchronizers.addAll(removeSynchronizers);
		allSynchronizers.addAll(evictSynchronizers);
		allSynchronizers.addAll(expireSynchronizers);

		for (TokenSynchronizer ts : allSynchronizers) {
			taskExecutor.execute(ts);
		}
		
		if(LOG.isInfoEnabled()){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			int i = 0;
			for(TokenSynchronizer ts:allSynchronizers){
				if(i > 0){
					sb.append(",");
				}
				sb.append(ts.getClass().getSimpleName());
				i++;
			}
			sb.append("]");
			
			LOG.info(String.format("start TokenSynchronizers :%s", sb.toString()));
		}
	}

	protected void init() {
		for (int i = 0; i < putSynchronizerNum; i++) {
			putSynchronizers.add(new PutTokenSynchronizer(tokenQueueBroker, tokenService));
		}

		for (int i = 0; i < updateSynchronizerNum; i++) {
			updateSynchronizers.add(new UpdateTokenSynchronizer(tokenQueueBroker, tokenService));
		}

		for (int i = 0; i < removeSynchronizerNum; i++) {
			removeSynchronizers.add(new RemoveTokenSynchronizer(tokenQueueBroker, tokenService));
		}

		for (int i = 0; i < evictSynchronizerNum; i++) {
			evictSynchronizers.add(new EvictTokenSynchronizer(tokenQueueBroker, tokenService));
		}

		for (int i = 0; i < expireSynchronizerNum; i++) {
			expireSynchronizers.add(new ExpireTokenSynchronizer(tokenQueueBroker, tokenService));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		start();
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setPutSynchronizerNum(int putSynchronizerNum) {
		this.putSynchronizerNum = putSynchronizerNum;
	}

	public void setUpdateSynchronizerNum(int updateSynchronizerNum) {
		this.updateSynchronizerNum = updateSynchronizerNum;
	}

	public void setRemoveSynchronizerNum(int removeSynchronizerNum) {
		this.removeSynchronizerNum = removeSynchronizerNum;
	}

	public void setEvictSynchronizerNum(int evictSynchronizerNum) {
		this.evictSynchronizerNum = evictSynchronizerNum;
	}

	public void setExpireSynchronizerNum(int expireSynchronizerNum) {
		this.expireSynchronizerNum = expireSynchronizerNum;
	}

	public void setTokenQueueBroker(TokenQueueBroker tokenQueueBroker) {
		this.tokenQueueBroker = tokenQueueBroker;
	}

	static class PutTokenSynchronizer extends AbstractTokenSynchronizer {

		public PutTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
			super(tokenQueueBroker, tokenService);
		}

		@Override
		protected void processWithTokenService(List<Token> tokensCopy) {
			getTokenService().addTokens(tokensCopy);
		}

		@Override
		protected Token takeToken() {
			return getTokenQueueBroker().takePutToken();
		}
	}

	static class UpdateTokenSynchronizer extends AbstractTokenSynchronizer {

		public UpdateTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
			super(tokenQueueBroker, tokenService);
		}

		protected void processWithTokenService(List<Token> tokensCopy) {
			getTokenService().updateLastModifiedTokens(tokensCopy);
		}

		protected Token takeToken() {
			Token t = getTokenQueueBroker().takeUpdateToken();
			if (t != null) {
				Token tokenToUpdate = new Token();
				tokenToUpdate.setTokenValue(t.getTokenValue());
				tokenToUpdate.setLastModified(t.getLastModified());

				return tokenToUpdate;
			}

			return null;
		}

	}

	static class RemoveTokenSynchronizer extends AbstractTokenSynchronizer {

		public RemoveTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
			super(tokenQueueBroker, tokenService);
		}

		@Override
		protected void processWithTokenService(List<Token> tokensCopy) {
			getTokenService().removeTokens(tokensCopy);

		}

		@Override
		protected Token takeToken() {
			return getTokenQueueBroker().takeRemoveToken();
		}

	}

	static class EvictTokenSynchronizer extends AbstractTokenSynchronizer {

		public EvictTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
			super(tokenQueueBroker, tokenService);
		}

		@Override
		protected void processWithTokenService(List<Token> tokensCopy) {
			getTokenService().removeTokens(tokensCopy);

		}

		@Override
		protected Token takeToken() {
			return getTokenQueueBroker().takeEvictToken();
		}

	}

	static class ExpireTokenSynchronizer extends AbstractTokenSynchronizer {

		public ExpireTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
			super(tokenQueueBroker, tokenService);
		}

		@Override
		protected void processWithTokenService(List<Token> tokensCopy) {
			getTokenService().removeTokens(tokensCopy);
		}

		@Override
		protected Token takeToken() {
			return getTokenQueueBroker().takeExpireToken();
		}
	}

}
