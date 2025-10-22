package br.com.bookly.services.impl;

import br.com.bookly.entities.Colection;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.ColectionDTO;
import br.com.bookly.entities.dtos.ColectionRespondeDTO;
import br.com.bookly.repositories.ColectionRepository;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.ColectionService;
import br.com.bookly.services.UsersService;
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
    UsersService usersService;

    @Override
    public Colection createColection(ColectionDTO colectionDTO) {

        if(colectionDTO.getName() == null || colectionDTO.getName().isBlank() || colectionDTO.getUserId() == null) {
            return null;
        }

        Users user = usersService.getUsersRepository().findById(colectionDTO.getUserId()).orElse(null);

        if (user == null) {
            return null;
        }

        if (colectionRepository.existsByNameIgnoreCaseAndUser_Id(colectionDTO.getName(), user.getId())) {
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
    public Page<Colection> findByUser_Id(UUID idUser, Pageable pageable) {
        if (idUser == null)
            return colectionRepository.findAll(pageable);

        return colectionRepository.findByUser_Id(idUser, pageable);
    }

    @Override
    public boolean existsByNameIgnoreCaseAndUser_Id(String name, UUID idUser) {
        return colectionRepository.existsByNameIgnoreCaseAndUser_Id(name, idUser);
    }


    @Override
    public Page<ColectionRespondeDTO> findAllColections(Pageable pageable) {
        Page<Colection> colections = colectionRepository.findAll(pageable);

        return (colections.map(ColectionRespondeDTO::new));
    }
}
