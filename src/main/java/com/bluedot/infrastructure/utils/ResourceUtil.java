package com.bluedot.infrastructure.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.resource.ResourceException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @since 2023/07/30 - 23:10
 */
public class ResourceUtil {
    public static <T, ID> T updateResource(T origin, ID id, JpaRepository<T, ID> repository){
        T t = repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));

        BeanUtil.copyProperties(origin, t, CopyOptions.create(origin.getClass(), true));

        return repository.save(t);
    }
}
