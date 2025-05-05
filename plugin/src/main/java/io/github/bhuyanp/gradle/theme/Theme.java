package io.github.bhuyanp.gradle.theme;

import io.github.bhuyanp.gradle.ansi.Attribute;

import java.util.List;
import java.util.Random;

import static io.github.bhuyanp.gradle.ansi.Ansi.colorize;
import static io.github.bhuyanp.gradle.ansi.Attribute.*;

/**
 * Theme consists of banner and caption theme config. 3 predefined themes available to choose from.
 *
 * @author Prasanta Bhuyan
 * @since 1.0
 */
public final class Theme {

    static final Theme DARK = new Theme(
            new ThemeConfig(TEXT_COLOR(230, 230, 230), BACK_COLOR(45, 45, 45), BOLD()),
            new ThemeConfig(CYAN_TEXT(), NONE())
    );

    static final Theme LIGHT = new Theme(
            new ThemeConfig(TEXT_COLOR(60, 60, 60), BACK_COLOR(242, 242, 242), BOLD()),
            new ThemeConfig(TEXT_COLOR(0, 120, 120), NONE())
    );

    static final Theme SURPRISE_ME = new Theme();

    private ThemeConfig _bannerTheme;
    private ThemeConfig _captionTheme;

    Theme(ThemeConfig bannerTheme, ThemeConfig captionTheme) {
        this._captionTheme = captionTheme;
        this._bannerTheme = bannerTheme;
    }

    Theme() {
    }


    public ThemeConfig getBannerTheme() {
        return (null != _bannerTheme) ? _bannerTheme : new ThemeConfig(
                getRandomAttribute(bannerTextColors),
                getRandomAttribute(bannerBackColors),
                getRandomAttribute(bannerEffects));
    }

    public ThemeConfig getCaptionTheme() {
        return (null != _captionTheme) ? _captionTheme : new ThemeConfig(
                getRandomAttribute(bannerTextColors),
                getRandomAttribute(captionBackColors),
                getRandomAttribute(captionEffects));

    }


    static final List<Attribute> bannerTextColors = List.of(
            BRIGHT_BLUE_TEXT(),
            BRIGHT_MAGENTA_TEXT(),
            BRIGHT_CYAN_TEXT(),
            BRIGHT_WHITE_TEXT(),
            BRIGHT_RED_TEXT(),
            BRIGHT_BLUE_TEXT(),
            TEXT_COLOR(230, 230, 230),
            TEXT_COLOR(252, 195, 185),//melon
            TEXT_COLOR(113, 236, 241),//Electric blue
            TEXT_COLOR(231, 247, 225),//honey dew
            TEXT_COLOR(246, 191, 255)//mauve
    );

    static final List<Attribute> bannerBackColors = List.of(BLACK_BACK(),
            NONE(),
            NONE(),
            NONE(),
            RED_BACK(),
            GREEN_BACK(),
            YELLOW_BACK(),
            BLUE_BACK(),
            MAGENTA_BACK(),
            BLACK_BACK(),
            BLACK_BACK(),
            CYAN_BACK(),
            BRIGHT_BLACK_BACK(),
            BACK_COLOR(58, 68, 79),
            BACK_COLOR(6, 20, 57)
    );

    static final List<Attribute> captionBackColors = List.of(
            NONE(),
            NONE(),
            NONE(),
            NONE(),
            NONE(),
            NONE(),
            NONE(),
            BLACK_BACK(),
            BLACK_BACK(),
            BRIGHT_BLACK_BACK(),
            BRIGHT_BLACK_BACK(),
            BACK_COLOR(72, 69, 76)
    );


    static final List<Attribute> bannerEffects = List.of(
            NONE(),
            BOLD(),
            BOLD(),
            FRAMED(),
            ENCIRCLED(),
            DIM(),
            DIM(),
            DIM(),
            DESATURATED(),
            DESATURATED(),
            DESATURATED(),
            SATURATED(),
            SATURATED(),
            SATURATED()
    );


    static final List<Attribute> captionEffects = List.of(
            NONE(),
            BOLD(),
            BOLD(),
            FRAMED(),
            ENCIRCLED(),
            DIM(),
            ITALIC(),
            ITALIC(),
            ITALIC(),
            DESATURATED(),
            SATURATED(),
            SATURATED(),
            SATURATED()
    );


    private Attribute getRandomAttribute(List<Attribute> attributes) {
        return attributes.get(new Random().nextInt(attributes.size()));
    }

    public static TextPadding getBannerPadding(String font) {
        return switch (font) {
            case "3dascii" -> new TextPadding(2, 2, 0, 2);
            case "3ddiagonal" -> new TextPadding(0, 2, 1, 3);
            case "5lineoblique" -> new TextPadding(0, 1, 1, 1);
            case "ansiregular" -> new TextPadding(2, 4, 0, 4);
            case "ansishadow" -> new TextPadding(1, 4, 0, 4);
            case "basic" -> new TextPadding(2, 3, 0, 3);
            case "block" -> new TextPadding(0, 3, 1, 3);
            case "bolger" -> new TextPadding(1, 3, 1, 4);
            case "braced" -> new TextPadding(1, 3, 0, 4);
            case "broadway" -> new TextPadding(0, 3, 1, 3);
            case "broadway_kb" -> new TextPadding(0, 3, 1, 3);
            case "bulbhead" -> new TextPadding(0, 3, 1, 3);
            case "calvins" -> new TextPadding(1, 4, 1, 4);
            case "colossal" -> new TextPadding(1, 4, 1, 4);
            case "elite" -> new TextPadding(1, 4, 1, 4);
            case "epic" -> new TextPadding(1, 4, 1, 4);
            case "georgia11" -> new TextPadding(0, 3, 1, 3);
            case "lean" -> new TextPadding(0, 0, 1, 1);
            case "nancyj" -> new TextPadding(1, 3, 1, 2);
            case "poison" -> new TextPadding(0, 2, 0, 3);
            case "soft" -> new TextPadding(0, 2, 1, 3);
            case "starwars" -> new TextPadding(1, 2, 1, 2);
            case "swan" -> new TextPadding(0, 4, 2, 4);
            case "thin" -> new TextPadding(0, 4, 1, 4);
            case "usaflag" -> new TextPadding(1, 3, 0, 2);
            case "univers" -> new TextPadding(0, 3, 1, 3);
            case "whimsy" -> new TextPadding(1, 4, 1, 4);
            default -> new TextPadding(1, 3, 1, 3);
        };
    }

    public static void main(String[] args) {
        TextPadding textPadding = new TextPadding(1, 2, 1, 2);

        System.out.println("## Banner Text Colors:");
        bannerTextColors.stream().distinct().map(attribute -> colorize(textPadding.apply(attribute.construct()), attribute))
                .forEach(System.out::println);
        System.out.println("------");
        System.out.println("## Banner Back Colors:");
        bannerBackColors.stream().map(attribute -> colorize(textPadding.apply(attribute.construct()), attribute))
                .distinct()
                .forEach(System.out::println);
        System.out.println("------");
        System.out.println("## Caption Back Colors:");
        captionBackColors.stream().map(attribute -> colorize(textPadding.apply(attribute.construct()), attribute))
                .distinct()
                .forEach(System.out::println);
        System.out.println("------");
        System.out.println("## Banner Effects:");
        bannerEffects.stream().map(attribute -> colorize(textPadding.apply(attribute.construct()), attribute))
                .distinct()
                .forEach(System.out::println);
        System.out.println("------");
        System.out.println("## Caption Effects:");
        captionEffects.stream().map(attribute -> colorize(textPadding.apply(attribute.construct()), attribute))
                .distinct()
                .forEach(System.out::println);



    }


}
