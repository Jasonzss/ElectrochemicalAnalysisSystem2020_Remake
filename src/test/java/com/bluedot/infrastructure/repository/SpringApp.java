package com.bluedot.infrastructure.repository;

import com.bluedot.infrastructure.config.SpringConfig;
import com.bluedot.infrastructure.config.PersistenceJPAConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author Jason
 * @creationDate 2023/07/05 - 18:52
 */
@ComponentScan(basePackageClasses = {SpringConfig.class, PersistenceJPAConfig.class})
//@Configuration
public class SpringApp  extends ResourceConfig {

}