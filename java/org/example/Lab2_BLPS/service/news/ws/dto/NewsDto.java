package org.example.Lab2_BLPS.service.news.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.ws.validation.constraint.NewsDtoConstraint;

import java.util.Date;

@Getter
@Setter
@NewsDtoConstraint
public class NewsDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("author")
    private String author;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("description")
    private String description;

    @JsonProperty("data")
    private Date data;
}
