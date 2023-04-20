package bts.bu136.controller;

import bts.bu136.model.BackupFolder;
import bts.bu136.model.dto.LogRecordDto;
import bts.bu136.service.FolderService;
import bts.bu136.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final FolderService folderService;
    private final RecordService recordService;


    @GetMapping("/index")
    public String index(Model model) {

        List<BackupFolder> all = folderService.getAll();
        model.addAttribute("folders", all);
        return "main";
    }

    @GetMapping("/logs")
    public String logs(Model model) {

        List<LogRecordDto> all = recordService.getAllRecords();
        model.addAttribute("records", all);
        return "logs";
    }
}
