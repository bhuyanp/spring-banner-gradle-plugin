package io.github.bhuyanp.gradle;


import io.github.bhuyanp.gradle.theme.Theme;
import io.github.bhuyanp.gradle.theme.ThemeBuilder;
import io.github.bhuyanp.gradle.theme.ThemeProvider;
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

public interface SpringBannerExtension extends ThemeProvider {

    String NAME = "springBanner";

    static void create(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        extensions.create(NAME, SpringBannerExtension.class);
    }

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

    Property<ThemeBuilder> getBannerTheme();
    default ThemeBuilder getBannerThemeValues() {
        return getBannerTheme().getOrElse(getBannerTheme(getThemeValues()));
    }


    Property<String> getCaption();
    default String getCaptionValue(Project project) {
        return getCaption().getOrElse("""
                | Version: %s
                | SpringBoot Version: %s
                | Java Version: %s
                | Gradle Version: %s
                """.formatted(project.getVersion(), SPRING_BOOT_VERSION, JavaVersion.current(), ((DefaultGradleVersion) GradleVersion.current()).getVersion()));

    }

    Property<ThemeBuilder> getCaptionTheme();
    default ThemeBuilder getCaptionThemeValues() {
        return getCaptionTheme().getOrElse(getCaptionTheme(getThemeValues()));
    }

    String SPRING_BOOT_VERSION = "${spring-boot.version}";

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
