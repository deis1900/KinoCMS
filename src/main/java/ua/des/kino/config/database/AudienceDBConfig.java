package ua.des.kino.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "audienceEntityManagerFactory",
        transactionManagerRef = "audienceTransactionManager",
        basePackages = {"ua.des.kino.repository.audience"})
public class AudienceDBConfig {

    @Bean(name = "audienceDataSourceProperties")
    @ConfigurationProperties("spring.datasource.audience")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "audienceDataSource")
    public HikariDataSource dataSource(@Qualifier("audienceDataSourceProperties") DataSourceProperties properties) {
        HikariDataSource hikariDataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();

        Resource initSchema = new ClassPathResource("/schema-audience.sql");
        Resource initData = new ClassPathResource("/data-audience.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, hikariDataSource);
        return hikariDataSource;
    }

    @Bean(name = "audienceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("audienceDataSource") DataSource dataSource) {

        return builder.dataSource(dataSource).packages("ua.des.kino.model.audience").persistenceUnit("audience")
                .build();
    }

    @Bean(name = "audienceTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("audienceEntityManagerFactory") EntityManagerFactory postgresEntityManagerFactory) {
        return new JpaTransactionManager(postgresEntityManagerFactory);
    }

}
