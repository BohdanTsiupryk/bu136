package bts.bu136.service;

import bts.bu136.config.AppConfigData;
import bts.bu136.config.GitData;
import bts.bu136.model.BackupFolder;
import bts.bu136.repository.BackupFolderRepository;
import bts.bu136.repository.LogRecordRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GitService {

    private final AppConfigData appConfigData;
    private final BackupFolderRepository repository;
    private final RecordService recordService;

    private final Logger logger = LoggerFactory.getLogger(GitService.class);

    public void backup() {
        List<BackupFolder> folders = repository.findAll();

        logger.info("Start backup processing");
        logger.info(String.format("Folders list: \n %s",
                folders.stream().map(f -> f.getPref() + " - " + f.getPath()).collect(Collectors.toList()))
        );


        folders
                .forEach(folder -> {

                            if (!checkFolder(folder.getPath())) {
                                logger.error(folder.getPref() + " - root or .git don't exist");
                                recordService.addError("root or .git don't exist", folder);
                                return;
                            }

                            try {
                                Git git = git(folder.getPath());
                                SshSessionFactory sshSessionFactory = createSshSessionFactory(appConfigData.ssh().file(), appConfigData.ssh().passphrase());

                                TransportConfigCallback transportConfigCallback = transport -> {
                                    if (transport instanceof SshTransport t) {
                                        t.setSshSessionFactory(sshSessionFactory);
                                    }
                                };

                                String message = generateCommitMessage(folder.getPref());

                                git.pull()
                                        .setTransportConfigCallback(transportConfigCallback)
                                        .call();
                                git.add().addFilepattern(".").call();
                                git.commit().setMessage(message).call();
                                git.push()
                                        .setTransportConfigCallback(transportConfigCallback)
                                        .setRemote(folder.getRemote())
                                        .call();

                                recordService.addInfo("Backup success, commit message: " + message, folder);
                                logger.info(folder.getPref() + " - backup success, commit message: " + message);

                            } catch (Exception e) {
                                recordService.addError(e.getMessage(), folder);
                                logger.error(folder.getPref() + " - " + e.getMessage());
                            }
                        }
                );

        logger.info("Finish backup processing");
    }

    private String generateCommitMessage(String pref) {
        return pref + " - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

    }

    private SshSessionFactory createSshSessionFactory(String file, String passphrase) {
        return new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host hc, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch jsch = super.createDefaultJSch(fs);
                // Load SSH key
                jsch.addIdentity(file, passphrase);
                return jsch;
            }
        };
    }

    private boolean checkFolder(String path) {
        File file = new File(path);

        return file.isDirectory() &&
                Arrays.stream(Objects.requireNonNull(file.list())).anyMatch(f -> f.contains(".git"));
    }

    private Git git(String path) throws IOException {
        Repository existingRepo = new FileRepositoryBuilder()
                .setGitDir(new File(path + "/.git"))
                .build();
        return new Git(existingRepo);
    }
}
