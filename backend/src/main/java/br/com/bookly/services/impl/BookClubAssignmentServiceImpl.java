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
public class BookClubAssignmentServiceImpl implements BookClubAssignmentService {

    @Autowired
    BookClubAssignmentRepository bookClubAssignmentRepository;


    @Override
    public BookClubAssignment createBookClubAssignment(BookClubAssignment bookClubAssignment) {

        if (bookClubAssignment.getStartDate() == null || bookClubAssignment.getFinishDate() == null) {
            return null;
        }

        //Não permite datas igual (inicio/fim)
        if (bookClubAssignment.getStartDate().isEqual(bookClubAssignment.getFinishDate())){
            return null;
        }

        //Data final menor do que inicial
        if (bookClubAssignment.getStartDate().isAfter(bookClubAssignment.getFinishDate())){
            return null;
        }

        boolean exists = bookClubAssignmentRepository.existsByBook_IdBookAndBookClub_IdBookClubAndStartDateAndFinishDate(bookClubAssignment.getBook().getIdBook(), bookClubAssignment.getBookClub().getIdBookClub(),
                bookClubAssignment.getStartDate(),
                bookClubAssignment.getFinishDate()
        );

        if (exists) {
            return null;
        }

        return bookClubAssignmentRepository.save(bookClubAssignment);
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

        if (bookClubAssignment.getStartDate() == null || bookClubAssignment.getFinishDate() == null) {
            return null;
        }

        if (bookClubAssignment.getStartDate().isEqual(bookClubAssignment.getFinishDate())){
            return null;
        }

        if (bookClubAssignment.getStartDate().isAfter(bookClubAssignment.getFinishDate())){
            return null;
        }

        exists.setFinishDate(bookClubAssignment.getFinishDate());
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

    @Override
    public Page<BookClubAssignment> findByBookId(UUID bookId, Pageable pageable) {
        return bookClubAssignmentRepository.findByBook_IdBook(bookId, pageable);
    }

    @Override
    public Page<BookClubAssignment> findByBookClubId(UUID bookClubId, Pageable pageable) {
        return bookClubAssignmentRepository.findByBookClub_IdBookClub(bookClubId, pageable);
    }
}
