package br.com.bookly.entities.dtos;

import br.com.bookly.entities.BookClubAssignment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class BookClubAssignmentDTO {

    private UUID idClubBook;
    private UUID bookId;
    private UUID bookClubId;
    private LocalDate startDate;
    private LocalDate finishDate;

    public BookClubAssignmentDTO(UUID idClubBook, UUID bookId, UUID bookClubId, LocalDate startDate, LocalDate finishDate) {
        this.idClubBook = idClubBook;
        this.bookId = bookId;
        this.bookClubId = bookClubId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public BookClubAssignmentDTO(BookClubAssignment bookClubAssignment) {
        this.idClubBook = bookClubAssignment.getIdClubBook();
        this.bookId = bookClubAssignment.getBook().getIdBook();
        this.bookClubId = bookClubAssignment.getBookClub().getIdBookClub();
        this.startDate = bookClubAssignment.getStartDate();
        this.finishDate = bookClubAssignment.getFinishDate();
    }
}
