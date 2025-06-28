/*
 * Copyright 2022-2023 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bhuyanp.gradle.tasks;


import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.figlet.Fonts;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import javax.inject.Inject;

public class PrintAllBannersTask extends DefaultTask implements SpringBannerTask {
    private static final String NAME = "printAllBanners";

    private final Project project;
    private final SpringBannerExtension extension;

    @Inject
    public PrintAllBannersTask(Project project) {
        this.project = project;
        this.extension = project.getExtensions().getByType(SpringBannerExtension.class);
        setGroup(GROUP);
    }

    public static void register(Project project) {
        TaskContainer tasks = project.getTasks();
        tasks.register(NAME, PrintAllBannersTask.class, project);
    }

    @TaskAction
    public void print() {
        Fonts.all().forEach(font ->
                System.out.println(getBannerWCaption(extension, project, font, true)
                        + LINE_SEPARATOR
                )
        );
    }
}