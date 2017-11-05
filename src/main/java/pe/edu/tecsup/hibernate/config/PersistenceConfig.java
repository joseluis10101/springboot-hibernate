package pe.edu.tecsup.hibernate.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "persistence")
public class PersistenceConfig {

    @Value("${driver}")
    String driver;

    @Value("${jdbcUrl}")
    String jdbcUrl;

    @Value("${userdb}")
    String username;

    @Value("${password}")
    String password;

    @Value("${model}")
    String model;

    @Value("${showSql}")
    String showSql;

    @Value("${dialect}")
    String dialect;

    @Value("${minPool}")
    int minPool;

    @Value("${maxPool}")
    int maxPool;

    @Value("${maxIddleTime}")
    int maxIddleTime;

    @Value("${acquireIncrement}")
    int acquireIncrement;

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {

        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass(this.driver);
        ds.setJdbcUrl(this.jdbcUrl);
        ds.setUser(this.username);
        ds.setPassword(this.password);
        ds.setAcquireIncrement(acquireIncrement);
        ds.setMinPoolSize(minPool);
        ds.setMaxPoolSize(maxPool);
        ds.setMaxIdleTime(maxIddleTime);

        return ds;
    }

    @Bean
    public LocalSessionFactoryBean factoryBean(ComboPooledDataSource ds) {

        LocalSessionFactoryBean fb = new LocalSessionFactoryBean();
        fb.setDataSource(ds);
        fb.setPackagesToScan(this.model);

        Properties prop = new Properties();
        prop.setProperty("hibernate.dialect", this.dialect);
        prop.setProperty("hibernate.show_sql", this.showSql);
        prop.setProperty("hibernate.connection.release_mode", "after_transaction");
        prop.setProperty("hibernate.connection.useUnicode", "true");
        prop.setProperty("hibernate.connection.charSet", "UTF8");
        prop.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        
        fb.setHibernateProperties(prop);

        return fb;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager transManager = new HibernateTransactionManager();
        transManager.setSessionFactory(sf);
        return transManager;
    }

}
