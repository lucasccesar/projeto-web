package br.com.bookly.services;

import br.com.bookly.entities.BookClub;
import br.com.bookly.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UsersService {

    Users signupUser(Users user);
    Users loginUser(String email, String password);
    Users updateUser(UUID id, Users user);
    boolean deleteUser(UUID id);
    Page<Users> getUsers(Pageable pageable);

}
