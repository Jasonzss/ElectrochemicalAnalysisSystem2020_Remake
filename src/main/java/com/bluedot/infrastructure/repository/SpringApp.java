package com.bluedot.infrastructure.repository;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import com.bluedot.domain.rbac.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/05 - 18:52
 */
@ComponentScan(basePackageClasses = {AppConfig.class,PersistenceJPAConfig.class})
public class SpringApp {}