package org.example.Lab2_BLPS.service.news.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.CommentDtoConstraint;

import java.util.Date;

@Getter
@Setter
@CommentDtoConstraint
public class CommentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String content;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("news_id")
    private Long newsId;
}
