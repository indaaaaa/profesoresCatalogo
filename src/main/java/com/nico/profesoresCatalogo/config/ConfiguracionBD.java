package com.nico.profesoresCatalogo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ConfiguracionBD {

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("com.nico.profesoresCatalogo.model");
		sessionFactoryBean.setHibernateProperties(propiedadesDeHibernate());

		return sessionFactoryBean;

	}

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/catalogocursos");
		dataSource.setUsername("root");
		dataSource.setPassword("3276327632");

		return dataSource;

	}

	public Properties propiedadesDeHibernate() {
		Properties propiedades = new Properties();
		propiedades.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		propiedades.put("show_sql", "true");

		return propiedades;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager() {

		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());

		return transactionManager;

	}

}
