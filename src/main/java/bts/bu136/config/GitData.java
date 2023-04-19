package bts.bu136.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "git")
public record GitData(List<Folder> folders) {
}
