package br.com.bookly.services.impl;

import br.com.bookly.entities.Colection;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.ColectionDTO;
import br.com.bookly.repositories.ColectionRepository;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.ColectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ColectionServiceImpl implements ColectionService {

    @Autowired
    ColectionRepository colectionRepository;

    @Autowired
    UsersRepository userRepository;

    @Override
    public Colection createColection(ColectionDTO colectionDTO) {
        // 1. Validação dos dados do DTO
        if(colectionDTO.getName() == null || colectionDTO.getName().isBlank() || colectionDTO.getUserId() == null) {
            return null;
        }

        Users user = userRepository.findById(colectionDTO.getUserId()).orElse(null);

        if (user == null) {
            return null;
        }

        if (colectionRepository.existsByNameIgnoreCaseAndUser_IdUser(colectionDTO.getName(), user.getIdUser())) {
            return null;
        }

        Colection newColection = new Colection();
        newColection.setName(colectionDTO.getName());
        newColection.setDescription(colectionDTO.getDescription());
        newColection.setUser(user);

        return colectionRepository.save(newColection);
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
        if(!colectionRepository.existsById(idColection))
            return false;

        colectionRepository.deleteById(idColection);
        return true;
    }

    @Override
    public Colection updateColection(UUID idColection, Colection colection) {
        Colection exists = colectionRepository.findById(idColection).orElse(null);

        if(exists == null)
            return null;

        if(colection.getName() == null || colection.getName().isBlank())
            return null;

        if(colection.getDescription() == null || colection.getDescription().isBlank())
            return null;

        exists.setName(colection.getName());
        exists.setDescription(colection.getDescription());

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
    public List<Colection> findByUser_IdUser(UUID idUser) {
        if (idUser == null)
            return colectionRepository.findAll();

        return colectionRepository.findByUser_IdUser(idUser);
    }

    @Override
    public boolean existsByNameIgnoreCaseAndUser_IdUser(String name, UUID idUser) {
        return colectionRepository.existsByNameIgnoreCaseAndUser_IdUser(name, idUser);
    }


    @Override
    public Page<Colection> findAllColections(Pageable pageable) {
        return  colectionRepository.findAll(pageable);
    }
}
