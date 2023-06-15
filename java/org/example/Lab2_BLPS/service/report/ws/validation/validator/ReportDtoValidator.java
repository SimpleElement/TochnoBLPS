package org.example.Lab2_BLPS.service.report.ws.validation.validator;

import org.example.Lab2_BLPS.common.util.ConstraintUtil;
import org.example.Lab2_BLPS.service.report.enm.ReportType;
import org.example.Lab2_BLPS.service.report.ws.dto.ReportDto;
import org.example.Lab2_BLPS.service.report.ws.validation.constraint.ReportDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ReportDtoValidator implements ConstraintValidator<ReportDtoConstraint, ReportDto> {

    @Override
    public boolean isValid(ReportDto reportDto, ConstraintValidatorContext context) {
        boolean res = true;

        // Null constraint
        if (Objects.isNull(reportDto.getReportType())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле report_type не должно быть null");
        }
        if (Objects.isNull(reportDto.getReportContentId())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле report_content_id не должно быть null");
        }
        if (Objects.isNull(reportDto.getSuspectUsername())) {
            res = ConstraintUtil.addConstraintViolation(context, "Поле suspect_username не должно быть null");
        }

        // Enum constraint
        if (Objects.nonNull(reportDto.getReportType())) {
            boolean coincidence = false;
            for (ReportType enumValue : ReportType.values()) {
                if (enumValue.name().equals(reportDto.getReportType())) {
                    coincidence = true;
                }
            }
            if (!coincidence) {
                res = ConstraintUtil.addConstraintViolation(context, "Поле report_type введено некорректно");
            }
        }

        // Parse constraint
        if (Objects.nonNull(reportDto.getUnbanDate())) {
            try {
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("dd.MM.yyyy");
                format.parse(reportDto.getUnbanDate());
            } catch (Exception e) {
                res = ConstraintUtil.addConstraintViolation(context, "Поле unban_date заполнено некорректно. dd.MM.yyyy");
            }
        }

        return res;
    }
}
