package app.controllers;

import app.models.binding.PoemBindingModel;
import app.models.entities.Poem;
import app.services.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller("/poem")
public class PoemController extends BaseController {

    private final PoemService poemService;

    @Autowired
    public PoemController(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return this.view("add");
    }

    @PostMapping("/add")
    public ModelAndView add(@Valid PoemBindingModel poemBindingModel) {
        this.poemService.add(poemBindingModel);
        return this.redirect("/");
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("query") String query, @RequestParam(value = "year", defaultValue = "0") Integer year, @PageableDefault(size = 2) Pageable pageable) {
        List<Poem> poems = this.poemService.search(query, year, pageable);
        return this.view("search", "poems", poems);
    }
}