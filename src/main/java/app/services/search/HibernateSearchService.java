package app.services.search;

import app.models.view.PoemTableRowViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HibernateSearchService {

    void initializeHibernateSearch();

    Page<PoemTableRowViewModel> fuzzySearch(String query, Integer year, Pageable pageable);
}