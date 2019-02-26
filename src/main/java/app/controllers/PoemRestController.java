package app.controllers;

import app.services.caching.YearCacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoemRestController {

    private final YearCacher yearCacher;

    @Autowired
    public PoemRestController(YearCacher yearCacher) {
        this.yearCacher = yearCacher;
    }

    @ResponseBody
    @GetMapping("/poems/years")
    public List<Integer> years() {
        return this.yearCacher.getYears();
    }
}