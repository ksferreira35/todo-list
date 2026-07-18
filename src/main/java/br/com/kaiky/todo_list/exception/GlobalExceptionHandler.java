package br.com.kaiky.todo_list.exception;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.openai.errors.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Logger
    private static final Logger log = 
    LoggerFactory.getLogger(
        GlobalExceptionHandler.class
    );

    //400 - DTOs Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors =exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null
                                        ? error.getDefaultMessage()
                                        : "Invalid value",
                        (first, second) -> first
                ));

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "One or more fields are invalid."
        );

        problem.setTitle("Validation Error");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("errors", errors);

        return problem;
    }

    //400 - Invalid Parameter
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch (
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid value for parameter: " + exception.getName()
        );

        problem.setTitle("Invalid value for parameter: ");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    //400 - Invalid Json
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableMessage(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "The request body is invalid or malformed."
        );

        problem.setTitle("Invalid request body");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    // 400 - Prompt Injection
    @ExceptionHandler(PromptInjectionException.class)
    public ProblemDetail handlePromptInjection(
            PromptInjectionException exception,
            HttpServletRequest request

    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "The request was blocked because it contained potentially unsafe instructions.\n"
        );

        problem.setTitle("Unsafe instructions");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    //502
    public ProblemDetail handleAIProviderBadRequest(
            BadRequestException exception,
            HttpServletRequest request
    ) {
        log.error(
                "AI provider rejected the request for {}",
                request.getRequestURI(),
                exception
        );

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "The AI provider could not process the request"
        );
        problem.setTitle("AI provider error");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(
        ResourceNotFoundException exception,
        HttpServletRequest request
    ) {
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

        log.error(
                "Unexpected error while processing {}",
                request.getRequestURI(),
                exception
        );

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred."
        );

        problem.setTitle("Internal Server Error");
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }
}
