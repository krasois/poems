package app.services.poem;

import app.exceptions.PoemNotFoundException;
import app.models.binding.PoemBindingModel;
import app.models.entities.Poem;
import app.models.view.PoemTableRowViewModel;
import app.models.view.PoemViewModel;
import app.repositories.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoemServiceImpl implements PoemService {

    private final PoemRepository poemRepository;

    @Autowired
    public PoemServiceImpl(PoemRepository poemRepository) {
        this.poemRepository = poemRepository;
    }

    private PoemTableRowViewModel makeTableRowModel(Poem poem) {
        PoemTableRowViewModel model = new PoemTableRowViewModel();
        model.setId(poem.getId());
        model.setAuthor(poem.getAuthor());
        model.setPublishYear(poem.getPublishYear());
        model.setTitle(poem.getTitle());
        return model;
    }

    private PoemViewModel makeModel(Poem poem) {
        PoemViewModel model = new PoemViewModel();
        model.setAuthor(poem.getAuthor());
        model.setDateAdded(poem.getDateAdded());
        model.setPoemContent(poem.getPoemContent());
        model.setTitle(poem.getTitle());
        model.setPublishYear(poem.getPublishYear());
        return model;
    }

    @Override
    public void add(PoemBindingModel poemBindingModel) {
        Poem poem = new Poem();
        poem.setTitle(poemBindingModel.getTitle());
        poem.setAuthor(poemBindingModel.getAuthor());
        poem.setPoemContent(poemBindingModel.getPoemContent());
        poem.setPublishYear(poemBindingModel.getPublishYear());

        this.poemRepository.save(poem);
    }

    @Override
    public Page<PoemTableRowViewModel> getAll(Pageable pageable) {
        Page<Poem> poems = this.poemRepository.getAllOrdered(pageable);
        return poems.map(this::makeTableRowModel);
    }

    @Override
    public Page<PoemTableRowViewModel> search(String query, Integer year, Pageable pageable) {
        Page<Poem> poems = this.poemRepository.searchTitleAndAuthorByQueryAndByYear(query, query, year, pageable);
        return poems.map(this::makeTableRowModel);
    }

    @Override
    public PoemViewModel getPoemById(String id) throws PoemNotFoundException {
        Poem poem = this.poemRepository.findById(id).orElse(null);
        if (poem == null) throw new PoemNotFoundException();
        return this.makeModel(poem);
    }

    @Override
    public List<Integer> getDistinctYears() {
        return this.poemRepository.getDistinctYears();
    }
}