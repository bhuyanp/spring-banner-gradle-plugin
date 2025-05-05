package io.github.bhuyanp.gradle.tasks;


import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.ansi.Attribute;
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

    default String getBannerWCaption(SpringBannerExtension extension, Project project) {
        List<String> bannerFonts = extension.getBannerFontsValue();
        String bannerFont = "usaflag";
        if (bannerFonts.size() > 1) {
            bannerFont = bannerFonts.get(new Random().nextInt(bannerFonts.size()));
        } else if (bannerFonts.size() == 1) {
            bannerFont = bannerFonts.getFirst();
        }
        return getBannerWCaption(extension, project, bannerFont);
    }

    default String getBannerWCaption(SpringBannerExtension extension, Project project, String bannerFont) {
        String bannerText = extension.getTextValue(project);
        String captionText = extension.getCaptionValue(project);
        ThemePreset themePreset = extension.getThemePresetValue();
        ThemeConfig bannerTheme = extension.getBannerThemeValue() != null ? extension.getBannerThemeValue() : themePreset.getTheme().getBannerTheme();
        ThemeConfig captionTheme = extension.getCaptionThemeValue() != null ? extension.getCaptionThemeValue() : themePreset.getTheme().getCaptionTheme();
        return getBannerWCaption(bannerFont, bannerText, bannerTheme, captionText, captionTheme, themePreset == ThemePreset.SURPRISE_ME);
    }

    private String getBannerWCaption(String bannerFont, String bannerText, ThemeConfig bannerTheme, String captionText, ThemeConfig captionTheme, boolean printConfig) {
        bannerText = getBanner(bannerFont, bannerText, bannerTheme);
        captionText = getCaption(captionText, captionTheme);
        if (printConfig) {
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

    private String getBanner(String font, String text, ThemeConfig bannerTheme) {
        if (text.isBlank()) return text;
        System.out.println("  Banner Font: " + font);
        String banner = FigletBannerRenderer.SINGLETON.render(font, text);

        TextPadding textPadding = Theme.getBannerPadding(font);

        //For no background banners no left/right padding needed
        if (!bannerTheme.hasBackColor()) {
            textPadding = new TextPadding(textPadding.getTop(), 0, textPadding.getBottom(), 0);
        }

        System.out.println("  Banner Paddings: " + textPadding);
        banner = textPadding.apply(banner);
        banner = banner.lines()
                .map(line -> colorize(line, bannerTheme))
                .collect(Collectors.joining(System.lineSeparator()));
        return banner;
    }



    default String getCaption(String caption, ThemeConfig captionTheme) {
        if (caption.isBlank()) return caption;
        //For no background, padding not needed for captions
        boolean addPadding = captionTheme.hasBackColor();
        int biggestLineLength = caption.lines().map(line -> {
                    if (line.contains(SPRING_BOOT_VERSION)) {
                        return line.length() - SPRING_BOOT_VERSION.length() + 5;
                    } else {
                        return line.length();
                    }
                })
                .max(Integer::compareTo).get()+1;
        caption = caption.lines()
                .map(line -> {
                    if (line.contains(SPRING_BOOT_VERSION)) {
                        return line + DEFAULT_SPACING.repeat(biggestLineLength - (line.length() - SPRING_BOOT_VERSION.length() + 5));
                    } else {
                        return line + DEFAULT_SPACING.repeat(biggestLineLength - line.length());
                    }
                })
                .collect(Collectors.joining(System.lineSeparator()));
        if (addPadding) {
            caption = new TextPadding(1, 2, 1, 3).apply(caption);
        } else {
            caption = caption.lines().map(line->"| "+line).collect(Collectors.joining(System.lineSeparator()));
        }
        return colorize(caption, captionTheme);
    }

}
