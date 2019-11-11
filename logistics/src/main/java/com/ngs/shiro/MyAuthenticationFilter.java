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
//	表单提交时进行验证码的验证
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
//		取得表单的 东西
		String myVerifyCode =req.getParameter("verifyCode");
//		获得验证码存在session中的东西
		HttpSession session = req.getSession();
		String seVerifyCode = (String) session.getAttribute("rand");
//		把这两个进行对比，相同就接着下面的，不然就直接让它退出去，注意，进行对比的时候不区分大小写
//		首先要先进行判断是否是空的，不要当是当的时候，也会记性判断
		if(StringUtils.isNotBlank(myVerifyCode)&&StringUtils.isNotBlank(seVerifyCode)) {
			if(!myVerifyCode.equalsIgnoreCase(seVerifyCode)) {
				req.setAttribute("errorInfo", "您输入的验证码不正确，请重新输入！");
				req.getRequestDispatcher("/login.jsp").forward(req, response);
				return false;
			}
		}
		return super.onAccessDenied(request, response);
	}

//	清楚登录时，记录的那个目录
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		WebUtils.saveRequest(request);
		return super.onLoginSuccess(token, subject, request, response);
	}
//	这个重写的原因是，记住我之后，只有一个index页面能记住，其他的页面得不到cookie
	@Override
		protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//从请求中获取Shiro的 主体
				Subject subject = getSubject(request, response);
				//从主体中获取Shiro框架的Session
				Session session = subject.getSession();
				//如果主体没有认证（Session中认证）并且 主体已经设置记住我了
				if (!subject.isAuthenticated() && subject.isRemembered()) {
					//获取主体的身份（从记住我的Cookie中获取的）
					User principal = (User) subject.getPrincipal();
					//将身份认证信息共享到 Session中
					session.setAttribute("user", principal);
				}
				return subject.isAuthenticated() || subject.isRemembered();
			}

}
