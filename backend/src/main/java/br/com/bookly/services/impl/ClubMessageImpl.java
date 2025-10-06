package br.com.bookly.services.impl;

import br.com.bookly.entities.ClubMessage;
import br.com.bookly.repositories.ClubMessageRepository;
import br.com.bookly.services.ClubMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ClubMessageImpl implements ClubMessageService {

    @Autowired
    ClubMessageRepository clubMessageRepository;

    @Override
    public Page<ClubMessage> listClubMessage(Pageable pageable) {
        return clubMessageRepository.findAll(pageable);
    }

    @Override
    public ClubMessage findClubMessageById(UUID id) {
        return clubMessageRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ClubMessage> getByClubId(UUID idClub, Pageable pageable) {
        return clubMessageRepository.findByClub_IdBookClub(idClub, pageable);
    }

    @Override
    public Page<ClubMessage> getByUserId(UUID idUser, Pageable pageable) {
        return clubMessageRepository.findByUser_Id(idUser, pageable);
    }

    @Override
    public boolean deleteClubMessageById(UUID id) {
        if(clubMessageRepository.existsById(id) == false){
            return false;
        }
        clubMessageRepository.deleteById(id);
        return true;
    }

    @Override
    public ClubMessage createClubMessage(ClubMessage clubMessage) {

        if(clubMessage.getMessage() == null  || clubMessage.getMessage().trim().isEmpty()){
            return null;
        }

        if(clubMessage.getUser() == null || clubMessage.getClub() == null) {
            return null;
        }

        if(clubMessage.getMessageDate() == null){
            clubMessage.setMessageDate(Timestamp.valueOf(LocalDateTime.now()));
        }

        return clubMessageRepository.save(clubMessage);
    }

    @Override
    public ClubMessage updateClubMessage(UUID id, ClubMessage clubMessage) {

        ClubMessage exists = clubMessageRepository.findById(id).orElse(null);

        if(exists == null){
            return null;
        }

        if(clubMessage.getMessage() == null || clubMessage.getMessage().trim().isEmpty()){
            return null;
        }

        if(clubMessage.getClub() == null || clubMessage.getUser() == null){
            return null;
        }

        exists.setMessage(clubMessage.getMessage());
        exists.setUser(clubMessage.getUser());
        exists.setClub(clubMessage.getClub());
        exists.setMessageDate(Timestamp.valueOf(LocalDateTime.now()));

        return clubMessageRepository.save(exists);
    }
}
