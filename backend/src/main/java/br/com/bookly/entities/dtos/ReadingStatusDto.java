package br.com.bookly.entities.dtos;


import br.com.bookly.entities.Book;
import br.com.bookly.entities.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReadingStatusDto {

    private Status status;
    private UUID idbook;
    private UUID iduser;

    public ReadingStatusDto() {
    }

    public ReadingStatusDto(Status status, UUID idbook, UUID idUser) {
        this.status = status;
        this.idbook = idbook;
        this.iduser = idUser;
    }
}
