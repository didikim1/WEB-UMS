package com.inbiznetcorp.acs.framework.config.mybatis;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.inbiznetcorp.acs.framework.config.mybatis.properties.DatabaseProperties;
import com.inbiznetcorp.acs.framework.config.mybatis.properties.MasterDatabaseProperties;


public abstract class DatabaseConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public abstract DataSource dataSource();

    protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource, DatabaseProperties databaseProperties) {
    	LOGGER.info("configureDataSource = {}", databaseProperties);
    	dataSource.setDriverClassName(databaseProperties.getDriverClassName());
    	dataSource.setUrl(databaseProperties.getUrl());
    	dataSource.setUsername(databaseProperties.getUserName());
    	dataSource.setPassword(databaseProperties.getPassword());
        dataSource.setMaxActive(databaseProperties.getMaxActive());
        dataSource.setMaxIdle(databaseProperties.getMaxIdle());
        dataSource.setMinIdle(databaseProperties.getMinIdle());
        dataSource.setMaxWait(databaseProperties.getMaxWait());
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
    }
}

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MasterDatabaseProperties.class)
class MasterDatabaseConfig extends DatabaseConfig {

	@Autowired
	private MasterDatabaseProperties masterDatabaseProperties;

	@Bean(name = "masterDataSource", destroyMethod = "close")
	@Primary
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource masterDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		configureDataSource(masterDataSource, masterDatabaseProperties);
		return masterDataSource;
	}

	@Bean
    public PlatformTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(masterDataSource);
		transactionManager.setGlobalRollbackOnParticipationFailure(false);
		return transactionManager;
    }
}
