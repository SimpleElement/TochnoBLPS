package org.example.Lab2_BLPS.service.news.ws.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.common.object.page.Page;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentFilter extends Page {
    @NotNull
    @Min(value = 1, message = "Параметр news_id не должно быть меньше 1")
    @Max(value = 2147483647, message = "Параметр news_id не должно быть больше 2147483647")
    private Long newsId;
}
