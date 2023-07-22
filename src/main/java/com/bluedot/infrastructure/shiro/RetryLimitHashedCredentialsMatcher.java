package com.bluedot.infrastructure.shiro;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jason
 * @creationDate 2023/07/18 - 10:26
 */
@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    /**
     * 密码重试缓存
     */
    private final Ehcache passwordRetryCache;

    private final Ehcache accountFrozen;

    /**
     * 最大登录尝试错误
     */
    private static final int RETRY_MAX_COUNT = 5;
    /**
     * 账户冻结时间，单位：分钟
     */
    private static final int ACCOUNT_FROZEN_TIME = 60;


    public RetryLimitHashedCredentialsMatcher() {
        //初始化缓存
        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        accountFrozen = cacheManager.getCache("accountFrozen");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();

        Element e = accountFrozen.get(username);
        if(e != null){
            LocalDateTime unfreezeTime = (LocalDateTime) e.getObjectValue();
            if(unfreezeTime.isAfter(LocalDateTimeUtil.now())){
                throw new ExcessiveAttemptsException("Account freezing, unfreezing time: "
                        +DateUtil.date(unfreezeTime).toStringDefaultTimeZone());
            }
        }


        Element element = passwordRetryCache.get(username);
        if (element == null) {
            //首次尝试登录
            element = new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }

        //登录次数+1
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        if (retryCount.incrementAndGet() > RETRY_MAX_COUNT) {
            //尝试登录次数大于5次则抛出异常
            Element element1 = new Element(username, LocalDateTimeUtil.offset(LocalDateTimeUtil.now(), ACCOUNT_FROZEN_TIME, ChronoUnit.MINUTES));
            element1.setTimeToLive(ACCOUNT_FROZEN_TIME * 60);
            accountFrozen.put(element1);
            throw new ExcessiveAttemptsException("login fail more than 5 times, account is frozen for one hour");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //登录成功，清空尝试登录次数
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
