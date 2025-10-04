package br.com.bookly.services.impl;

import br.com.bookly.entities.Colection;
import br.com.bookly.repositories.ColectionRepository;
import br.com.bookly.services.ColectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ColectionServiceImpl implements ColectionService {

    @Autowired
    ColectionRepository colectionRepository;

    @Override
    public Colection createColection(Colection colection) {
        if(colection.getName() == null || colection.getName().isBlank())
            return null;

        if(colection.getDescription() == null || colection.getDescription().isBlank())
            colection.setDescription("");

        if (colectionRepository.existsByNameIgnoreCase(colection.getName()))
            return null;

        return colectionRepository.save(colection);
    }

    @Override
    public boolean deleteColection(Colection colection) {

        Colection exists = colectionRepository.findById(colection.getIdColection()).orElse(null);

        if(exists == null)
            return false;

        colectionRepository.delete(exists);
        return true;
    }

    @Override
    public boolean deleteColectionById(UUID idColection) {
        if(colectionRepository.findById(idColection) == null)
            return false;

        colectionRepository.deleteById(idColection);
        return true;
    }

    @Override
    public Colection updateColection(UUID uuid, Colection colection) {
        Colection exists = colectionRepository.findById(uuid).orElse(null);

        if(exists == null)
            return null;

        if(exists.getName() == null || exists.getName().isBlank())
            return null;

        if(exists.getDescription() == null || exists.getDescription().isBlank())
            return null;

        exists.setName(exists.getName());
        exists.setDescription(exists.getDescription());

        return  colectionRepository.save(exists);
    }

    @Override
    public Colection findColectionById(UUID idColection) {
        return colectionRepository.findById(idColection).orElse(null);
    }

    @Override
    public Colection findColectionByName(String name) {
        return colectionRepository.findByNameIgnoreCase(name) ;
    }

    @Override
    public Page<Colection> findAllColections(Pageable pageable) {
        return  colectionRepository.findAll(pageable);
    }
}
