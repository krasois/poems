package app.repositories;

import app.models.entities.Poem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoemRepository extends PagingAndSortingRepository<Poem, String> {

    @Query("SELECT p FROM Poem AS p ORDER BY p.author ASC")
    List<Poem> getAllOrdered(Pageable pageable);

    @Query("SELECT p FROM Poem AS p WHERE (p.author LIKE %?1% OR p.title LIKE %?2%) AND p.publishYear > ?3 ORDER BY p.author ASC")
    List<Poem> searchTitleAndAuthorByQueryAndByYear(String author, String title, Integer year, Pageable pageable);
}