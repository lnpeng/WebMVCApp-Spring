package microblogger.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(DuplicateBlogException.class)
    public String handleNotFound() {
        return "error/duplicate";
    }

}
