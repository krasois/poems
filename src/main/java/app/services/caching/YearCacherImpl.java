package app.services.caching;

import app.services.poem.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YearCacherImpl implements YearCacher {

    private final PoemService poemService;

    @Autowired
    public YearCacherImpl(PoemService poemService) {
        this.poemService = poemService;
    }

    @Override
    @Cacheable("years")
    public List<Integer> getYears() {
        return this.poemService.getDistinctYears();
    }

    @Override
    @CacheEvict("years")
    public void clearAll() {
    }
}