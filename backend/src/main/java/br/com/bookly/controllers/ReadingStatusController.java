package br.com.bookly.controllers;


import br.com.bookly.entities.ReadingStatus;
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
    public ResponseEntity<ReadingStatus> createReadingStatus(@RequestBody ReadingStatusDto readingStatus){

        ReadingStatus created = readingStatusService.createReadingStatus(readingStatus);

        if(created == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(201).body(created);
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
    public ResponseEntity<ReadingStatus> updtaReadingStatus(@RequestBody ReadingStatusDto readingStatus, @PathVariable UUID id){
        ReadingStatus rd =  readingStatusService.updateReadingStatus(id, readingStatus);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rd);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReadingStatus> getReadingStatus(@PathVariable UUID id){
        ReadingStatus rd = readingStatusService.findReadingStatus(id);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rd);
    }

    @GetMapping("/idBook/{id}")
    public ResponseEntity<ReadingStatus> getReadingStatusByIdBook(@PathVariable UUID id){
        ReadingStatus rd = readingStatusService.findReadingStatusbyBook_IdBook(id);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rd);
    }

    @GetMapping("/idUser/{id}")
    public ResponseEntity<Page<ReadingStatus>> getReadingStatusByIdUser(@PathVariable UUID id, Pageable pageable){
        Page<ReadingStatus> rd = readingStatusService.findReadingStatusbyUsers_Id(id, pageable);
        if(rd == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rd);
    }
}
