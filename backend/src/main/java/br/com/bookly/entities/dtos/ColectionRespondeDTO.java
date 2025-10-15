package br.com.bookly.entities.dtos;

import br.com.bookly.entities.Book;
import br.com.bookly.entities.Colection;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter

public class ColectionRespondeDTO {

    private UUID id;
    private UUID userId;
    private String description;
    private String name;

    public ColectionRespondeDTO (Colection colection) {
        this.id = colection.getIdColection();
        this.userId = colection.getUser().getId();
        this.description = colection.getDescription();
        this.name = colection.getName();
    }
}
