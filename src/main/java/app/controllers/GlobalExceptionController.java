package app.controllers;

import app.exceptions.PoemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PoemNotFoundException.class)
    public ModelAndView poemNotFound(PoemNotFoundException e) {
        return this.view("poem-not-found", "errorMessage", e.getMessage());
    }
}