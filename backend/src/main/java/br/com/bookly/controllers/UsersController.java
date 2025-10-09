package br.com.bookly.controllers;

import br.com.bookly.entities.BookClub;
import br.com.bookly.entities.Users;
import br.com.bookly.entities.dtos.ParticipantUserDTO;
import br.com.bookly.entities.dtos.UsersDTO;
import br.com.bookly.entities.dtos.UsersLoginDTO;
import br.com.bookly.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<Page<UsersDTO>> getUsers(Pageable pageable) {
        Page<UsersDTO> dtos = usersService.getUsers(pageable).map(UsersDTO::new);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Users> signupUser(@RequestBody Users user) {

        Users userSignedUp = usersService.signupUser(user);

        if (userSignedUp != null) {
            return ResponseEntity.status(201).body(userSignedUp);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean deleted = usersService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable UUID id, @RequestBody Users user) {
        Users updated = usersService.updateUser(id, user);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestBody UsersLoginDTO loginDto) {
        Users user = usersService.loginUser(loginDto.getEmail(), loginDto.getPassword());

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
