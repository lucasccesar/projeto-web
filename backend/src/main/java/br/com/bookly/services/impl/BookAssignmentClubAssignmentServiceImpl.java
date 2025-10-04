package br.com.bookly.services.impl;

import br.com.bookly.entities.BookClubAssignment;
import br.com.bookly.repositories.BookClubAssignmentRepository;
import br.com.bookly.services.BookClubAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookAssignmentClubAssignmentServiceImpl implements BookClubAssignmentService {

    @Autowired
    BookClubAssignmentRepository bookClubAssignmentRepository;


    @Override
    public BookClubAssignment createBookClubAssignment(BookClubAssignment bookClubAssignment) {

        if (bookClubAssignment.getStartDate() == null || bookClubAssignment.getFisnishDate() == null) {
            return null;
        }

        //Não permite datas igual (inicio/fim)
        if (bookClubAssignment.getStartDate().isEqual(bookClubAssignment.getFisnishDate())){
            return null;
        }

        //Data final menor do que inicial
        if (bookClubAssignment.getStartDate().isAfter(bookClubAssignment.getFisnishDate())){
            return null;
        }

        return bookClubAssignmentRepository.save(bookClubAssignment);
    }

    @Override
    public boolean deleteBookClubAssignment(BookClubAssignment bookClubAssignment) {

        BookClubAssignment exists = bookClubAssignmentRepository.findById(bookClubAssignment.getIdClubBook()).orElse(null);

        if(exists == null){
            return false;
        }

        bookClubAssignmentRepository.delete(exists);
        return true;
    }

    @Override
    public boolean deleteById(UUID id) {

        if(bookClubAssignmentRepository.existsById(id) ==  false){
            return false;
        }

        bookClubAssignmentRepository.deleteById(id);
        return true;
    }

    @Override
    public BookClubAssignment updateBookClubAssignment(UUID id, BookClubAssignment bookClubAssignment) {

        BookClubAssignment exists = bookClubAssignmentRepository.findById(id).orElse(null);

        if(exists == null){
            return null;
        }

        if (bookClubAssignment.getStartDate() == null || bookClubAssignment.getFisnishDate() == null) {
            return null;
        }

        if (bookClubAssignment.getStartDate().isEqual(bookClubAssignment.getFisnishDate())){
            return null;
        }

        if (bookClubAssignment.getStartDate().isAfter(bookClubAssignment.getFisnishDate())){
            return null;
        }

        exists.setFisnishDate(bookClubAssignment.getFisnishDate());
        exists.setStartDate(bookClubAssignment.getStartDate());

        return bookClubAssignmentRepository.save(exists);
    }

    @Override
    public BookClubAssignment findBookClubAssignmentById(UUID id) {
        return bookClubAssignmentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<BookClubAssignment> findAllBookClubsAssignment(Pageable pageable) {
        return bookClubAssignmentRepository.findAll(pageable);
    }
}
