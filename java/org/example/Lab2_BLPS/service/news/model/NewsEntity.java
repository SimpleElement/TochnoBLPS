package org.example.Lab2_BLPS.service.news.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "news_ref")
public class NewsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_seq")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "topic")
    private String topic;

    @Column(name = "description")
    private String description;

    @Column(name = "data")
    private Date data;

    @OneToMany(mappedBy = "news", fetch=FetchType.LAZY)
    private List<LikeEntity> likeEntities;

    @OneToMany(mappedBy = "news", fetch=FetchType.LAZY)
    private List<CommentEntity> commentEntities;
}
