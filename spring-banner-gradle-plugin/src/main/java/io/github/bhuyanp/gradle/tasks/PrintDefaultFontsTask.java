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


import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import javax.inject.Inject;
import java.util.stream.Collectors;

import static io.github.bhuyanp.gradle.common.Constants.DEFAULT_FONTS;
import static io.github.bhuyanp.gradle.common.Constants.LINE_SEPARATOR;

public class PrintDefaultFontsTask extends SpringBannerTask {
    private static final String NAME = "printDefaultFonts";

    @Inject
    public PrintDefaultFontsTask(Project project) {
        super(project);
    }

    public static void register(Project project) {
        TaskContainer tasks = project.getTasks();
        tasks.register(NAME, PrintDefaultFontsTask.class, project).configure(
                task -> {
                    task.setGroup(GROUP);
                    task.setDescription("Prints banners with only default fonts.");
                }
        );
    }

    @TaskAction
    public void print() {
        String finalOutput = DEFAULT_FONTS.stream().map(font ->
                System.lineSeparator() +"Font: " + font + System.lineSeparator() + generate(font)
        ).collect(Collectors.joining(LINE_SEPARATOR));
        System.out.println(finalOutput);
    }
}