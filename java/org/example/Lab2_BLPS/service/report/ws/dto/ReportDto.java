package org.example.Lab2_BLPS.service.report.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.report.ws.validation.constraint.ReportDtoConstraint;

import java.util.Date;

@Getter
@Setter
@ReportDtoConstraint
public class ReportDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("report_type")
    private String reportType;

    @JsonProperty("report_content_id")
    private Long reportContentId;

    @JsonProperty("report_content")
    private String reportContent;

    @JsonProperty("suspect_username")
    private String suspectUsername;

    @JsonProperty("report_result")
    private String reportResult;

    @JsonProperty("unban_date")
    private String unbanDate;
}
