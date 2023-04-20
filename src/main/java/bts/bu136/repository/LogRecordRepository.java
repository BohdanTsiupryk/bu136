package bts.bu136.repository;

import bts.bu136.model.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {
}
