package br.com.bookly.services;


import br.com.bookly.entities.Colection;
import br.com.bookly.entities.dtos.ColectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ColectionService {

    public Colection createColection(ColectionDTO colectionDTO);
    public boolean deleteColection(Colection colection);
    public boolean deleteColectionById(UUID idColection);
    Colection updateColection(UUID idColection, Colection colection);
    public Colection findColectionById(UUID idColection);
    public Colection findColectionByName(String name);
    List<Colection> findByUser_IdUser(UUID userId);
    boolean existsByNameIgnoreCaseAndUser_IdUser(String name, UUID userId);
    public Page<Colection> findAllColections(Pageable pageable);
}
