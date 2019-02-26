package app.services.poem;

import app.exceptions.PoemNotFoundException;
import app.models.binding.PoemBindingModel;
import app.models.view.PoemTableRowViewModel;
import app.models.view.PoemViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PoemService {
    void add(PoemBindingModel poemBindingModel);

    Page<PoemTableRowViewModel> getAll(Pageable pageable);

    Page<PoemTableRowViewModel> search(String query, Integer year, Pageable pageable);

    PoemViewModel getPoemById(String id) throws PoemNotFoundException;

    List<Integer> getDistinctYears();
}