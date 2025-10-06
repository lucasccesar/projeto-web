package br.com.bookly.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersLoginDTO {

    private String email;
    private String password;

    public UsersLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
