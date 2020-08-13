package com.inbiznetcorp.acs.framework.config.mybatis;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.inbiznetcorp.acs.framework.config.mybatis.support.Master;

public abstract class MyBatisConfig {

	public static final String BASE_PACKAGE 				= "com.inbiznetcorp.acs.mapper";
	public static final String CONFIG_OPERATE_LOCATION_PATH 	        = "classpath:mybatis/mybatisConfig.xml";
//	public static final String CONFIG_OPERATE_LOCATION_PATH 	        = "mybatisConfig.xml";
	public static final String CONFIG_AUTHCOMBINE_LOCATION_PATH 		= CONFIG_OPERATE_LOCATION_PATH;

	protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
		PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_OPERATE_LOCATION_PATH));
	}
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = Master.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
class MasterMyBatisConfig extends MyBatisConfig {
	@Bean
	@Primary
	public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
		SqlSessionFactoryBean 				sessionFactoryBean 	= new SqlSessionFactoryBean();

		configureSqlSessionFactory(sessionFactoryBean, masterDataSource);

		return sessionFactoryBean.getObject();
	}
}
