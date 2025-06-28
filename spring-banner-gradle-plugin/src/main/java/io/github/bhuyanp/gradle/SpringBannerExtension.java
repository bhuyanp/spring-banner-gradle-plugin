package io.github.bhuyanp.gradle;


import io.github.bhuyanp.gradle.theme.ThemeConfig;
import io.github.bhuyanp.gradle.theme.ThemePreset;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.util.GradleVersion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Extension for the plugin with various customization option
 */
public interface SpringBannerExtension {

    /**
     * Extension name
     */
    String NAME = "springBanner";

    static void create(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        extensions.create(NAME, SpringBannerExtension.class);
    }

    Property<String> getText();

    /**
     *
     * @param project
     * @return Text provided in the extension otherwise the project name
     */
    default String getTextValue(Project project) {
        return getText().getOrElse(capitalizeProjectName(project.getName()));
    }

    /**
     *
     * @param projectName Name of the project
     * @return The capitalized project name and dashes replaces with spaces
     */
    default String capitalizeProjectName(String projectName) {
        return Arrays.stream(projectName.replace('-', ' ').split(" ")).map(name ->
                        name.substring(0, 1).toUpperCase() + name.substring(1))
                .collect(Collectors.joining(" "));
    }

    Property<ThemePreset> getThemePreset();

    default ThemePreset getThemePresetValue() {
        return getThemePreset().getOrElse(ThemePreset.DARK);
    }

    Property<ThemeConfig> getBannerTheme();

    default ThemeConfig getBannerThemeValue() {
        return getBannerTheme().getOrNull();
    }


    Property<String> getCaption();

    String DEFAULT_CAPTION = """
                Version                 : %s
                Spring Boot Version     : %s
                JDK Version             : %s
                Gradle Version          : %s
                """;
    String SPRING_BOOT_VERSION = "${spring-boot.version}";
    default String getCaptionValue(Project project) {
        return getCaption().getOrElse(DEFAULT_CAPTION.formatted(project.getVersion(), SPRING_BOOT_VERSION,
                JavaVersion.current(), GradleVersion.current().getVersion()));

    }

    Property<ThemeConfig> getCaptionTheme();

    default ThemeConfig getCaptionThemeValue() {
        return getCaptionTheme().getOrNull();
    }

    Property<Boolean> getShowPreview();

    default Boolean getShowPreviewValue() {
        return getShowPreview().getOrElse(false);
    }

    Property<Boolean> getPrintBannerConfig();

    default Boolean getPrintBannerConfigValue() {
        return getPrintBannerConfig().getOrElse(false);
    }



    ListProperty<String> getBannerFonts();

    default List<String> getBannerFontsValue() {
        return getBannerFonts().get().isEmpty() ? DEFAULT_FONTS : getBannerFonts().get();
    }

    List<String> DEFAULT_FONTS = List.of(
            "3d",
            "4max",
            "ansiregular",
            "ansishadow",
            "banner3_d",
            "banner4",
            "bigmoneyne",
            "block",
            "bolger",
            "calvins",
            "colossal",
            "cyberlarge",
            "elite",
            "georgia11",
            "lean",
            "epic",
            "lineblocks",
            "nancyj",
            "poison",
            "starwars",
            "puffy",
            "soft",
            "standard",
            "usaflag",
            "usaflag",
            "usaflag",
            "whimsy");
}
