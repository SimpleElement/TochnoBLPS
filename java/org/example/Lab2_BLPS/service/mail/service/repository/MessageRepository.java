package org.example.Lab2_BLPS.service.mail.service.repository;

import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    Page<MessageEntity> findByRecipient(String recipient, Pageable pageable);
}
