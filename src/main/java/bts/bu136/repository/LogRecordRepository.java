package bts.bu136.repository;

import bts.bu136.model.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {

    @Query("from LogRecord lr order by lr.dateTime desc limit 20")
    List<LogRecord> getAllLimited();
}
