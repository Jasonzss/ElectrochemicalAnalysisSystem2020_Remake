package com.bluedot.infrastructure.repository;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author Jason
 * @creationDate 2023/07/05 - 18:52
 */
@ComponentScan(basePackageClasses = {AppConfig.class,PersistenceJPAConfig.class})
//@Configuration
public class SpringApp  extends ResourceConfig {

}