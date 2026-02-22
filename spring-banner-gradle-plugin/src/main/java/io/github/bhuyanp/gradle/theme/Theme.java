package io.github.bhuyanp.gradle.theme;

import io.github.bhuyanp.gradle.ansi.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

import static io.github.bhuyanp.gradle.ansi.Attribute.*;

/**
 * Theme consists of banner and caption theme config. 3 predefined themes available to choose from.
 *
 * @author Prasanta Bhuyan
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public final class Theme {

    static final Theme DARK = new Theme(
            new ThemeConfig(TEXT_COLOR(232, 232, 232), BACK_COLOR(40, 40, 40), BOLD()),
            new ThemeConfig(CYAN_TEXT(), NONE())
    );

    static final Theme LIGHT = new Theme(
            new ThemeConfig(TEXT_COLOR(50, 50, 50), BACK_COLOR(245, 245, 245), BOLD()),
            new ThemeConfig(TEXT_COLOR(0, 120, 120), NONE())
    );

    private ThemeConfig bannerTheme;
    private ThemeConfig captionTheme;

    Theme(ThemeConfig bannerTheme, ThemeConfig captionTheme) {
        this.captionTheme = captionTheme;
        this.bannerTheme = bannerTheme;
    }

    public Theme(final List<Integer> bannerTextColors, final List<Integer> bannerBackColors, final boolean bold, final ADDITIONAL_EFFECT additionalEffect) {
        this.bannerTheme = new ThemeConfig(
                TEXT_COLOR(bannerTextColors.get(0), bannerTextColors.get(1), bannerTextColors.get(2)),
                BACK_COLOR(bannerBackColors.get(0), bannerBackColors.get(1), bannerBackColors.get(2)),
                getAdditionalEffect(additionalEffect));
        if (bold) {
            this.bannerTheme.addBold();
        }
    }

    public Theme(final List<Integer> bannerTextColors, final boolean bold, final ADDITIONAL_EFFECT additionalEffect) {
        this.bannerTheme = new ThemeConfig(
                TEXT_COLOR(bannerTextColors.get(0), bannerTextColors.get(1), bannerTextColors.get(2)),
                NONE(),
                getAdditionalEffect(additionalEffect));
        if (bold) {
            this.bannerTheme.addBold();
        }

    }

    private Attribute getAdditionalEffect(ADDITIONAL_EFFECT additionalEffect) {
        return switch (additionalEffect) {
            case NONE -> NONE();
            case FRAMED -> FRAMED();
            case ENCIRCLED -> ENCIRCLED();
            case DIM -> DIM();
            case DESATURATED -> DESATURATED();
            case SATURATED -> SATURATED();
        };
    }

    public enum ADDITIONAL_EFFECT {
        NONE,
        SATURATED,
        DESATURATED,
        DIM,
        FRAMED,
        ENCIRCLED
    }

    public static ThemeConfig getBannerTheme(THEME_PRESET selectedTheme) {
        return switch (selectedTheme) {
            case DARK -> DARK.bannerTheme;
            case LIGHT -> LIGHT.bannerTheme;
            case SURPRISE_ME -> getRandomBannerTheme(true);
            case SURPRISE_ME_LIGHT -> getRandomBannerTheme(false);
        };
    }

    public static ThemeConfig getCaptionTheme(THEME_PRESET selectedTheme) {
        boolean isDarkMode = true;
        return switch (selectedTheme) {
            case DARK -> DARK.captionTheme;
            case LIGHT -> LIGHT.captionTheme;
            case SURPRISE_ME -> new ThemeConfig(
                    getRandomAttribute(brightTextColors) ,
                    NONE(),
                    NONE());
            case SURPRISE_ME_LIGHT -> new ThemeConfig(
                    getRandomAttribute(darkTextColors),
                    NONE(),
                    NONE());
        };
    }

    private static ThemeConfig getRandomBannerTheme(boolean isDarkMode) {
        boolean useAlternative = new Random().nextInt(10) == 0;
        Attribute textColor;
        Attribute backgroundColor;
        if (useAlternative) {
            textColor = !isDarkMode ? getRandomAttribute(brightTextColors) : getRandomAttribute(darkTextColors);
            backgroundColor = !isDarkMode ? getRandomAttribute(darkBackgroundColors, NONE()) : getRandomAttribute(brightBackgroundColors, NONE());

        } else {
            textColor = isDarkMode ? getRandomAttribute(brightTextColors) : getRandomAttribute(darkTextColors);
            backgroundColor = isDarkMode ? getRandomAttribute(darkBackgroundColors) : getRandomAttribute(brightBackgroundColors);
        }

        boolean addBold = new Random().nextInt(5) == 0;
        ThemeConfig themeConfig = new ThemeConfig(
                textColor,
                backgroundColor,
                getRandomBannerEffect()
        );
        if (addBold) {
            themeConfig.addBold();
        }
        return themeConfig;
    }

    // https://imagecolorpicker.com/
    private static final List<Attribute> darkBackgroundColors = List.of(
            NONE(),
            NONE(),
            BLACK_BACK(),
            BLACK_BACK(),
            BACK_COLOR(141, 51, 51), //El Salva
            BACK_COLOR(61, 83, 118), // East Bay
            BACK_COLOR(13, 38, 46), //Firefly
            BACK_COLOR(14, 8, 42),  //Violet
            BACK_COLOR(38, 39, 58),    //Ebony Clay
            BACK_COLOR(26, 27, 31),   //Shark
            BACK_COLOR(26, 27, 31),   //Shark
            BACK_COLOR(33, 10, 14),  //Coffee Bean
            BACK_COLOR(42, 27, 8),   //Graphite
            BACK_COLOR(45, 45, 45)
    );

    private static final List<Attribute> brightTextColors = List.of(
            TEXT_COLOR(127, 180, 255),  // Malibu
            TEXT_COLOR(142, 189, 255),  // Anakiwa
            TEXT_COLOR(220, 240, 255),  // Patterns Blue
            TEXT_COLOR(237, 255, 220),  // Rice Flower
            TEXT_COLOR(249, 249, 249),  // Albaster
            TEXT_COLOR(240, 240, 240),  // Gallery
            TEXT_COLOR(205, 214, 220),  // Geyser
            TEXT_COLOR(255, 220, 220),   // Cosmos
            TEXT_COLOR(230, 230, 230),
            BRIGHT_BLUE_TEXT(),
            BRIGHT_BLUE_TEXT(),
            BRIGHT_MAGENTA_TEXT(),
            BRIGHT_CYAN_TEXT(),
            BRIGHT_WHITE_TEXT(),
            TEXT_COLOR(112, 255, 221)//Aqua marine
    );

    private static final List<Attribute> brightBackgroundColors = List.of(
            NONE(),
            NONE(),
            BRIGHT_WHITE_BACK(),
            BACK_COLOR(127, 180, 255),  // Malibu
            BACK_COLOR(142, 189, 255),  // Anakiwa
            BACK_COLOR(220, 240, 255),  // Patterns Blue
            BACK_COLOR(237, 255, 220),  // Rice Flower
            BACK_COLOR(249, 249, 249),  // Albaster
            BACK_COLOR(240, 240, 240),  // Gallery
            BACK_COLOR(205, 214, 220),  // Geyser
            BACK_COLOR(255, 220, 220)   // Cosmos
    );

    private static final List<Attribute> darkTextColors = List.of(
            BLACK_TEXT(),
            TEXT_COLOR(141, 51, 51), //El Salva
            TEXT_COLOR(31, 99, 147), //Matisse
            TEXT_COLOR(21, 62, 75), //Elephant
            TEXT_COLOR(44, 29, 110),  //Melorite
            TEXT_COLOR(56, 58, 81),    //Bright Gray
            TEXT_COLOR(26, 27, 31),   //Shark
            TEXT_COLOR(33, 10, 14),  //Coffee Bean
            TEXT_COLOR(42, 27, 8),   //Graphite
            TEXT_COLOR(0, 110, 110)
    );


    private static final List<Attribute> bannerEffects = List.of(
            NONE(),
            NONE(),
            NONE(),
            FRAMED(),
            ENCIRCLED(),
            DIM(),
            DESATURATED(),
            DESATURATED(),
            SATURATED(),
            SATURATED(),
            SATURATED(),
            SATURATED()
    );

    private static Attribute getRandomBannerEffect() {
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        if (randomNumber < 9) {
            return FRAMED();
        } else if (randomNumber < 18) {
            return ENCIRCLED();
        } else if (randomNumber < 30) {
            return DIM();
        } else if (randomNumber < 45) {
            return DESATURATED();
        } else if (randomNumber < 70) {
            return NONE();
        } else {
            return SATURATED();
        }
    }

    private static Attribute getRandomAttribute(List<Attribute> attributes) {
        return attributes.get(new Random().nextInt(attributes.size()));
    }

    private static Attribute getRandomAttribute(List<Attribute> attributes, Attribute exclude) {
        List<Attribute> filteredAttributeList = attributes.stream().filter(at -> !at.construct().equalsIgnoreCase(exclude.construct()))
                .toList();
        return filteredAttributeList.get(new Random().nextInt(filteredAttributeList.size()));
    }

}
