package io.github.bhuyanp.gradle.tasks;

import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.common.PluginUtil;
import io.github.bhuyanp.gradle.theme.SpringBannerGenerator;
import io.github.bhuyanp.gradle.theme.SpringCaptionGenerator;
import lombok.RequiredArgsConstructor;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;

import java.io.File;

import static io.github.bhuyanp.gradle.common.Constants.BLANK;

@RequiredArgsConstructor
public class SpringBannerTask extends DefaultTask {
    protected static final String GROUP = "Spring Banner";
    protected final SpringBannerExtension extension;
    private final File buildFile;
    private final String projectName;

    public SpringBannerTask(Project project) {
        this.buildFile = project.getBuildFile();
        this.extension = project.getExtensions().getByType(SpringBannerExtension.class);
        this.projectName = project.getName();
    }


    protected String generate() {
        String banner = SpringBannerGenerator.INSTANCE.generate(extension, projectName);
        String caption = SpringCaptionGenerator.INSTANCE.generate(extension, buildFile);
        return getFinalBanner(banner, caption);
    }

    protected String generate(String bannerFont) {
        String banner =  SpringBannerGenerator.INSTANCE.generate(extension,projectName, bannerFont);
        String caption = SpringCaptionGenerator.INSTANCE.generate(extension, buildFile);
        return getFinalBanner(banner, caption);
    }

    private String getFinalBanner(String banner, String caption) {
        String finalText;
        if (PluginUtil.isBlankString(banner) && PluginUtil.isBlankString(caption)) {
            return BLANK;
        } else if (!PluginUtil.isBlankString(banner) && !PluginUtil.isBlankString(caption)) {
            finalText = banner + System.lineSeparator().repeat(2) + caption;
        } else if (!PluginUtil.isBlankString(banner)) {
            finalText = banner;
        } else {
            finalText = caption;
        }
        finalText = System.lineSeparator() + finalText + System.lineSeparator();
        return finalText;
    }
}
