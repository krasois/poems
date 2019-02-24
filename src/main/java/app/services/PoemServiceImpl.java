package app.services;

import app.models.binding.PoemBindingModel;
import app.models.entities.Poem;
import app.repositories.PoemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Poem> getAll(Pageable pageable) {
        return this.poemRepository.getAllOrdered(pageable);
    }

    @Override
    public List<Poem> search(String query, Integer year, Pageable pageable) {
        return this.poemRepository.searchTitleAndAuthorByQueryAndByYear(query, query, year, pageable);
    }
}