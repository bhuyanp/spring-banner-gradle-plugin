package io.github.bhuyanp.gradle.tasks;


import io.github.bhuyanp.gradle.SpringBannerExtension;
import org.gradle.api.DefaultTask;
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

public class GenerateBannerTask extends DefaultTask implements SpringBannerTask {
    public static final String NAME = "generateBanner";

    private static final String FILENAME = "banner.txt";

    private final SpringBannerExtension extension;
    private final File resourcesDir;
    private final String projectName;


    @Inject
    public GenerateBannerTask(Project project) {
        this.resourcesDir = project.getExtensions()
                .getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME).getOutput().getResourcesDir();
        this.extension = project.getExtensions().getByType(SpringBannerExtension.class);
        this.projectName = project.getName();
    }

    public static void register(Project project) {
        TaskContainer tasks = project.getTasks();
        var generateTask = tasks.register(NAME, GenerateBannerTask.class, project);
        generateTask.configure(
                    task -> {
                        task.setGroup(GROUP);
                        task.setDescription("Generates '" + FILENAME + "'.");
                    }
        );
        Task processResourceTask = tasks.getByName(project.getExtensions()
                .getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getProcessResourcesTaskName());
        processResourceTask.finalizedBy(generateTask.get());
    }


    @TaskAction
    public void generate() {
        Path path = Objects.requireNonNull(resourcesDir)
                .toPath()
                .resolve(FILENAME);
        String result = getBannerWCaption(extension, projectName);
        try {
            Path dir = path.getParent();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            } else {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
            if(extension.getShowPreviewValue()) {
                System.out.println(result);
            }
            Files.write(path, (result).getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
