package app.services.search;

import app.models.entities.Poem;
import app.models.view.PoemTableRowViewModel;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HibernateSearchServiceImpl implements HibernateSearchService {

    private final EntityManager entityManager;

    @Autowired
    public HibernateSearchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private PoemTableRowViewModel makeTableRowModel(Poem poem) {
        PoemTableRowViewModel model = new PoemTableRowViewModel();
        model.setId(poem.getId());
        model.setTitle(poem.getTitle());
        model.setAuthor(poem.getAuthor());
        model.setPublishYear(poem.getPublishYear());
        return model;
    }

    @Override
    public void initializeHibernateSearch() {

        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public Page<PoemTableRowViewModel> fuzzySearch(String query, Integer year, Pageable pageable) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Poem.class).get();
        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("title", "author")
                .matching(query).createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Poem.class);

        List<Poem> poemsList = null;
        try {
            poemsList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            ;
        }

        if (poemsList == null) return new PageImpl<>(new ArrayList<>());

        List<Poem> filtered = poemsList.stream().filter(p -> p.getPublishYear() > year).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > filtered.size() ? filtered.size() : (start + pageable.getPageSize());
        Page<Poem> pages = new PageImpl<>(filtered.subList(start, end), pageable, filtered.size());

        return pages.map(this::makeTableRowModel);
    }
}