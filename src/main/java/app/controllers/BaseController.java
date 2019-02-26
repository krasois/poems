package app.controllers;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class BaseController {

    private static final String REDIRECT_ACTION = "redirect:";
    private static final String BASE_LAYOUT_NAME = "base-layout";
    private static final String VIEW_NAME = "view";

    private ModelAndView setBase(ModelAndView modelAndView) {
        modelAndView.setViewName(BASE_LAYOUT_NAME);
        return modelAndView;
    }

    protected ModelAndView view(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(VIEW_NAME, viewName);
        return this.setBase(modelAndView);
    }

    protected ModelAndView view(String viewName, String modelName, Object model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(VIEW_NAME, viewName);
        modelAndView.addObject(modelName, model);
        return this.setBase(modelAndView);
    }

    protected ModelAndView view(String viewName, Map<String, ?> models) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(VIEW_NAME, viewName);
        modelAndView.addAllObjects(models);
        return this.setBase(modelAndView);
    }

    protected ModelAndView redirect(String url) {
        return new ModelAndView(REDIRECT_ACTION + url);
    }
}