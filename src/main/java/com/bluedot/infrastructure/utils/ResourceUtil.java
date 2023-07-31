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
    /**
     * 把更新实体的逻辑抽象出来的业务方法，称为Util可能不太好，但问题不大
     * @param origin 源对象，即更新后字段的来源
     * @param id 被修改对象的id
     * @param repository 被修改对象对应的JpaRepository
     * @param <T> 被修改对象的泛型
     * @param <ID> 被修改对象id的泛型
     * @return 修改后的对象
     */
    public static <T, ID> T updateResource(T origin, ID id, JpaRepository<T, ID> repository){
        T t = repository.findById(id).orElseThrow(new ResourceException(CommonErrorCode.E_7001));

        BeanUtil.copyProperties(origin, t, CopyOptions.create(origin.getClass(), true));

        return repository.save(t);
    }
}
