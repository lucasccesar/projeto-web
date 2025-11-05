package br.com.bookly.controllers;


import br.com.bookly.entities.ReadingStatus;
import br.com.bookly.entities.dtos.RatingDTO;
import br.com.bookly.entities.dtos.ReadingStatusDto;
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
    public ResponseEntity<ReadingStatusDto> createReadingStatus(@RequestBody ReadingStatus readingStatus){

        ReadingStatus created = readingStatusService.createReadingStatus(readingStatus);

        if(created == null){
            return ResponseEntity.notFound().build();
        }
        ReadingStatusDto readingStatusDto = new ReadingStatusDto(created);

        return ResponseEntity.status(201).body(readingStatusDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReadingStatus> deleteReadingStatus(@PathVariable UUID id){
        boolean delete =   readingStatusService.deleteReadingStatus(id);
        if(delete == false){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadingStatusDto> updateReadingStatus(@RequestBody ReadingStatus readingStatus, @PathVariable UUID id){
        ReadingStatus rd = readingStatusService.updateReadingStatus(id, readingStatus);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        ReadingStatusDto readingStatusDto = new ReadingStatusDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadingStatusDto> getReadingStatus(@PathVariable UUID id){
        ReadingStatus rd = readingStatusService.findReadingStatus(id);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        ReadingStatusDto readingStatusDto = new ReadingStatusDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/idBookAndIdUser")
    public ResponseEntity<ReadingStatusDto> getReadingStatusByIdBook(@RequestBody ReadingStatus readingStatus){
        UUID bookId = readingStatus.getBook().getIdBook();
        UUID userId = readingStatus.getUsers().getId();
        ReadingStatus rd = readingStatusService.findReadingStatusbyBook_IdBookAndUsers_Id(bookId, userId);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        ReadingStatusDto readingStatusDto = new ReadingStatusDto(rd);
        return ResponseEntity.ok(readingStatusDto);
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<Page<ReadingStatusDto>> getReadingStatusByIdUser(@PathVariable UUID id, Pageable pageable){
        Page<ReadingStatusDto> rd = readingStatusService.findReadingStatusbyUsers_Id(id, pageable).map(ReadingStatusDto::new);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rd);
    }
}
