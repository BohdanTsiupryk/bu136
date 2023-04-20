package bts.bu136.service;

import bts.bu136.model.BackupFolder;
import bts.bu136.repository.BackupFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final BackupFolderRepository repository;


    public List<BackupFolder> getAll() {
        return repository.findAll();
    }

    public void addFolder(String pref, String path, String remote) {
        repository.save(new BackupFolder(pref, path, remote));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
