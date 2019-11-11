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
	// ������֤
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

//	������Ȩ
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("������Ȩ");
		User user = (User) principals.getPrimaryPrincipal();
//		�õ�Ȩ�޵��ַ���
		String permissionIds = user.getPermissionIds();
//		������ʲô��û�е����
		if(permissionIds==null) {
			return null;
		}
//		��Ϊ��һ�������ַ���������Ҫ���зָ�

		String[] split = permissionIds.split(",");
//		����long���͵ļ�����
		List<Long> permissionIdList=new ArrayList<Long>();
		for (String permissionId : split) {
			permissionIdList.add(Long.valueOf(permissionId));
		}
		List<String> permissionExpression=permissionServicevice.selectPermissionByIds(permissionIdList);
//		������Ȩ,���ǽ�Ȩ�޷ŵ�shiro��Ϣ������ѣ���Ȩ�ǽ���shiro�Լ�Ū�ģ��Լ��ж���û�����
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissionExpression);
		return simpleAuthorizationInfo;
	}

}
