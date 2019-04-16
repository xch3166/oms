package com.hrada.oms.service;

import com.hrada.oms.model.log.GDN;

/**
 * Created by shin on 2018/11/24.
 */
public interface GDNService {

    GDN save(GDN gdn);

    void pass(Long id);

    void refuse(Long id);

    void reSub(Long id);
}
