package bts.bu136.model.dto;

import java.time.LocalDateTime;

public record LogRecordDto(
        Long id,
        String pref,
        String msg,
        String type,
        String date
) {
}
