package org.example.Lab2_BLPS.service.report.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.service.news.enm.StateOfModel;
import org.example.Lab2_BLPS.service.news.service.repository.CommentRepository;
import org.example.Lab2_BLPS.service.news.service.repository.LikeRepository;
import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.example.Lab2_BLPS.service.report.service.repostory.NewsServiceBanRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnbanByCommentScheduler {

    private final ReportService reportService;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final NewsServiceBanRepository newsServiceBanRepository;

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(initialDelayString = "60000", fixedDelayString = "60000")
    public void doWork() {
        if (reportService.haveUsersNeedUnban()) {
            List<String> usernames = reportService.getUnbanUsernames();

            for (String username: usernames) {
                likeRepository.updatesLikesModelByUsername(username, StateOfModel.ACTIVE);
                commentRepository.updatesCommentsModelByUsername(username, StateOfModel.ACTIVE);
                commentRepository.deleteById(newsServiceBanRepository.findByUsername(username).getReport().getReportContentId());
                newsServiceBanRepository.unbanByUsername(username);
            }
        }
    }
}
