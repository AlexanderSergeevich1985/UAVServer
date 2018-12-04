package com.uavframework.apps.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
public class ExceptionHandlerController {
    private static Logger logger;

    static {
        logger = Logger.getLogger(ExceptionHandlerController.class.getName());
        logger.setLevel(Level.INFO);
    }

    @ExceptionHandler
    public @ResponseBody ResponseEntity<?> handleException(final Exception ex) {
        if(logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, "Exception occurred: ", ex);
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
