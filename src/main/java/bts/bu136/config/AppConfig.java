package bts.bu136.config;

import bts.bu136.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableConfigurationProperties({AppConfigData.class, GitData.class})
@EnableScheduling
@EnableWebMvc
@RequiredArgsConstructor
public class AppConfig {

    private final GitService gitService;

    @Scheduled(cron = "${settings.schedule}")
    public void backupSchedule() {
        gitService.backup();
    }
}
