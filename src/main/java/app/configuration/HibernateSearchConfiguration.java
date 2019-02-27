package app.configuration;

import app.services.search.HibernateSearchService;
import app.services.search.HibernateSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@EnableAutoConfiguration
@Configuration
public class HibernateSearchConfiguration {

    private final EntityManager entityManager;

    @Autowired
    public HibernateSearchConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    @Transactional
    public HibernateSearchService hibernateSearchService() {
        HibernateSearchService hibernateSearchService = new HibernateSearchServiceImpl(this.entityManager);
        hibernateSearchService.initializeHibernateSearch();
        return hibernateSearchService;
    }
}
