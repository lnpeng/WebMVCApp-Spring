package microblogger.db.hibernate;

import microblogger.db.BlogRepository;
import microblogger.db.BloggerRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateRepositoryTestConfig {

    @Inject
    private SessionFactory sessionFactory;

    @Bean
    public DataSource dataSource() {
       return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("microblogger/db/schema.sql", "microblogger/db/test-data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean
    public SessionFactory sessionFactoryBean(DataSource dataSource) {
        try {
            LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
            lsfb.setDataSource(dataSource);
            lsfb.setPackagesToScan("microblogger.domain");
            Properties props = new Properties();
            props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
            lsfb.setHibernateProperties(props);
            lsfb.afterPropertiesSet();
            SessionFactory object = lsfb.getObject();
            return object;
        } catch (IOException e) {
            return null;
        }
    }

    @Bean
    public BloggerRepository bloggerRepository(SessionFactory sessionFactory) {
        return new HibernateBloggerRepository(sessionFactory);
    }

    @Bean
    public BlogRepository blogRepository(SessionFactory sessionFactory) {
        return new HibernateBlogRepository(sessionFactory);
    }
}
