package br.com.bookly.controllers;

import br.com.bookly.entities.Colection;
import br.com.bookly.services.ColectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/collection") //
public class ColectionController {

    @Autowired
    private ColectionService colectionService;

    @PostMapping
    public ResponseEntity<Colection> createCollection(@RequestBody Colection colection) {
        Colection createdCollection = colectionService.createColection(colection);
        if (createdCollection == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(createdCollection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionById(@PathVariable UUID id) {
        boolean deleted = colectionService.deleteColectionById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colection> updateCollection(@PathVariable UUID id, //O Path Variable (id) identifica o recurso
                                                      @RequestBody Colection colection){ // o Request Body (colection) provê os novos dados.
        Colection updatedCollection = colectionService.updateColection(id, colection);
        if (updatedCollection == null) {

            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colection> findCollectionById(@PathVariable UUID id) {
        Colection colection = colectionService.findColectionById(id);
        if (colection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colection);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Colection> findCollectionByName(@PathVariable String name) {
        Colection colection = colectionService.findColectionByName(name);
        if (colection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colection);
    }

    @GetMapping
    public ResponseEntity<Page<Colection>> listAllCollections(Pageable pageable) {
        return ResponseEntity.ok(colectionService.findAllColections(pageable));
    }
}