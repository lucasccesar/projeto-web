package br.com.bookly.services;


import br.com.bookly.entities.BookClubAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookClubAssignmentService {
    public BookClubAssignment createBookClub(BookClubAssignment bookClubAssignment);
    public boolean deleteBookClub(BookClubAssignment bookClubAssignment);
    public boolean deleteById(UUID id);
    public BookClubAssignment updateBookClub(UUID id, BookClubAssignment bookClubAssignment);
    public BookClubAssignment findBookClubById(UUID id);
    public Page<BookClubAssignment> findAllBookClubs(Pageable pageable);
}
