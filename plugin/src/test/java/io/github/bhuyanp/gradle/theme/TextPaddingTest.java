package io.github.bhuyanp.gradle.theme;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TextPaddingTest {

    @Test
    void shouldNot_applyPadding() {
        //given
        TextPadding textPadding = new TextPadding(0,0,0,0);
        String subject = "text";
        //when
        String paddedText = textPadding.apply(subject);
        //then
        assertThat(paddedText).isNotBlank().isEqualTo(subject);
    }

    @Test
    void shouldNot_applyPaddingWithBlankSubject() {
        //given
        TextPadding textPadding = new TextPadding(1,1,0,0);
        String subject = "";
        //when
        String paddedText = textPadding.apply(subject);
        //then
        assertThat(paddedText).isBlank();
    }


    @Test
    void should_applyTopPadding() {
        //given
        TextPadding textPadding = new TextPadding(1,0,0,0);
        String subject = "Text";
        //when
        String paddedText = textPadding.apply(subject);
        List<String> paddedTextLines = paddedText.lines().toList();
        //then
        assertThat(paddedTextLines.get(0)).isBlank().isEqualTo("    ");
        assertThat(paddedTextLines.get(1)).isNotBlank().isEqualTo(subject);
    }

    @Test
    void should_applyBottomPadding() {
        //given
        TextPadding textPadding = new TextPadding(0,0,2,0);
        String subject = "Text";
        //when
        String paddedText = textPadding.apply(subject);
        List<String> paddedTextLines = paddedText.lines().toList();
        //then
        assertThat(paddedTextLines.get(0)).isNotBlank().isEqualTo(subject);
        assertThat(paddedTextLines.get(1)).isBlank().isEqualTo("    ");
        assertThat(paddedTextLines.get(2)).isBlank().isEqualTo("    ");
    }

    @Test
    void should_applyLeftPadding() {
        //given
        TextPadding textPadding = new TextPadding(0,0,0,2);
        String subject = "Text";
        //when
        String paddedText = textPadding.apply(subject);
        //then
        assertThat(paddedText).isNotBlank().isEqualTo("  "+subject);
    }

    @Test
    void should_applyRightPadding() {
        //given
        TextPadding textPadding = new TextPadding(0,3,0,0);
        String subject = "Text";
        //when
        String paddedText = textPadding.apply(subject);
        //then
        assertThat(paddedText).isNotBlank().isEqualTo(subject+"   ");
    }

    @Test
    void should_applyAllPadding() {
        //given
        TextPadding textPadding = new TextPadding(2,3,1,2);
        String subject = "Text";
        //when
        String paddedText = textPadding.apply(subject);
        List<String> paddedTextLines = paddedText.lines().toList();

        //then
        assertThat(paddedText).hasLineCount(4);
        assertThat(paddedTextLines.get(0)).isBlank().isEqualTo("         ");
        assertThat(paddedTextLines.get(1)).isBlank().isEqualTo("         ");
        assertThat(paddedTextLines.get(2)).isNotBlank().isEqualTo("  Text   ");
        assertThat(paddedTextLines.get(3)).isBlank().isEqualTo("         ");
    }
}