package app.controllers;

import org.springframework.web.servlet.ModelAndView;

public class BaseController {

    private static final String REDIRECT_ACTION = "redirect:";

    protected ModelAndView view(String viewName) {
        return new ModelAndView(viewName);
    }

    protected ModelAndView view(String viewName, String modelName, Object model) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject(modelName, model);
        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView(REDIRECT_ACTION + url);
    }
}