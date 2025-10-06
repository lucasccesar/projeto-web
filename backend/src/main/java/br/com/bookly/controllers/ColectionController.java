package br.com.bookly.controllers;

import br.com.bookly.entities.Colection;
import br.com.bookly.entities.dtos.ColectionDTO;
import br.com.bookly.services.ColectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/colection") //
public class ColectionController {

    @Autowired
    private ColectionService colectionService;

    @PostMapping
    public ResponseEntity<Colection> createColection(@RequestBody ColectionDTO colectionDTO) {
        Colection createdCollection = colectionService.createColection(colectionDTO);
        if (createdCollection == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(createdCollection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColectionById(@PathVariable UUID id) {
        boolean deleted = colectionService.deleteColectionById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colection> updateColection(@PathVariable UUID id, //O Path Variable (id) identifica o recurso
                                                      @RequestBody Colection colection){ // o Request Body (colection) provê os novos dados.
        Colection updatedCollection = colectionService.updateColection(id, colection);
        if (updatedCollection == null) {

            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colection> findColectionById(@PathVariable UUID idColection) {
        Colection colection = colectionService.findColectionById(idColection);
        if (colection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colection);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Colection> findColectionByName(@PathVariable String name) {
        Colection colection = colectionService.findColectionByName(name);
        if (colection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colection);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Colection>> findColectionsByIdUser(@PathVariable UUID userId, Pageable pageable) {
        Page<Colection> colecoes = colectionService.findByUser_Id(userId, pageable);
        return ResponseEntity.ok(colecoes);
    }


    @GetMapping
    public ResponseEntity<Page<Colection>> listAllColections(Pageable pageable) {
        return ResponseEntity.ok(colectionService.findAllColections(pageable));
    }
}