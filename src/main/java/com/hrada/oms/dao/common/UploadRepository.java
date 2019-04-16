package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/11/16.
 */
public interface UploadRepository extends JpaRepository<Upload, Long> {
}
