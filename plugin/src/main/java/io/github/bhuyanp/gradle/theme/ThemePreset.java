package io.github.bhuyanp.gradle.theme;

import lombok.Getter;

@Getter
public enum ThemePreset {
    DARK(Theme.DARK), LIGHT(Theme.LIGHT), SURPRISE_ME(Theme.SURPRISE_ME);
    private final Theme theme;
    ThemePreset(Theme theme) {
        this.theme = theme;
    }
}
