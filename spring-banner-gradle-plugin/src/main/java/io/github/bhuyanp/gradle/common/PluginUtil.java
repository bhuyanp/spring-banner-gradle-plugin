package io.github.bhuyanp.gradle.common;

import java.util.Optional;

public class PluginUtil {

    public static boolean isBlankString(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String addPadding(String subject, int desiredLength) {
        Optional.ofNullable(subject).orElseThrow();
        int subjectLength = subject.length();
        if (desiredLength < 1 || subjectLength >= desiredLength) return subject;
        int paddingLength = desiredLength - subjectLength;
        return subject + String.format("%-" + paddingLength + "s", " ");
    }
}
