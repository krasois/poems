package app.services;

import app.models.binding.PoemBindingModel;
import app.models.entities.Poem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PoemService {
    void add(PoemBindingModel poemBindingModel);

    List<Poem> getAll(Pageable pageable);

    List<Poem> search(String query, Integer year, Pageable pageable);
}
