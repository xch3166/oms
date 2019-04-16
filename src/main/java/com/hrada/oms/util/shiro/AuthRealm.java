package com.hrada.oms.util.shiro;

import com.hrada.oms.model.common.Permission;
import com.hrada.oms.model.common.Role;
import com.hrada.oms.model.common.User;
import com.hrada.oms.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shin on 2018/4/27.
 */
public class AuthRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user  = (User)principals.getPrimaryPrincipal();
            for (Role role : user.getRoleList( )) {
                if (role != null && role.getPermissionList() != null) {
                    authorizationInfo.addRole(role.getName( ));
                    for (Permission permission : role.getPermissionList( )) {
                        authorizationInfo.addStringPermission(permission.getName( ));
                    }
                }
            }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String uname = (String)token.getPrincipal();
        User user = userService.findByUname(uname);
        if(user == null){
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getUpass(),
                getName()
        );
        return authenticationInfo;
    }
}
