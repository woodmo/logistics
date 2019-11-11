package com.ngs.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.ngs.pojo.User;

public class MyAuthenticationFilter extends FormAuthenticationFilter {
//	���ύʱ������֤�����֤
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
//		ȡ�ñ��� ����
		String myVerifyCode =req.getParameter("verifyCode");
//		�����֤�����session�еĶ���
		HttpSession session = req.getSession();
		String seVerifyCode = (String) session.getAttribute("rand");
//		�����������жԱȣ���ͬ�ͽ�������ģ���Ȼ��ֱ�������˳�ȥ��ע�⣬���жԱȵ�ʱ�����ִ�Сд
//		����Ҫ�Ƚ����ж��Ƿ��ǿյģ���Ҫ���ǵ���ʱ��Ҳ������ж�
		if(StringUtils.isNotBlank(myVerifyCode)&&StringUtils.isNotBlank(seVerifyCode)) {
			if(!myVerifyCode.equalsIgnoreCase(seVerifyCode)) {
				req.setAttribute("errorInfo", "���������֤�벻��ȷ�����������룡");
				req.getRequestDispatcher("/login.jsp").forward(req, response);
				return false;
			}
		}
		return super.onAccessDenied(request, response);
	}

//	�����¼ʱ����¼���Ǹ�Ŀ¼
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.saveRequest(request);
		return super.onLoginSuccess(token, subject, request, response);
	}
//	�����д��ԭ���ǣ���ס��֮��ֻ��һ��indexҳ���ܼ�ס��������ҳ��ò���cookie
	@Override
		protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//�������л�ȡShiro�� ����
				Subject subject = getSubject(request, response);
				//�������л�ȡShiro��ܵ�Session
				Session session = subject.getSession();
				//�������û����֤��Session����֤������ �����Ѿ����ü�ס����
				if (!subject.isAuthenticated() && subject.isRemembered()) {
					//��ȡ�������ݣ��Ӽ�ס�ҵ�Cookie�л�ȡ�ģ�
					User principal = (User) subject.getPrincipal();
					//�������֤��Ϣ���� Session��
					session.setAttribute("user", principal);
				}
				return subject.isAuthenticated() || subject.isRemembered();
			}

}
