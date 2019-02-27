package app.controllers;

import app.exceptions.PoemNotFoundException;
import app.models.binding.PoemBindingModel;
import app.models.view.PoemTableRowViewModel;
import app.models.view.PoemViewModel;
import app.services.caching.YearCacher;
import app.services.poem.PoemService;
import app.services.search.HibernateSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/poem")
public class PoemController extends BaseController {

    private final PoemService poemService;

    private final YearCacher yearCacher;

    private final HibernateSearchService hibernateSearchService;

    @Autowired
    public PoemController(PoemService poemService, YearCacher yearCacher, HibernateSearchService hibernateSearchService) {
        this.poemService = poemService;
        this.yearCacher = yearCacher;
        this.hibernateSearchService = hibernateSearchService;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute("poem") PoemBindingModel poemBindingModel) {
        return this.view("add");
    }

    @PostMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("poem") PoemBindingModel poemBindingModel, BindingResult result) {
        if (result.hasErrors()) return this.view("add");
        this.poemService.add(poemBindingModel);
        this.yearCacher.clearAll();
        return this.redirect("/");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("query") String query, @RequestParam(value = "year", defaultValue = "0") Integer year, @PageableDefault(size = 2) Pageable pageable) {
        Page<PoemTableRowViewModel> poems;
        if (query.contains(" ")) {
            poems = this.hibernateSearchService.fuzzySearch(query, year, pageable);
        } else {
            poems = this.poemService.search(query, year, pageable);
        }

        Map<String, ?> models = new HashMap<>() {{
            put("poems", poems);
            put("query", query);
            put("year", year);
        }};

        return this.view("search", models);
    }

    @GetMapping("/details/{poemId}")
    public ModelAndView details(@PathVariable String poemId) throws PoemNotFoundException {
        PoemViewModel poem = this.poemService.getPoemById(poemId);
        return this.view("details", "poem", poem);
    }
}