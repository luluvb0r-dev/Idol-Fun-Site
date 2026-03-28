package jp.co.idolFunSite.app.controller.common;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.idolFunSite.app.dto.common.ApiError;
import jp.co.idolFunSite.app.dto.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

/**
 * 例外をAPI共通形式へ変換します。
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        ApiError apiError = new ApiError(resolveErrorCode(status, exception.getReason()), resolveMessage(exception.getReason()));
        return ResponseEntity.status(status)
                .body(ApiResponse.failure(getRequestId(request), List.of(apiError)));
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError("INVALID_REQUEST", "Request is invalid.");
        return ResponseEntity.badRequest()
                .body(ApiResponse.failure(getRequestId(request), List.of(apiError)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception exception, HttpServletRequest request) {
        log.error("Unexpected error occurred.", exception);
        ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR", "An internal server error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(getRequestId(request), List.of(apiError)));
    }

    private String getRequestId(HttpServletRequest request) {
        Object requestId = request.getAttribute(RequestIdFilter.REQUEST_ID_ATTRIBUTE);
        return requestId instanceof String value ? value : null;
    }

    private String resolveErrorCode(HttpStatus status, String reason) {
        if (status == HttpStatus.NOT_FOUND) {
            if (reason != null && reason.contains("Site")) {
                return "SITE_NOT_FOUND";
            }
            if (reason != null && reason.contains("Song")) {
                return "SONG_NOT_FOUND";
            }
            if (reason != null && reason.contains("Member")) {
                return "MEMBER_NOT_FOUND";
            }
            if (reason != null && reason.contains("Release")) {
                return "RELEASE_NOT_FOUND";
            }
        }

        if (status == HttpStatus.BAD_REQUEST) {
            return "INVALID_REQUEST";
        }

        return "INTERNAL_SERVER_ERROR";
    }

    private String resolveMessage(String reason) {
        return reason == null || reason.isBlank() ? "Request failed." : reason;
    }
}
