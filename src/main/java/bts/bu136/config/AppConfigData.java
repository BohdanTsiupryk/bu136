package bts.bu136.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "settings")
public record AppConfigData(String schedule, SshData ssh) {}