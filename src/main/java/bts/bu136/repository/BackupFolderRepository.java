package bts.bu136.repository;

import bts.bu136.model.BackupFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupFolderRepository extends JpaRepository<BackupFolder, Long> {

}
