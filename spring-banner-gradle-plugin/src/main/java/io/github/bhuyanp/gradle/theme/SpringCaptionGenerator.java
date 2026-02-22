package io.github.bhuyanp.gradle.theme;

import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.common.PluginUtil;
import io.github.bhuyanp.gradle.model.CaptionSetting;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gradle.api.JavaVersion;
import org.gradle.util.GradleVersion;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static io.github.bhuyanp.gradle.ansi.Ansi.colorize;
import static io.github.bhuyanp.gradle.common.Constants.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringCaptionGenerator {
    public static final SpringCaptionGenerator INSTANCE = new SpringCaptionGenerator();

    public static final String KEY_APP_VERSION = "$appVersion";
    public static final String KEY_SPRING_VERSION = "$springBootVersion";
    public static final String KEY_JDK_VERSION = "$jdkVersion";
    public static final String KEY_GRADLE_VERSION = "$gradleVersion";
    public static final String KEY_ACTIVE_PROFILES = "$activeProfiles";
    public static final int CAPTION_LENGTH = 20;

    private static final String CAPTION_TEMPLATE_APP = PluginUtil.addPadding("Version", CAPTION_LENGTH)  + ": " + KEY_APP_VERSION;
    private static final String CAPTION_TEMPLATE_SPRING_BOOT = PluginUtil.addPadding("Spring Boot", CAPTION_LENGTH) + ": " + KEY_SPRING_VERSION;
    private static final String CAPTION_TEMPLATE_JDK = PluginUtil.addPadding("JDK", CAPTION_LENGTH) + ": " + KEY_JDK_VERSION;
    private static final String CAPTION_TEMPLATE_GRADLE = PluginUtil.addPadding("Gradle", CAPTION_LENGTH)  + ": " + KEY_GRADLE_VERSION;

    private static final List<CaptionSetting.CAPTION_BULLET_STYLE> captionBulletStyles = Arrays.stream(CaptionSetting.CAPTION_BULLET_STYLE.values())
            .filter(style -> style != CaptionSetting.CAPTION_BULLET_STYLE.RANDOM)
            .toList();
    private static final String SPRING_BOOT_VERSION = "${spring-boot.version}";
    private static final String SPRING_ACTIVE_PROFILES = "${spring.profiles.active}";


    public String generate(SpringBannerExtension springBannerExtension, File buildFile) {
        if(!springBannerExtension.getGenerateCaptionValue()) return BLANK;

        CaptionSetting captionSetting = springBannerExtension.getCaptionSettingValue();
        String caption = BLANK;
        String appVersion = getAppVersion(buildFile);
        String jdkVersion = JavaVersion.current().toString();
        String gradleVersion = GradleVersion.current().getVersion();


        String captionText = captionSetting.getText();
        if (!PluginUtil.isBlankString(captionText)) {
            caption += captionText.trim()
                    .replace(KEY_APP_VERSION, appVersion)
                    .replace(KEY_SPRING_VERSION, SPRING_BOOT_VERSION)
                    .replace(KEY_JDK_VERSION, jdkVersion)
                    .replace(KEY_GRADLE_VERSION, gradleVersion)
                    .replace(KEY_ACTIVE_PROFILES, SPRING_ACTIVE_PROFILES);
        } else {
            if (!captionSetting.isHideAppVersion() && !PluginUtil.isBlankString(appVersion)) {
                caption += CAPTION_TEMPLATE_APP.replace(KEY_APP_VERSION, appVersion) + System.lineSeparator();
            }
            if (!captionSetting.isHideSpringBootVersion()) {
                caption += CAPTION_TEMPLATE_SPRING_BOOT.replace(KEY_SPRING_VERSION, SPRING_BOOT_VERSION) + System.lineSeparator();
            }
            if (!captionSetting.isHideJDKVersion() && !PluginUtil.isBlankString(jdkVersion)) {
                caption += CAPTION_TEMPLATE_JDK.replace(KEY_JDK_VERSION, jdkVersion) + System.lineSeparator();
            }
            if (!captionSetting.isHideGradleVersion() && !PluginUtil.isBlankString(gradleVersion)) {
                caption += CAPTION_TEMPLATE_GRADLE.replace(KEY_GRADLE_VERSION, gradleVersion) + System.lineSeparator();
            }

            //caption += CAPTION_TEMPLATE_ACTIVE_PROFILES.replace(KEY_ACTIVE_PROFILES, SPRING_ACTIVE_PROFILES) + System.lineSeparator();

        }

        if (caption.isEmpty()) return caption;

        // Remove spaces around caption lines
        caption = caption.lines()
                .filter(line -> !line.isEmpty())
                .map(String::trim).collect(Collectors.joining(System.lineSeparator()));

        // Find the biggest line in caption
        int biggestLineLength = caption.lines().map(String::length).max(Integer::compareTo).orElse(0);

        // Add spaces to smaller lines to match the biggestLineLength
        caption = caption.lines()
                .map(line -> biggestLineLength > line.length()?line + SPACE.repeat(biggestLineLength - line.length()):line)
                .collect(Collectors.joining(System.lineSeparator()));

        CaptionSetting.CAPTION_BULLET_STYLE captionBulletStyle = captionSetting.getCaptionBulletStyle();
        captionBulletStyle = captionBulletStyle == null ? DEFAULT_CAPTION_BULLET_STYLE : captionBulletStyle;
        if (captionBulletStyle == CaptionSetting.CAPTION_BULLET_STYLE.RANDOM) {
            int randomCaptionIndex = new Random().nextInt(captionBulletStyles.size());
            captionBulletStyle = captionBulletStyles.get(randomCaptionIndex);
        }
        String captionBullet = switch (captionBulletStyle) {
            case GT, PIPE, GTA, POUND, DOLLAR, DASH -> captionBulletStyle.getLabel() + SPACE;
            case NONE, RANDOM -> BLANK;
        };

        THEME_PRESET themePreset = springBannerExtension.getThemePresetValue();
        ThemeConfig captionTheme = Theme.getCaptionTheme(themePreset);
        caption = caption.lines().map(line -> captionBullet + line).collect(Collectors.joining(System.lineSeparator()));
        caption = colorize(caption, captionTheme);
        return caption;
    }



    private String getAppVersion(File buildFile) {
        try {
            String scriptContent = Files.readString(buildFile.toPath());
            String versionLine =  scriptContent.lines()
                    .filter(line->line.contains("version") && line.contains("="))
                    .findFirst()
                    .orElse(BLANK);
            if(versionLine.equals(BLANK)) return BLANK;
            return versionLine
                    .replace("version", BLANK)
                    .replace("\"", BLANK)
                    .replace(" ", BLANK)
                    .replace("=", BLANK)
                    .replace("'", BLANK);
        } catch (Exception e) {
            log.error("Unable to read app version from build file: " + buildFile.getAbsolutePath(), e);
            return BLANK;
        }
    }



}
