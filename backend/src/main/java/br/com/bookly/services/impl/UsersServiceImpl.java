package br.com.bookly.services.impl;

import br.com.bookly.entities.BookClub;
import br.com.bookly.entities.Enums.UserType;
import br.com.bookly.entities.Users;
import br.com.bookly.repositories.UsersRepository;
import br.com.bookly.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;


    @Override
    public UsersRepository getUsersRepository() {
        return usersRepository;
    }

    @Override
    public Users signupUser(Users user) {

        if(user.getName() == null || user.getName().isBlank()) {
            return null;
        }

        if(user.getEmail() == null || user.getEmail().isBlank() || usersRepository.findByEmail(user.getEmail()) != null) {
            return null;
        }

        if(user.getPassword() == null || user.getPassword().isBlank()) {
            return null;
        }

        if(user.getBirthday().isAfter(LocalDate.now())) {
            return null;
        }

        if(user.getType() == null) {
            user.setType(UserType.CLIENT);
        }

        return usersRepository.save(user);
    }

    @Override
    public Users loginUser(String email, String password) {

        Users userByEmail = usersRepository.findByEmail(email);

        if(userByEmail == null) {
            return null;
        }

        if(!userByEmail.getEmail().equals(email) || !userByEmail.getPassword().equals(password)) {
            return null;
        }

        return userByEmail;

    }

    @Override
    public Users updateUser(UUID id, Users user) {

        if(user == null || id == null){
            return null;
        }

        Users userById = usersRepository.findById(id).orElse(null);

        if(userById == null) {
            return null;
        }

        userById.setName(user.getName());

        boolean isSameEmail = user.getEmail().equals(userById.getEmail());

        if(!isSameEmail) {
            if(user.getEmail() == null || user.getEmail().isBlank() || usersRepository.existsByEmail(user.getEmail())){
                return null;
            }
            userById.setEmail(user.getEmail());
        }

        userById.setPassword(user.getPassword());
        userById.setType(user.getType());

        return usersRepository.save(userById);

    }

    @Override
    public boolean deleteUser(UUID id) {
        if(!usersRepository.existsById(id)){
            return false;
        }

        usersRepository.deleteById(id);

        if(!usersRepository.existsById(id)){
            return true;
        }

        return false;
    }

    @Override
    public Page<Users> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }
}
