package com.chat_server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler - Gestionnaire global des exceptions
 * Centralise la gestion des erreurs pour tous les contrôleurs
 * Retourne des réponses JSON cohérentes
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String STATUS_KEY = "status";
    private static final String ERROR_VALUE = "error";
    private static final String MESSAGE_KEY = "message";

    /**
     * Gère les exceptions de validation (IllegalArgumentException)
     * @param ex - L'exception levée
     * @return ResponseEntity avec un message d'erreur structuré
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        
        logger.error("[GlobalExceptionHandler] IllegalArgumentException: {}", ex.getMessage());
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS_KEY, ERROR_VALUE);
        errorResponse.put("code", "INVALID_INPUT");
        errorResponse.put(MESSAGE_KEY, ex.getMessage());
        errorResponse.put(TIMESTAMP_KEY, LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Gère les exceptions nullPointer
     * @param ex - L'exception levée
     * @return ResponseEntity avec un message d'erreur structuré
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(
            NullPointerException ex) {
        
        logger.error("[GlobalExceptionHandler] NullPointerException: {}", ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS_KEY, ERROR_VALUE);
        errorResponse.put("code", "NULL_POINTER");
        errorResponse.put(MESSAGE_KEY, "Une valeur requise est null");
        errorResponse.put(TIMESTAMP_KEY, LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Gère les exceptions génériques non gérées
     * @param ex - L'exception levée
     * @return ResponseEntity avec un message d'erreur structuré
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        logger.error("[GlobalExceptionHandler] Erreur générique: {}", ex.getMessage(), ex);
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS_KEY, ERROR_VALUE);
        errorResponse.put("code", "INTERNAL_SERVER_ERROR");
        errorResponse.put(MESSAGE_KEY, "Une erreur interne est survenue");
        errorResponse.put("details", ex.getMessage());
        errorResponse.put(TIMESTAMP_KEY, LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
