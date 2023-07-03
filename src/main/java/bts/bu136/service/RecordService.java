package bts.bu136.service;

import bts.bu136.model.BackupFolder;
import bts.bu136.model.LogRecord;
import bts.bu136.model.dto.LogRecordDto;
import bts.bu136.repository.LogRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final LogRecordRepository repository;

    public List<LogRecordDto> getAllRecords() {
        return repository.getAllLimited()
                .stream()
                .map(log -> new LogRecordDto(
                        log.getId(),
                        log.getPref(),
                        log.getMessage(),
                        log.getType().name(),
                        log.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                ))
                .collect(Collectors.toList());
    }

    public LogRecord addInfo(String msg, BackupFolder folder) {
        return repository.save(new LogRecord(folder.getPref(), msg, LogRecord.Type.INFO, LocalDateTime.now()));
    }

    public LogRecord addError(String msg, BackupFolder folder) {
        return repository.save(new LogRecord(folder.getPref(), msg, LogRecord.Type.ERROR, LocalDateTime.now()));
    }
}
