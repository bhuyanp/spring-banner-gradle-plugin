package io.pbhuyan.gradle.spring;

import io.pbhuyan.gradle.spring.theme.Theme;
import io.pbhuyan.gradle.spring.theme.ThemeFormat;
import io.pbhuyan.gradle.spring.theme.ThemeProvider;
import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.util.GradleVersion;
import org.gradle.util.internal.DefaultGradleVersion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface SpringBannerExtension extends ThemeProvider{

    String NAME = "springBanner";

    static void create(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        extensions.create(NAME, SpringBannerExtension.class);
    }

    /**
     * Banner text
     *
     * @return Banner text
     */
    Property<String> getText();
    default String getTextValue(Project project) {
        return getText().getOrElse(capitalizeProjectName(project.getName()));
    }

    default String capitalizeProjectName(String projectName) {
        return Arrays.stream(projectName.replace('-', ' ').split(" ")).map(name ->
                        name.substring(0, 1).toUpperCase() + name.substring(1))
                .collect(Collectors.joining(" "));
    }

    Property<Theme> getTheme();
    default Theme getThemeValues() {
        return getTheme().getOrElse(Theme.DARK);
    }

    Property<ThemeFormat> getBannerTheme();
    default ThemeFormat getBannerThemeValues() {
        return getCaptionTheme().getOrElse(getBannerTheme(getThemeValues()));
    }


    Property<String> getCaption();
    default String getCaptionValue(Project project) {
        return getCaption().getOrElse("""
                | Version: %s
                | SpringBoot Version: ${spring-boot.version}
                | Java Version: %s
                | Gradle Version: %s
                """.formatted(project.getVersion(), JavaVersion.current(), ((DefaultGradleVersion) GradleVersion.current()).getVersion()));

    }

    Property<ThemeFormat> getCaptionTheme();
    default ThemeFormat getCaptionThemeValues() {
        return getCaptionTheme().getOrElse(getCaptionTheme(getThemeValues()));
    }


    ListProperty<String> getFonts();
    default List<String> getFontsValue() {
        return getFonts().get().isEmpty() ? DEFAULT_FONTS : getFonts().get();
    }

    List<String> DEFAULT_FONTS = List.of(
            "3d",
            "4max",
            "ansiregular",
            "ansishadow",
            "banner3_d",
            "banner4",
            "bigmoneyne",
            "bolger",
            "calvins",
            "colossal",
            "elite",
            "georgia11",
            "lean",
            "epic",
            "lineblocks",
            "nancyj",
            "poison",
            "puffy",
            "soft",
            "standard",
            "usaflag",
            "whimsy");
}
