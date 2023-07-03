package bts.bu136.controller;

import bts.bu136.config.AppConfigData;
import bts.bu136.model.BackupFolder;
import bts.bu136.model.Config;
import bts.bu136.model.dto.LogRecordDto;
import bts.bu136.repository.ConfigRepository;
import bts.bu136.service.FolderService;
import bts.bu136.service.GitService;
import bts.bu136.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final AppConfigData appConfigData;
    private final FolderService folderService;
    private final RecordService recordService;
    private final ConfigRepository configRepository;
    private final GitService gitService;

    @GetMapping("/backup")
    public String backup() {

        gitService.backup();

        return "redirect:/logs";
    }

    @GetMapping("/index")
    public String index(Model model) {

        String sshKeyPath = configRepository.findConfigByKey("sshKeyPath")
                .orElse(new Config())
                .getValue();

        List<BackupFolder> all = folderService.getAll();

        model.addAttribute("folders", all);
        model.addAttribute("sshKeyPath", sshKeyPath);
        return "main";
    }

    @GetMapping("/logs")
    public String logs(Model model) {

        List<LogRecordDto> all = recordService.getAllRecords();
        model.addAttribute("records", all);
        return "logs";
    }

    @PostMapping("/ssh/key/add")
    public String sshKeyAdd(
            @RequestParam("path") String path
    ) {

        String volume = appConfigData.dockerVolume();

        String pathname = volume + path;
        if (new File(pathname).isFile()) {

            Optional<Config> sshKeyPath = configRepository.findConfigByKey("sshKeyPath");

            if (sshKeyPath.isPresent()) {
                Config config = sshKeyPath.get();
                config.setValue(pathname);
                configRepository.save(config);
            } else {

                configRepository.save(new Config("sshKeyPath", pathname));
            }
        }

        return "redirect:/index";
    }
}
