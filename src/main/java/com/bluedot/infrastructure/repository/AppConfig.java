package com.bluedot.infrastructure.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 18:55
 * Spring容器的配置
 */
@Configuration
@ComponentScan(basePackages = {"com.bluedot"})
@EnableJpaRepositories(basePackages = {"com.bluedot"})
@EntityScan(basePackages = {"com.bluedot"})
public class AppConfig {
}