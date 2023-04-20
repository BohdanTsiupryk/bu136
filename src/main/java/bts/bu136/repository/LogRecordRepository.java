package bts.bu136.repository;

import bts.bu136.model.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {
}
