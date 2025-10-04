package br.com.bookly.services.impl;

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
    public Users signupUser(Users user) {

        if(user.getName() == null || user.getName().isBlank()) {
            return null;
        }

        if(user.getEmail() == null || user.getEmail().isBlank() || usersRepository.existsByEmail(user.getEmail())) {
            return null;
        }

        if(user.getPassword() == null || user.getPassword().isBlank()) {
            return null;
        }

        if(user.getBirthday().isBefore(LocalDate.now())) {
            return null;
        }

        if(user.getType() == null) {
            user.setType(UserType.CLIENT);
        }

        return usersRepository.save(user);
    }

    @Override
    public Users loginUser(Users user) {

        Users userByEmail = usersRepository.findByEmail(user.getEmail());

        if(!user.getEmail().equals(userByEmail.getEmail()) || !user.getPassword().equals(userByEmail.getPassword())) {
            return null;
        }

        return user;

    }

    @Override
    public Users updateUser(Users user) {

        if(user == null || user.getIdUser() == null){
            return null;
        }

        Users userById = usersRepository.findById(user.getIdUser()).orElse(null);

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
    public boolean deleteUser(Users user) {
        usersRepository.delete(user);

        if(!usersRepository.existsById(user.getIdUser())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteUser(UUID id) {
        usersRepository.deleteById(id);

        if(!usersRepository.existsById(id)){
            return true;
        }

        return false;
    }
}
