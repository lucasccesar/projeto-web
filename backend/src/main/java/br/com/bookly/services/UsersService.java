package br.com.bookly.services;

import br.com.bookly.entities.BookClub;
import br.com.bookly.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface UsersService {

    public Users signupUser(Users user);
    public Users loginUser(String email, String password);
    public Users updateUser(UUID id, Users user);
    public boolean deleteUser(UUID id);
    public Page<Users> getUsers(Pageable pageable);

}
