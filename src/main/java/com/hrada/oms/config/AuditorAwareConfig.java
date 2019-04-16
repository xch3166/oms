package com.hrada.oms.config;

import com.hrada.oms.model.common.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * Created by shin on 2018/12/7.
 */
@Configuration
public class AuditorAwareConfig implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user!=null) {
            return user.getId();
        } else {
            return null;
        }
    }
}
