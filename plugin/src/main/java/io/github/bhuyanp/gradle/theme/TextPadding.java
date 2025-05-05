package io.github.bhuyanp.gradle.theme;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Collectors;

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
        int bannerWidth = subject.lines().findFirst().get().length();
        if (top > 0) {
            subject = (DEFAULT_SPACING.repeat(bannerWidth) + System.lineSeparator()).repeat(top)
                    + subject;
        }
        if (bottom > 0) {
            subject = subject
                    + System.lineSeparator()
                    + (DEFAULT_SPACING.repeat(bannerWidth) + System.lineSeparator()).repeat(bottom);
        }
        return subject;
    }
    @Override
    public String toString(){
        return "[%s,%s,%s,%s]".formatted(top,right,bottom,left);
    }
}
