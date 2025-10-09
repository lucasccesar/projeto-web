package br.com.bookly.entities.dtos;

import br.com.bookly.entities.Enums.UserType;
import br.com.bookly.entities.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {

    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String password;
    private UserType type;

    public UsersDTO(UUID id, String name, LocalDate birthDate, String email, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public UsersDTO(Users user){
        this.id = user.getId();
        this.name = user.getName();
        this.birthDate = user.getBirthday();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.type = user.getType();

    }

}
