package com.hrada.oms.util.shiro;

import com.hrada.oms.model.common.User;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *  不退出直接登录，如果用户名不一样则先退出当前用户
 * Created by shin on 2017/9/18.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                //本次用户登陆账号
                String account = this.getUsername(request);
                //之前登陆的用户
                Subject subject = this.getSubject(request, response);
                User user = (User) subject.getPrincipal();
                //如果两次登陆的用户不一样，则先退出之前登陆的用户
                if (account != null && user != null && !account.equals(user.getUname())) {
                    subject.logout();
                }
            }
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }
}
