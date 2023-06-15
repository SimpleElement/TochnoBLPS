package org.example.Lab2_BLPS.service.report.service;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.authorization.service.repository.UserRepository;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.LikeRepository;
import org.example.Lab2_BLPS.service.report.enm.ReportResult;
import org.example.Lab2_BLPS.service.report.model.NewsServiceBanEntity;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.service.repostory.NewsServiceBanRepository;
import org.example.Lab2_BLPS.service.report.service.repostory.ReportRepository;
import org.example.Lab2_BLPS.service.report.service.validator.ReportAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;

    private final ReportRepository reportRepository;

    private final CommentRepository commentRepository;

    private final NewsServiceBanRepository newsServiceBanRepository;

    public ReportEntity sendReport(ReportEntity report) {
        ReportAssert.isUserExists(userRepository.existsByUsername(report.getSuspectUsername()), "Пользователь с данным именем не найден");

        switch (report.getReportType()) {
            case COMMENT:
                ReportAssert.isCommentExists(commentRepository.existsById(report.getReportContentId()), "Коментарий с данным id не найден");
                ReportAssert.isCommentOfUser(
                        commentRepository.existsByIdAndUsername(report.getId(), report.getSuspectUsername()),
                        "Данный коментарий не пренадлежит заявленному пользователю"
                );
                report.setReportContent(commentRepository.findById(report.getReportContentId()).get().getContent());
        }

        report.setId(null);
        report.setReportResult(ReportResult.EXPECTATION);
        return reportRepository.save(report);
    }

    public Page<ReportEntity> getReports(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reportRepository.findByModeratorUsernameIsNull(pageable);
    }

    public ReportEntity moderateReport(ReportEntity report) {
        ReportAssert.reportIdIsNonNull(report.getId(), "Не указан id репорта");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (reportRepository.existsByModeratorUsernameAndReportResult(username, ReportResult.EXPECTATION))
            return reportRepository.findByModeratorUsernameAndReportResult(username, ReportResult.EXPECTATION);

        ReportEntity moderate = reportRepository.moderateReportById(report.getId(), username);
        ReportAssert.reportIsNonNull(moderate, "Выбранная вами заявка уже обработана, попробуйте взять другую");

        return moderate;
    }

    public ReportEntity skipReport(ReportEntity report) {
        ReportAssert.reportIdIsNonNull(report.getId(), "Не указан id репорта");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReportAssert.reportReadyToModerate(
                reportRepository.existsByIdAndModeratorUsernameAndReportResult(report.getId(), username, ReportResult.EXPECTATION),
                "Ошибка данных репорта");

        return reportRepository.updateReportResultById(report.getId(), username, ReportResult.SKIP);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReportEntity acceptReport(ReportEntity report) {
        ReportAssert.reportIdIsNonNull(report.getId(), "Не указан id репорта");
        ReportAssert.newsServiceBanNonNull(report.getNewsServiceBanEntity(), "Не указан бан сервис");
        ReportAssert.unbanDateNonNull(report.getNewsServiceBanEntity().getUnbanDate(), "Не указана дата разбана");

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ReportAssert.reportReadyToModerate(
                reportRepository.existsByIdAndModeratorUsernameAndReportResult(report.getId(), username, ReportResult.EXPECTATION),
                "Ошибка данных репорта");

        likeRepository.updatesLikesModelByUsername(report.getSuspectUsername(), StateOfModel.FROZEN);
        commentRepository.updatesCommentsModelByUsername(report.getSuspectUsername(), StateOfModel.FROZEN);

        ReportEntity reportFromDb = reportRepository.updateReportResultById(report.getId(), username, ReportResult.SKIP);
        NewsServiceBanEntity newsServiceBan = report.getNewsServiceBanEntity();
        newsServiceBan.setReport(reportFromDb);
        newsServiceBan.setUsername(report.getSuspectUsername());
        newsServiceBanRepository.save(newsServiceBan);

        return reportFromDb;
    }

    public boolean userHaveBanFromNewsService(String username) {
        return newsServiceBanRepository.existsByUsername(username);
    }

    public NewsServiceBanEntity getNewsServiceBanEntityByUsername(String username) {
        return newsServiceBanRepository.findByUsername(username);
    }

    public boolean haveUsersNeedUnban() {
        return newsServiceBanRepository.existsUsersByUnban(new Date());
    }

    public List<String> getUnbanUsernames() {
        return newsServiceBanRepository.getUnbanUsername(new Date());
    }
}
