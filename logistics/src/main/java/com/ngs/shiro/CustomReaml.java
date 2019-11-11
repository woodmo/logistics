package com.ngs.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;
import com.ngs.pojo.UserExample.Criteria;
import com.ngs.service.PermissionService;
import com.ngs.service.UserService;

public class CustomReaml extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionServicevice;
	// 进行认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<User> selectByExample = userService.selectByExample(example);
		if (selectByExample != null) {
			String password = selectByExample.get(0).getPassword();
			String salt = selectByExample.get(0).getSalt();
			ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(selectByExample.get(0), password,
					credentialsSalt, this.getName());
			return authenticationInfo;
		}
		return null;
	}

//	进行授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("进来授权");
		User user = (User) principals.getPrimaryPrincipal();
//		得到权限的字符串
		String permissionIds = user.getPermissionIds();
//		可能有什么都没有的情况
		if(permissionIds==null) {
			return null;
		}
//		因为是一串，的字符串，所以要进行分割

		String[] split = permissionIds.split(",");
//		存在long类型的集合中
		List<Long> permissionIdList=new ArrayList<Long>();
		for (String permissionId : split) {
			permissionIdList.add(Long.valueOf(permissionId));
		}
		List<String> permissionExpression=permissionServicevice.selectPermissionByIds(permissionIdList);
//		进行授权,就是将权限放到shiro信息里面而已，授权是交给shiro自己弄的，自己判断有没有这个
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissionExpression);
		return simpleAuthorizationInfo;
	}

}
