package br.com.bookly.services;


import br.com.bookly.entities.Colection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ColectionService {

    public Colection createColection(Colection colection);
    public boolean deleteColection(Colection colection);
    public boolean deleteColectionById(UUID idColection);
    public Colection updateColection(UUID uuid, Colection colection);
    public Colection findColectionById(UUID idColection);
    public Colection findColectionByName(String name);
    public Page<Colection> findAllColections(Pageable pageable);
}
