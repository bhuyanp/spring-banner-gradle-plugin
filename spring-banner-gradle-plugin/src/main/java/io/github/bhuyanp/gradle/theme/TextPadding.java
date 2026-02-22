package io.github.bhuyanp.gradle.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;

import static io.github.bhuyanp.gradle.common.Constants.BLANK;

@Getter
@AllArgsConstructor
public class TextPadding {
    private final int top;
    private final int right;
    private final int bottom;
    private final int left;
    private static final String DEFAULT_SPACING = " ";

    public boolean hasPadding(){
        return top>0 || right>0 || bottom>0 || left>0;
    }

    public String apply(String subject){
        if(subject.isBlank() || !hasPadding()) return subject;
        if (left > 0 || right>0) {
            subject = subject.lines()
                    .map(line -> DEFAULT_SPACING.repeat(left) + line + DEFAULT_SPACING.repeat(right))
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        int subjectWidth = subject.lines().findFirst().orElse(BLANK).length();
        if (top > 0) {
            subject = (DEFAULT_SPACING.repeat(subjectWidth) + System.lineSeparator()).repeat(top)
                    + subject;
        }
        if (bottom > 0) {
            subject = subject
                    + (System.lineSeparator() + DEFAULT_SPACING.repeat(subjectWidth)).repeat(bottom);
        }
        return subject;
    }

    public static TextPadding getBannerPadding(String font) {
        return switch (font) {
            case "3d" -> new TextPadding(1, 4, 1, 3);
            case "3dascii" -> new TextPadding(2, 2, 0, 2);
            case "3ddiagonal" -> new TextPadding(0, 2, 1, 3);
            case "5lineoblique" -> new TextPadding(0, 1, 1, 1);
            case "ansiregular" -> new TextPadding(2, 4, 0, 4);
            case "ansishadow" -> new TextPadding(1, 4, 0, 4);
            case "basic" -> new TextPadding(2, 3, 0, 3);
            case "block" -> new TextPadding(0, 3, 1, 3);
            case "bigmoneyne" -> new TextPadding(3, 3, 0, 3);
            case "bigmoneyne-withtall" -> new TextPadding(1, 3, 1, 3);
            case "bolger" -> new TextPadding(1, 3, 1, 4);
            case "braced" -> new TextPadding(1, 3, 0, 4);
            case "broadway" -> new TextPadding(0, 3, 1, 3);
            case "broadway_kb" -> new TextPadding(0, 3, 1, 3);
            case "bulbhead" -> new TextPadding(0, 3, 1, 3);
            case "calvins" -> new TextPadding(1, 4, 1, 4);
            case "colossal" -> new TextPadding(1, 4, 1, 4);
            case "computer" -> new TextPadding(1, 2, 0, 3);
            case "doom" -> new TextPadding(1, 3, 0, 3);
            case "elite" -> new TextPadding(1, 4, 1, 4);
            case "epic" -> new TextPadding(1, 4, 1, 4);
            case "georgia11" -> new TextPadding(0, 3, 1, 3);
            case "lean" -> new TextPadding(1, 2, 0, 4);
            case "lean-withtall" -> new TextPadding(0, 2, 1, 4);
            case "poison" -> new TextPadding(0, 2, 0, 3);
            case "soft" -> new TextPadding(0, 2, 1, 3);
            case "starwars" -> new TextPadding(1, 2, 1, 2);
            case "stop-withtall" -> new TextPadding(1, 3, 2, 3);
            case "swan" -> new TextPadding(0, 4, 2, 4);
            case "thin" -> new TextPadding(0, 4, 1, 4);
            case "usaflag" -> new TextPadding(1, 3, 0, 2);
            case "univers" -> new TextPadding(0, 3, 1, 3);
            case "whimsy" -> new TextPadding(1, 4, 1, 4);
            default -> new TextPadding(1, 3, 1, 3);
        };
    }
    @Override
    public String toString(){
        return "[%s,%s,%s,%s]".formatted(top,right,bottom,left);
    }
}
