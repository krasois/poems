package app.controllers;

import app.models.entities.Poem;
import app.services.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController extends BaseController {

    private final PoemService poemService;

    @Autowired
    public HomeController(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/")
    public ModelAndView index(@PageableDefault(size = 2) Pageable pageable) {
        List<Poem> poems = this.poemService.getAll(pageable);
        return this.view("index", "poems", poems);
    }
}