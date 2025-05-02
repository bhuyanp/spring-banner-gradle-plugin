package io.github.bhuyanp.gradle.theme;

import com.diogonunes.jcolor.Attribute;

import java.util.List;
import java.util.Random;

import static com.diogonunes.jcolor.Attribute.*;

public interface ThemeProvider {

    default ThemeBuilder getBannerTheme(Theme theme) {
        return switch (theme) {
            case DARK -> new ThemeBuilder(BRIGHT_WHITE_TEXT(), BACK_COLOR(45, 45, 45), BOLD());
            case LIGHT -> new ThemeBuilder(TEXT_COLOR(65, 65, 65), BACK_COLOR(245, 245, 245), BOLD());
            default -> new ThemeBuilder(randomTextColor(), randomBackColor(), randomEffect());
        };
    }

    default ThemeBuilder getCaptionTheme(Theme theme) {
        return switch (theme) {
            case DARK -> new ThemeBuilder(CYAN_TEXT());
            case LIGHT -> new ThemeBuilder(TEXT_COLOR(0, 125, 125));
            default -> new ThemeBuilder(randomTextColor(), randomCaptionBackColor(), randomCaptionEffect());
        };
    }

    default Attribute randomTextColor() {
        List<Attribute> textColors = List.of(
                BRIGHT_BLUE_TEXT(),
                BRIGHT_MAGENTA_TEXT(),
                BRIGHT_CYAN_TEXT(),
                BRIGHT_WHITE_TEXT(),
                BRIGHT_RED_TEXT(),
                BRIGHT_BLUE_TEXT(),
                BRIGHT_YELLOW_TEXT(),
                TEXT_COLOR(252,195, 147),
                TEXT_COLOR(113,236, 241)
                );
        return textColors.get(new Random().nextInt(textColors.size()));
    }

    default Attribute randomBackColor() {
        List<Attribute> backColors = List.of(BLACK_BACK(),
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
                BACK_COLOR(58,68,79),
                BACK_COLOR(6,20,57)
                );
        return backColors.get(new Random().nextInt(backColors.size()));
    }

    default Attribute randomCaptionBackColor() {
        List<Attribute> backColors = List.of(
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
                BACK_COLOR(72,69,76)
        );
        return backColors.get(new Random().nextInt(backColors.size()));
    }

    default Attribute randomEffect() {
        //List<Attribute> effects=List.of(FRAMED());
        List<Attribute> effects = List.of(
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
        return effects.get(new Random().nextInt(effects.size()));
    }

    default Attribute randomCaptionEffect() {
        List<Attribute> effects = List.of(
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
        return effects.get(new Random().nextInt(effects.size()));
    }

}
