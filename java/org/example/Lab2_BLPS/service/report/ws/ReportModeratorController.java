package org.example.Lab2_BLPS.service.report.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.common.object.page.PageResponse;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.mail.ws.dto.MessageDto;
import org.example.Lab2_BLPS.service.report.model.ReportEntity;
import org.example.Lab2_BLPS.service.report.service.ReportService;
import org.example.Lab2_BLPS.service.report.ws.dto.ReportDto;
import org.example.Lab2_BLPS.service.report.ws.filter.ReportFilter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/moderatorReport")
@RequiredArgsConstructor
public class ReportModeratorController {

    private final ReportService reportService;

    private final ConversionService conversionService;

    @GetMapping(path = "/getReports")
    public PageResponse<ReportDto> getReports(@Valid ReportFilter filter) {
        Page<ReportEntity> page = reportService.getReports(filter.getPageNumber(), filter.getPageSize());

        PageResponse<ReportDto> response = conversionService.convert(page, PageResponse.class);
        response.setContent(
                page.getContent().stream()
                        .map(reportEntity -> conversionService.convert(reportEntity, ReportDto.class))
                        .collect(Collectors.toList())
        );
        response.setTotalElements(page.getTotalElements());
        return response;
    }

    @PostMapping(path = "/moderateReport")
    public ReportDto moderateReport(@Valid @RequestBody ReportDto report) {
        return conversionService.convert(
                reportService.moderateReport(conversionService.convert(report, ReportEntity.class)),
                ReportDto.class
        );
    }

    @PostMapping(path = "/skipReport")
    public ReportDto skipReport(@Valid @RequestBody ReportDto report) {
        return conversionService.convert(
                reportService.skipReport(conversionService.convert(report, ReportEntity.class)),
                ReportDto.class
        );
    }

    @PostMapping(path = "/acceptReport")
    public ReportDto acceptReport(@Valid @RequestBody ReportDto report) {
        return conversionService.convert(
                reportService.acceptReport(conversionService.convert(report, ReportEntity.class)),
                ReportDto.class
        );
    }
}
