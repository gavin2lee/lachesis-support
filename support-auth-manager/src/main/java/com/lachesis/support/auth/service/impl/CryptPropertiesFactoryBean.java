package com.lachesis.support.auth.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.lachesis.support.common.util.crypt.CryptUtils;
import com.lachesis.support.common.util.exception.CryptException;

public class CryptPropertiesFactoryBean implements FactoryBean<Properties>, BeanFactoryPostProcessor {
	private static final String CRYPT_FEATURED_PROPERTY_SUFFIX = ".crypt";
	private Resource[] locations;
	private String fileEncoding;
	private LocalCryptProperties props = new LocalCryptProperties();

	@Override
	public Properties getObject() throws Exception {
		return props;
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	protected void postPropertiesLoad(){
		props.postLoad();
	}

	protected void loadResources() {
		if (this.locations != null) {
			for (Resource location : this.locations) {
				try {
					PropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, this.fileEncoding));
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	private static class LocalCryptProperties extends Properties {

		private static final long serialVersionUID = 1L;

		public void postLoad() {
			for (String propName : stringPropertyNames()) {
				processPerProperty(propName);
			}
		}

		private void processPerProperty(String propertyName) {
			if (needCrypt(propertyName)) {
				String originalVal = getProperty(propertyName);
				setProperty(propertyName, decrypt(originalVal));
			}
		}

		private String decrypt(String ciphertext) {
			try {
				String result = CryptUtils.decrypt(ciphertext);
				return result;
			} catch (CryptException e) {
				return ciphertext;
			}
		}

		private boolean needCrypt(String propertyName) {
			return propertyName.endsWith(CRYPT_FEATURED_PROPERTY_SUFFIX);
		}
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		loadResources();
		postPropertiesLoad();
	}
}
