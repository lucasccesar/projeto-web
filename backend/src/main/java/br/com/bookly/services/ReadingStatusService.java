package br.com.bookly.services;

import br.com.bookly.entities.ReadingStatus;
import br.com.bookly.entities.dtos.ReadingStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReadingStatusService {

    public ReadingStatus createReadingStatus(ReadingStatusDto readingStatus);
    public ReadingStatus updateReadingStatus(UUID uuid,  ReadingStatusDto readingStatus);
    boolean deleteReadingStatus(UUID readingStatusId);
    public ReadingStatus findReadingStatus(UUID readingStatusId);
    public ReadingStatus findReadingStatusbyBook_IdBook(UUID idBook);
    public Page<ReadingStatus> findReadingStatusbyUsers_Id(UUID userId, Pageable pageable);
}
