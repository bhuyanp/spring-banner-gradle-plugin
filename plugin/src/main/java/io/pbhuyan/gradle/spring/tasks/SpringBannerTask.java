package io.pbhuyan.gradle.spring.tasks;

import com.diogonunes.jcolor.AnsiFormat;
import io.pbhuyan.gradle.spring.SpringBannerExtension;
import io.pbhuyan.gradle.spring.figlet.FigletBannerRenderer;
import io.pbhuyan.gradle.spring.figlet.Fonts;
import io.pbhuyan.gradle.spring.theme.ThemeFormat;
import org.gradle.api.Project;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;

public interface SpringBannerTask {
    String GROUP = "Spring Banner Generator";

    default String getBanner(SpringBannerExtension extension, Project project, FigletBannerRenderer renderer) {
        List<String> fontsValue = extension.getFontsValue();
        String font = "usaflag";
        if (fontsValue.size() > 1) {
            font = fontsValue.get(new Random().nextInt(fontsValue.size()));
        } else if(fontsValue.size() == 1) {
            font = fontsValue.getFirst();
        }
        return getBanner(extension, project, renderer, font);
    }

    default String getBanner(SpringBannerExtension extension, Project project, FigletBannerRenderer renderer, String font) {
        String text = extension.getTextValue(project);
        String caption = extension.getCaptionValue(project);
        AnsiFormat bannerTheme = extension.getBannerThemeValues();
        ThemeFormat captionTheme = extension.getCaptionThemeValues();


        System.out.println("Banner generated with font: " + font);
        String banner = renderer.render(font, text);
        banner = addPadding(font, banner);
        banner = applyBannerTheme(banner, bannerTheme);

        caption = applyCaptionTheme(caption, captionTheme);

        String result = caption.isBlank()
                ? banner
                : banner + System.lineSeparator() + System.lineSeparator() + caption;
        return System.lineSeparator() + result;
    }

    String DEFAULT_SPACING = " ";
    String DEFAULT_HORIZONTAL_SPACING = DEFAULT_SPACING.repeat(2);


    default String addPadding(String font, String banner) {
        if (banner.isBlank()) return banner;
        List<Integer> fontPaddings = Fonts.getPadding(font);
        System.out.println("Font paddings: " + fontPaddings);
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

    default String applyBannerTheme(String banner, AnsiFormat bannerTheme) {
        if (banner.isBlank()) return banner;
        return banner.lines()
                .map(line -> colorize(line, bannerTheme))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    default String applyCaptionTheme(String caption, ThemeFormat captionTheme) {
        if (caption.isBlank()) return caption;
        System.out.println(captionTheme.hasBackColor());
        return colorize(caption, captionTheme);
    }

}
