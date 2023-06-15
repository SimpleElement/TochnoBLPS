package org.example.Lab2_BLPS.service.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.example.Lab2_BLPS.service.news.model.CommentEntity;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.enm.ReportType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "report_ref")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    private Long id;

    @Column(name = "report_type")
    private ReportType reportType;

    @Column(name = "report_content_id")
    private Long reportContentId;

    @Column(name = "report_content")
    private String reportContent;

    @Column(name = "suspect_username")
    private String suspectUsername;

    @Column(name = "report_result")
    private ReportResult reportResult;

    @Column(name = "moderator")
    private String moderatorUsername;

    @OneToOne(mappedBy = "report", fetch=FetchType.LAZY)
    private NewsServiceBanEntity newsServiceBanEntity;
}
