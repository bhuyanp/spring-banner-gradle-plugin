package io.github.bhuyanp.gradle.tasks;


import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.figlet.FigletBannerRenderer;
import io.github.bhuyanp.gradle.figlet.Fonts;
import io.github.bhuyanp.gradle.theme.Theme;
import io.github.bhuyanp.gradle.theme.ThemeBuilder;
import org.gradle.api.Project;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;
import static io.github.bhuyanp.gradle.SpringBannerExtension.SPRING_BOOT_VERSION;

public interface SpringBannerTask {
    String GROUP = "Spring Banner Generator";

    default String getBanner(SpringBannerExtension extension, Project project, FigletBannerRenderer renderer) {
        List<String> bannerFonts = extension.getBannerFontsValue();
        String font = "usaflag";
        if (bannerFonts.size() > 1) {
            font = bannerFonts.get(new Random().nextInt(bannerFonts.size()));
        } else if (bannerFonts.size() == 1) {
            font = bannerFonts.getFirst();
        }
        return getBanner(extension, project, renderer, font);
    }

    default String getBanner(SpringBannerExtension extension, Project project, FigletBannerRenderer renderer, String font) {
        String text = extension.getTextValue(project);
        String caption = extension.getCaptionValue(project);
        Theme theme = extension.getThemeValues();
        ThemeBuilder bannerTheme = extension.getBannerThemeValues();
        ThemeBuilder captionTheme = extension.getCaptionThemeValues();

        System.out.println("Banner font: " + font);
        String banner = renderer.render(font, text);
        banner = addBannerPadding(font, banner);
        banner = applyBannerTheme(banner, bannerTheme);

        caption = addCaptionPadding(caption);
        caption = applyCaptionTheme(caption, captionTheme);

        if (theme == Theme.SURPRISE_ME) {
            System.out.println("Banner Theme: " + bannerTheme);
            System.out.println("Caption Theme: " + captionTheme);
        }
        String result = caption.isBlank()
                ? banner
                : banner + System.lineSeparator() + System.lineSeparator() + caption;
        return System.lineSeparator() + result + System.lineSeparator();
    }

    String DEFAULT_SPACING = " ";
    String DEFAULT_HORIZONTAL_SPACING = DEFAULT_SPACING.repeat(2);


    default String addBannerPadding(String font, String banner) {
        if (banner.isBlank()) return banner;
        List<Integer> fontPaddings = Fonts.getPadding(font);
        System.out.println("Banner paddings: " + fontPaddings);
        banner = banner.lines()
                .map(line -> DEFAULT_HORIZONTAL_SPACING.repeat(fontPaddings.getLast()) + line + DEFAULT_HORIZONTAL_SPACING.repeat(fontPaddings.get(1)))
                .collect(Collectors.joining(System.lineSeparator()));
        int bannerWidth = banner.lines().findFirst().get().length();
        if (fontPaddings.getFirst() > 0) {
            banner = (DEFAULT_SPACING.repeat(bannerWidth) + System.lineSeparator()).repeat(fontPaddings.getFirst())
                    + banner;
        }
        if (fontPaddings.get(2) > 0) {
            banner = banner
                    + System.lineSeparator()
                    + (DEFAULT_SPACING.repeat(bannerWidth) + System.lineSeparator()).repeat(fontPaddings.get(2));
        }
        return banner;
    }

    default String applyBannerTheme(String banner, ThemeBuilder bannerTheme) {
        if (banner.isBlank()) return banner;
        return banner.lines()
                .map(line -> colorize(line, bannerTheme))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    default String addCaptionPadding(String caption) {
        if (caption.isBlank()) return caption;
        int biggestLine = caption.lines().map(String::length).max(Integer::compare).get();
        return caption.lines()
                .map(line ->
                        line.contains(SPRING_BOOT_VERSION) ?
                                line + DEFAULT_SPACING.repeat(biggestLine - SPRING_BOOT_VERSION.length() - 5) + DEFAULT_SPACING :
                                line + DEFAULT_SPACING.repeat(biggestLine - line.length()) + DEFAULT_SPACING)
                .map(line -> DEFAULT_SPACING + line)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    default String applyCaptionTheme(String caption, ThemeBuilder captionTheme) {
        if (caption.isBlank()) return caption;
        return colorize(caption, captionTheme);
    }

}
