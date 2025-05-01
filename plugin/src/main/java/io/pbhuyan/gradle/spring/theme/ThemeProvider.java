package io.pbhuyan.gradle.spring.theme;

import static com.diogonunes.jcolor.Attribute.*;

public interface ThemeProvider {

    default ThemeFormat getBannerTheme(Theme theme){
        return switch (theme){
            case DARK -> new ThemeFormat(BRIGHT_WHITE_TEXT(), BACK_COLOR(45, 45, 45), BOLD());
            case LIGHT -> new ThemeFormat(TEXT_COLOR(65, 65 , 65), BACK_COLOR(245, 245, 245), BOLD());
        };
    }

    default ThemeFormat getCaptionTheme(Theme theme){
        return switch (theme){
            case DARK -> new ThemeFormat(CYAN_TEXT());
            case LIGHT -> new ThemeFormat(TEXT_COLOR(0, 125 , 125));
        };
    }

}
