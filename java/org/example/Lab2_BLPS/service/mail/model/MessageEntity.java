package org.example.Lab2_BLPS.service.mail.model;

import lombok.*;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "message_ref")
public class MessageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    private Long id;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "sender")
    private String sender;

    @Column(name = "topic")
    private String topic;

    @Column(name = "content")
    private String content;

    @Column(name = "date_of_dispatch")
    private Date dateOfDispatch;
}
