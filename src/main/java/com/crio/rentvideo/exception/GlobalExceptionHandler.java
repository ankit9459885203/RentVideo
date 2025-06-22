package com.crio.rentvideo.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @ControllerAdvice makes this class capable of handling exceptions across the entire application
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles RuntimeException, specifically for "Not Found" scenarios in this context.
     * Maps to HTTP Status 404 Not Found.
     * In a more complex application, you would define custom exceptions (e.g., ResourceNotFoundException)
     * and handle them specifically.
     *
     * @param ex The RuntimeException thrown.
     * @param request The current web request.
     * @return A ResponseEntity with an error message and HTTP status 404.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        // Here, we are specifically targeting the RuntimeExceptions thrown for "Not Found" scenarios
        // in our UserService and VideoService.
        // For production, you'd likely create custom exceptions (e.g., ResourceNotFoundException)
        // and handle them more granularly.
        if (ex.getMessage().contains("not found") || ex.getMessage().contains("already exists")) {
            // Log the exception details if needed
            System.err.println("Handling RuntimeException: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Or HttpStatus.BAD_REQUEST for "already exists"
        }
        // For any other RuntimeException not specifically handled, return Internal Server Error
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // You can add more @ExceptionHandler methods for other specific exceptions,
    // e.g., @ExceptionHandler(MethodArgumentNotValidException.class) for validation errors (HTTP 400),
    // @ExceptionHandler(AccessDeniedException.class) for authorization errors (HTTP 403), etc.
}
