package io.github.bhuyanp.gradle.tasks;


import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.figlet.FigletBannerRenderer;
import io.github.bhuyanp.gradle.theme.TextPadding;
import io.github.bhuyanp.gradle.theme.Theme;
import io.github.bhuyanp.gradle.theme.ThemeConfig;
import io.github.bhuyanp.gradle.theme.ThemePreset;
import org.gradle.api.Project;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static io.github.bhuyanp.gradle.SpringBannerExtension.SPRING_BOOT_VERSION;
import static io.github.bhuyanp.gradle.ansi.Ansi.colorize;

public interface SpringBannerTask {
    String GROUP = "Spring Banner";

    default String getBannerWCaption(SpringBannerExtension extension, Project project, boolean printBannerConfig) {
        List<String> bannerFonts = extension.getBannerFontsValue();
        String bannerFont = "usaflag";
        if (bannerFonts.size() > 1) {
            bannerFont = bannerFonts.get(new Random().nextInt(bannerFonts.size()));
        } else if (bannerFonts.size() == 1) {
            bannerFont = bannerFonts.get(0);
        }
        return getBannerWCaption(extension, project, bannerFont, printBannerConfig);
    }

    default String getBannerWCaption(SpringBannerExtension extension, Project project, String bannerFont, boolean printBannerConfig) {
        String bannerText = extension.getTextValue(project);
        String captionText = extension.getCaptionValue(project);
        ThemePreset themePreset = extension.getThemePresetValue();
        ThemeConfig bannerTheme = extension.getBannerThemeValue() != null ? extension.getBannerThemeValue() : themePreset.getTheme().getBannerTheme();
        ThemeConfig captionTheme = extension.getCaptionThemeValue() != null ? extension.getCaptionThemeValue() : themePreset.getTheme().getCaptionTheme();
        return getBannerWCaption(bannerFont, bannerText, bannerTheme, captionText, captionTheme, printBannerConfig);
    }

    private String getBannerWCaption(String bannerFont, String bannerText, ThemeConfig bannerTheme, String captionText, ThemeConfig captionTheme, boolean printBannerConfig) {
        bannerText = getBanner(bannerFont, bannerText, bannerTheme, printBannerConfig);
        captionText = getCaption(captionText, captionTheme);
        if (printBannerConfig) {
            System.out.println("  bannerTheme = " + bannerTheme);
            if (!captionText.isBlank()) {
                System.out.println("  captionTheme = " + captionTheme);
            }
        }

        String result = captionText.isBlank()
                ? bannerText
                : bannerText + System.lineSeparator() + System.lineSeparator() + captionText;
        return System.lineSeparator() + result + System.lineSeparator();

    }

    String DEFAULT_SPACING = " ";

    private String getBanner(String font, String text, ThemeConfig bannerTheme, boolean printBannerConfig) {
        if (text.isBlank()) return text;
        if (printBannerConfig) {
            System.out.println(System.lineSeparator() + "  Banner Font: " + font);
        }
        String banner = FigletBannerRenderer.SINGLETON.render(font, text);

        //For no background banners no left/right padding needed
        if (bannerTheme.hasBackColor()) {
            TextPadding textPadding = Theme.getBannerPadding(font);
            if (printBannerConfig) {
                System.out.println("  Banner Paddings: " + textPadding);
            }
            banner = textPadding.apply(banner);
        }
        banner = banner.lines()
                .map(line -> colorize(line, bannerTheme))
                .collect(Collectors.joining(System.lineSeparator()));
        return banner;
    }


    default String getCaption(String caption, ThemeConfig captionTheme) {
        if (caption.isBlank()) return caption;

        // Remove spaces around caption lines
        caption = caption.lines().map(String::trim).collect(Collectors.joining(System.lineSeparator()));

        // Find the biggest line in caption
        int biggestLineLength = caption.lines().map(line -> {
                    if (line.contains(SPRING_BOOT_VERSION)) {
                        return line.length() - SPRING_BOOT_VERSION.length() + 5;
                    } else {
                        return line.length();
                    }
                })
                .max(Integer::compareTo).get();

        // Add spaces to smaller lines to match the biggestLineLength
        caption = caption.lines()
                .map(line -> {
                    if (line.contains(SPRING_BOOT_VERSION)) {
                        return line + DEFAULT_SPACING.repeat(biggestLineLength - (line.length() - SPRING_BOOT_VERSION.length() + 5));
                    } else {
                        return line + DEFAULT_SPACING.repeat(biggestLineLength - line.length());
                    }
                })
                .collect(Collectors.joining(System.lineSeparator()));

        //For no background, padding not needed for captions
        boolean addPadding = captionTheme.hasBackColor();
        if (addPadding) {
            caption = new TextPadding(1, 2, 1, 2).apply(caption);
        } else {
            caption = caption.lines().map(line -> "| " + line).collect(Collectors.joining(System.lineSeparator()));
        }
        return colorize(caption, captionTheme);
    }

    String LINE_SEPARATOR = System.lineSeparator() + "-".repeat(30);


}
