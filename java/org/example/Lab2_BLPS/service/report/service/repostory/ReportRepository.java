package org.example.Lab2_BLPS.service.report.service.repostory;

import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
    Page<ReportEntity> findByModeratorUsernameIsNull(Pageable pageable);

    @Query("UPDATE FROM ReportEntity r SET r.suspectUsername = :username WHERE r.id = :id AND r.moderatorUsername is null")
    ReportEntity moderateReportById(@Param("id") Long id, @Param("username") String username);

    boolean existsByModeratorUsernameAndReportResult(String username, ReportResult reportResult);

    ReportEntity findByModeratorUsernameAndReportResult(String username, ReportResult reportResult);

    boolean existsByIdAndModeratorUsernameAndReportResult(Long id, String username, ReportResult reportResult);

    @Query("UPDATE FROM ReportEntity r SET r.reportResult = :reportResult WHERE r.id = :id AND r.moderatorUsername = :username")
    ReportEntity updateReportResultById(@Param("id") Long id, @Param("username") String username, ReportResult reportResult);
}
