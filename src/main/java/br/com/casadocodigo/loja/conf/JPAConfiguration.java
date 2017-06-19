package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factoryBean = 
				new LocalContainerEntityManagerFactoryBean();
		
		factoryBean.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
		
		factoryBean.setDataSource( this.getDataSource() );
		
		factoryBean.setJpaProperties( this.getHibernateProperties() );
		
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		
		return factoryBean;
	}
	
	private DriverManagerDataSource getDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("hermes");
        dataSource.setPassword("");
        dataSource.setUrl("jdbc:mysql://10.0.0.28:3306/casadocodigo");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}
	
	private Properties getHibernateProperties() {
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
    }
	
	@Bean
	public JpaTransactionManager transactionManager( EntityManagerFactory emf ) {
		return new JpaTransactionManager( emf );
	}
}
