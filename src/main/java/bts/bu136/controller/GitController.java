package bts.bu136.controller;

import bts.bu136.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GitController {

    private final GitService gitService;

    @GetMapping("/backup")
    public ResponseEntity<HttpStatus> backup() {

        gitService.backup();

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
