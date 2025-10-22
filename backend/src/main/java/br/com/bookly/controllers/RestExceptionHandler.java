package br.com.bookly.controllers;


import br.com.bookly.entities.dtos.ExceptionResponseDTO;
import br.com.bookly.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ExistentBookClubException.class)
    public ResponseEntity<ExceptionResponseDTO> existentBookClubExceptionHandler(ExistentBookClubException ebc, HttpServletRequest request) {
            ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ebc.getMessage(), 400, request.getRequestURI());
            return ResponseEntity.status(exceptionResponseDTO.getStatusCode()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(InexistentBookClubException.class)
    public ResponseEntity<ExceptionResponseDTO> inexistentBookClubExceptionHandler(InexistentBookClubException ibc, HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ibc.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatusCode()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseDTO> badRequestExceptionHandler(BadRequestException bre, HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(bre.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatusCode()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(ExistingParticipantUserException.class)
    public ResponseEntity<ExceptionResponseDTO> existingParticipantUserExceptionHandler(ExistingParticipantUserException epu, HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(epu.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatusCode()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(InexistentParticipantUserException.class)
    public ResponseEntity<ExceptionResponseDTO> inexistentParticipantUserExceptionHandler(InexistentParticipantUserException ipu, HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ipu.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatusCode()).body(exceptionResponseDTO);
    }
    //erro generico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGeneralException(Exception ex, HttpServletRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(ex.getMessage(), 500, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
