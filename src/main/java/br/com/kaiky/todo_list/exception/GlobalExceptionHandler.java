package br.com.kaiky.todo_list.exception;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Logger
    private static final Logger log = 
    LoggerFactory.getLogger(
        GlobalExceptionHandler.class
    );

    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(
        ResourceNotFoundException exception,
        HttpServletRequest request
    ) {
        log.error(
            "Unexpected error while processing {}",
            request.getRequestURI(),
            exception
        );

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, 
            exception.getMessage()
        );

        problem.setTitle("Resource not Found");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    //500
    public ProblemDetail handleExcetion(
        Exception exception,
        HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "An unexpected error occurred."
        );

        problem.setTitle("Internal Server Error");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }
}
