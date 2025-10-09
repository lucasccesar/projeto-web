package br.com.bookly.services.impl;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.ReadingStatus;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.ReadingStatusDto;
import br.com.bookly.repositories.BookRepository;
import br.com.bookly.repositories.ReadingStatusRepository;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.ReadingStatusService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReadingStatusServiceImpl implements ReadingStatusService {

    @Autowired
    ReadingStatusRepository readingStatusRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public ReadingStatus createReadingStatus(ReadingStatusDto readingStatus) {
        if(readingStatus == null || readingStatus.getStatus() == null){
            return null;
        }

        Users user = usersRepository.findById(readingStatus.getIduser()).orElse(null);
        if(user == null){
            return null;
        }

        Book book = bookRepository.findById(readingStatus.getIdbook()).orElse(null);
        if(book == null){
            return null;
        }


        ReadingStatus rd = new ReadingStatus();
        rd.setStatus(readingStatus.getStatus());
        rd.setBook(book);
        rd.setUsers(user);

        return readingStatusRepository.save(rd);

    }

    @Override
    public ReadingStatus updateReadingStatus(UUID id,ReadingStatusDto readingStatus) {

        ReadingStatus exists = readingStatusRepository.findById(id).orElse(null);
        if(exists==null){
            return null;
        }

        if(readingStatus.getIdbook() == null){
            return null;
        }
        if(readingStatus.getStatus() == null || readingStatus.getStatus().equals(exists.getStatus()) ){
            return null;
        }

        exists.setStatus(readingStatus.getStatus());
        return readingStatusRepository.save(exists);

    }

    @Override
    public boolean deleteReadingStatus(UUID readingStatusId) {
        if(readingStatusRepository.existsById(readingStatusId) == false){
            return false;
        }

        readingStatusRepository.deleteById(readingStatusId);
        return true;
    }

    @Override
    public ReadingStatus findReadingStatus(UUID readingStatusId) {
        return  readingStatusRepository.findById(readingStatusId).orElse(null);
    }

    @Override
    public ReadingStatus findReadingStatusbyBook_IdBook(UUID idBook) {
        return readingStatusRepository.findByBook_IdBook(idBook);
    }

    @Override
    public Page<ReadingStatus> findReadingStatusbyUsers_Id(UUID userId, Pageable pageable) {
        if(userId == null){
            return readingStatusRepository.findAll(pageable);
        }

        return readingStatusRepository.findByUsers_id(userId, pageable);
    }
}
