package br.com.bookly.repositories;

import br.com.bookly.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    public Users findByEmail(String email);

    public Users findByName(String name);
    public Users findByEmailAndPassword(String email, String password);
    public boolean existsByEmail(String email);
    public List<Users> findByNameContainingIgnoreCase(String name) ;
}
