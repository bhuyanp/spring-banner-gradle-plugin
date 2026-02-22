package io.github.bhuyanp.gradle.tasks;


import lombok.extern.slf4j.Slf4j;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
@Slf4j
public class GenerateBannerTask extends SpringBannerTask {
    public static final String NAME = "generateBanner";

    private static final String FILENAME = "banner.txt";

    protected final File resourcesDir;

    @Inject
    public GenerateBannerTask(Project project) {
        super(project);
        this.resourcesDir = project.getExtensions()
                .getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME).getResources().getSrcDirs().iterator().next();
    }

    public static void register(Project project) {
        TaskContainer tasks = project.getTasks();
        var generateBannerTask = tasks.register(NAME, GenerateBannerTask.class, project);
        generateBannerTask.configure(
                    task -> {
                        task.setGroup(GROUP);
                        task.setDescription("Generates '" + FILENAME + "' and stores under src/main/resources.");
                    }
        );
        Task processResourceTask = tasks.getByName(project.getExtensions()
                .getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getProcessResourcesTaskName());
        processResourceTask.dependsOn(generateBannerTask.get());
    }

    @TaskAction
    public void generateBanner() {
        Path path = Objects.requireNonNull(resourcesDir)
                .toPath()
                .resolve(FILENAME);
        String result = generate();
        try {
            Path dir = path.getParent();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            } else {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
            Files.write(path, (result).getBytes(), StandardOpenOption.CREATE);
            log.info("Banner generated at: " + path.toAbsolutePath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
