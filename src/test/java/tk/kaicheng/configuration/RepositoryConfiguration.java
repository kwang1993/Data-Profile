package tk.kaicheng.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"tk.kaicheng.models"})
@EnableJpaRepositories(basePackages = {"tk.kaicheng.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
