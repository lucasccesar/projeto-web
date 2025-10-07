package br.com.bookly.services.impl;

import br.com.bookly.entities.BookClub;
import br.com.bookly.repositories.BookClubRepository;
import br.com.bookly.services.BookClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class BookClubServiceImpl implements BookClubService {

    @Autowired
    BookClubRepository bookClubRepository;

    @Override
    public BookClub createBookClub(BookClub bookClub) {

        if (bookClub.getName() == null || bookClub.getName().isBlank()) {
            return null;
        }

        if (bookClub.getTheme() == null || bookClub.getTheme().isBlank()) {
            return null;
        }

        // descrição não obrigatoria seta como vazio
        if (bookClub.getDescription() == null || bookClub.getDescription().isBlank()) {
            bookClub.setDescription("");
        }

        // verifica se já tem um clube com o mesmo nome
        if (bookClubRepository.existsByNameIgnoreCase(bookClub.getName())) {
            return null;
        }

        return bookClubRepository.save(bookClub);
    }

    @Override
    public BookClub updateBookClub(UUID id, BookClub bookClub) {

        BookClub exists = bookClubRepository.findById(id).orElse(null); // se o clube não existir retorna null

        if (exists == null) {
            return null;
        }

        if (bookClub.getName() == null || bookClub.getName().isBlank()) {
            return null;
        }

        if (bookClub.getTheme() == null || bookClub.getTheme().isBlank()) {
            return null;
        }

        // Se o nome foi alterado, verifica duplicação
        if (!exists.getName().equals(bookClub.getName()) // verifica se foi feita alteração no nome
                && bookClubRepository.existsByNameIgnoreCase(bookClub.getName()))// verifica se existe algum clube com esse nome
            {return null;}


        exists.setName(bookClub.getName());
        exists.setTheme(bookClub.getTheme());
        exists.setDescription(bookClub.getDescription());

        return bookClubRepository.save(exists);
    }

    @Override
    public boolean deleteBookClub(BookClub bookClub) {

        BookClub exists = bookClubRepository.findById(bookClub.getIdBookClub()).orElse(null);

        if (exists == null) {
            return false;
        }

        bookClubRepository.delete(exists);
        return true;
    }

    @Override
    public boolean deleteBookClubById(UUID id) {

        if (bookClubRepository.existsById(id) == false) {
            return false;
        }

        bookClubRepository.deleteById(id);
        return true;
    }

    @Override
    public BookClub findBookClubById(UUID id) {
        return bookClubRepository.findById(id).orElse(null);
    }

    @Override
    public BookClub findBookClubByName(String name) {
        return bookClubRepository.findBookClubByNameIgnoreCase(name);
    }

    @Override
    public Page<BookClub> listBookClub(Pageable pageable) {
        return bookClubRepository.findAll(pageable);
    }
}