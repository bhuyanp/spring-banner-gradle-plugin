package io.github.bhuyanp.gradle.theme;

import org.junit.jupiter.api.Test;

import static io.github.bhuyanp.gradle.ansi.Attribute.*;
import static org.assertj.core.api.Assertions.assertThat;

class ThemeConfigTest {

    @Test
    void should_returnToString(){
        //given
        ThemeConfig themeConfig = new ThemeConfig(BRIGHT_CYAN_TEXT(), NONE(), DESATURATED());
        //when
        String themeString = themeConfig.toString();
        //then
        assertThat(themeString)
                .isNotBlank()
                .isEqualTo("ThemeConfig(BRIGHT_CYAN_TEXT(), NONE(), DESATURATED())");
    }

    @Test
    void should_haveBackColor(){
        //given
        ThemeConfig themeConfig = new ThemeConfig(BRIGHT_CYAN_TEXT(), BRIGHT_WHITE_BACK(), DESATURATED());
        //when
        //then
        assertThat(themeConfig.hasBackColor())
                .isTrue();
    }

    @Test
    void should_haveNotBackColor(){
        //given
        ThemeConfig themeConfig = new ThemeConfig(BRIGHT_CYAN_TEXT(), NONE(), DESATURATED());
        //when
        //then
        assertThat(themeConfig.hasBackColor())
                .isFalse();
    }

}