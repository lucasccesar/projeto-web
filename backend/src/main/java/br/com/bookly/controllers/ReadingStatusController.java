package br.com.bookly.controllers;


import br.com.bookly.entities.ReadingStatus;
import br.com.bookly.entities.dtos.RatingDTO;
import br.com.bookly.entities.dtos.ReadingStatusDto;
import br.com.bookly.entities.dtos.ReadingStatusResponseDto;
import br.com.bookly.services.ReadingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/readingstatus")
public class ReadingStatusController {

    @Autowired
    ReadingStatusService readingStatusService;

    @PostMapping
    public ResponseEntity<ReadingStatusResponseDto> createReadingStatus(@RequestBody ReadingStatus readingStatus){

        ReadingStatus created = readingStatusService.createReadingStatus(readingStatus);
        ReadingStatusResponseDto readingStatusDto = new ReadingStatusResponseDto(created);

        return ResponseEntity.status(201).body(readingStatusDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReadingStatusResponseDto> deleteReadingStatus(@PathVariable UUID id){
        boolean delete =   readingStatusService.deleteReadingStatus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadingStatusResponseDto> updateReadingStatus(@RequestBody ReadingStatus readingStatus, @PathVariable UUID id){
        ReadingStatus rd = readingStatusService.updateReadingStatus(id, readingStatus);
        ReadingStatusResponseDto readingStatusDto = new ReadingStatusResponseDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadingStatusResponseDto> getReadingStatus(@PathVariable UUID id){
        ReadingStatus rd = readingStatusService.findReadingStatus(id);
        ReadingStatusResponseDto readingStatusDto = new ReadingStatusResponseDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/idBookAndIdUser")
    public ResponseEntity<ReadingStatusResponseDto> getReadingStatusByIdBook(@RequestBody ReadingStatus readingStatus){
        UUID bookId = readingStatus.getBook().getIdBook();
        UUID userId = readingStatus.getUsers().getId();
        ReadingStatus rd = readingStatusService.findReadingStatusbyBook_IdBookAndUsers_Id(bookId, userId);
        ReadingStatusResponseDto readingStatusDto = new ReadingStatusResponseDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<Page<ReadingStatusResponseDto>> getReadingStatusByIdUser(@PathVariable UUID id, Pageable pageable){
        Page<ReadingStatusResponseDto> rd = readingStatusService.findReadingStatusbyUsers_Id(id, pageable).map(ReadingStatusResponseDto::new);
        return ResponseEntity.ok(rd);
    }
}
