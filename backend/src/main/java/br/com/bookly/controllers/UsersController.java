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
    public ResponseEntity<UsersDTO> signupUser(@RequestBody Users user) {
        Users userSignedUp = usersService.signupUser(user);
        UsersDTO userDTO = new UsersDTO(userSignedUp);

        if (userSignedUp != null) {
            return ResponseEntity.status(201).body(userDTO);
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
    public ResponseEntity<UsersDTO> updateUser(@PathVariable UUID id, @RequestBody Users user) {
        Users updated = usersService.updateUser(id, user);
        if (updated != null) {
            UsersDTO userDTO = new UsersDTO(updated);
            return ResponseEntity.ok(userDTO);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDTO> loginUser(@RequestBody UsersLoginDTO loginDto) {
        Users user = usersService.loginUser(loginDto.getEmail(), loginDto.getPassword());

        if (user != null) {
            UsersDTO userDTO = new UsersDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
