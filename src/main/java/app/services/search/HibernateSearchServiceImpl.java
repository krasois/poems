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
        Query luceneQuery = qb
                .bool()
                .must(qb.keyword().fuzzy().onFields("title", "author").matching(query).createQuery())
                .must(qb.range().onField("publishYear").above(year).createQuery())
                .createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Poem.class);

        List<Poem> poemsList = null;
        try {
            poemsList = jpaQuery.getResultList();
        } catch (NoResultException nre) {
            ;
        }

        if (poemsList == null || poemsList.isEmpty()) return new PageImpl<>(new ArrayList<>());

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > poemsList.size() ? poemsList.size() : (start + pageable.getPageSize());
        Page<Poem> pages = new PageImpl<>(poemsList.subList(start, end), pageable, poemsList.size());

        return pages.map(this::makeTableRowModel);
    }
}