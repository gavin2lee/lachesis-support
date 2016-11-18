package com.lachesis.support.auth.demo.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class LocalShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roleNames = new HashSet<String>();
	    Set<String> permissions = new HashSet<String>();
	    roleNames.add("admin");
	    roleNames.add("zhangsan");
	    permissions.add("user.do?myjsp");
	    permissions.add("login.do?main");
	    permissions.add("login.do?logout");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
	    info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		//System.out.println(authcToken.getUsername());
		//User user = userService.findUserByLoginName(authcToken.getUsername());
		//System.out.println(user);
		//if (user != null) {
			return new SimpleAuthenticationInfo("283", "123", getName());
		//}else{
		//	throw new AuthenticationException();
		//}
	}

}
