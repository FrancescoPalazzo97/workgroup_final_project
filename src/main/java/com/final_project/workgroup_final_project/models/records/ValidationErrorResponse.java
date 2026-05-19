package com.final_project.workgroup_final_project.models.records;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        Map<String, String> errors) {

}
