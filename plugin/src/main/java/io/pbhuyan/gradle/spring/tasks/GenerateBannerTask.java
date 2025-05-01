package io.pbhuyan.gradle.spring.tasks;

import io.pbhuyan.gradle.spring.SpringBannerExtension;
import io.pbhuyan.gradle.spring.figlet.FigletBannerRenderer;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class GenerateBannerTask extends DefaultTask implements SpringBannerTask {
    public static final String NAME = "generateBanner";

    private static final String FILENAME = "banner.txt";

    private final Project project;
    private final SpringBannerExtension extension;
    private final FigletBannerRenderer renderer = FigletBannerRenderer.SINGLETON;
    ;


    @Inject
    public GenerateBannerTask(Project project) {
        this.project = project;
        this.extension = project.getExtensions().getByType(SpringBannerExtension.class);
        setGroup(GROUP);
        setDescription("Generates '" + FILENAME + "'.");
    }

    public static void register(Project project) {
        TaskContainer tasks = project.getTasks();
        var generateTask = tasks.register(NAME, GenerateBannerTask.class, project);
        Task processResourceTask = tasks.getByName(project.getExtensions()
                .getByType(SourceSetContainer.class)
                .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                .getProcessResourcesTaskName());
        processResourceTask.finalizedBy(generateTask.get());
    }


    @TaskAction
    public void generate() {
        Path path = Objects.requireNonNull(project.getExtensions()
                        .getByType(SourceSetContainer.class)
                        .getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                        .getOutput()
                        .getResourcesDir(), "sourceSets.main.resourcesDir")
                .toPath()
                .resolve(FILENAME);
        String result = getBanner(extension, project, renderer);
        try {
            Path dir = path.getParent();
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            } else {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }
            Files.write(path, (System.lineSeparator() + result).getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
