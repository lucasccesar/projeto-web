package br.com.bookly.services.impl;

import br.com.bookly.entities.ParticipantUser;
import br.com.bookly.repositories.ParticipantUserRepository;
import br.com.bookly.services.ParticipantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ParticipantUserServiceImpl implements ParticipantUserService {

    @Autowired
    ParticipantUserRepository participantUserRepository;


    @Override
    public ParticipantUser createParticipantUser(ParticipantUser participantUser) {

        if (participantUser.getUser() == null || participantUser.getClub() == null) {
            return null;
        }

        boolean exists = participantUserRepository.existsByUser_IdAndClub_IdBookClub(
                participantUser.getUser().getId(),
                participantUser.getClub().getIdBookClub()
        );

        if (exists) {
            return null;
        }

        if (participantUser.getEntryDate() == null) {
            participantUser.setEntryDate(LocalDate.now());
        }

        return participantUserRepository.save(participantUser);
    }

    @Override
    public ParticipantUser updateParticipantUser(UUID id, ParticipantUser participantUser) {

        ParticipantUser exists = participantUserRepository.findById(id).orElse(null);

        if (exists == null) {
            return null;
        }

        if (participantUser.getEntryDate() != null) {
            exists.setEntryDate(participantUser.getEntryDate());
        } else {
            exists.setEntryDate(exists.getEntryDate());
        }

        if(participantUser.getUser() == null || participantUser.getClub() == null){
            return null;
        }

        exists.setClub(participantUser.getClub());
        exists.setUser(participantUser.getUser());

        return participantUserRepository.save(exists);
    }

    @Override
    public boolean deleteParticipantUserById(UUID id) {

        if(participantUserRepository.existsById(id) == false){
            return false;
        }

        participantUserRepository.deleteById(id);
        return true;
    }

    @Override
    public ParticipantUser getParticipantUser(UUID id) {
        return participantUserRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ParticipantUser> getParticipantUsers(Pageable pageable) {
        return participantUserRepository.findAll(pageable);
    }

    @Override
    public Page<ParticipantUser> getByUserId(UUID idUser, Pageable pageable) {
        return participantUserRepository.findByUser_Id(idUser, pageable);
    }

    @Override
    public Page<ParticipantUser> getByClubId(UUID idClub, Pageable pageable) {
        return participantUserRepository.findByClub_IdBookClub(idClub, pageable);
    }
}
