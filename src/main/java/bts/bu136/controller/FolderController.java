package bts.bu136.controller;

import bts.bu136.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/folder/add")
    public String addFolder(
            @RequestParam("pref") String pref,
            @RequestParam("path") String path,
            @RequestParam("remote") String remote,
            Model model
    ) {
        folderService.addFolder(pref, path, remote);

        return "redirect:/index";
    }
    @GetMapping("/folder/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
        folderService.delete(id);

        return "redirect:/index";
    }
}
