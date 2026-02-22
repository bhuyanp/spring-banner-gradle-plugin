package io.github.bhuyanp.gradle.theme;

public enum THEME_PRESET {
    /*
     * This theme is suitable for dark console/terminal backgrounds. It uses bright colors for the banner and caption to ensure good contrast and readability.
     */
    DARK,
    /*
     * This theme is designed for light console/terminal backgrounds. It uses darker colors for the banner and caption to provide good contrast and readability.
     */
    LIGHT,
    /*
     * This theme randomizes banner colors, fonts and additional effects to create a unique and dynamic appearance each time the build is run. It adds an element of surprise and fun to the build process.
     * This is the default theme if no theme preset is specified. It is recommended to use this theme if you want to add some fun and excitement to your build process, or if you want to see a different banner each time you run the build.
     * for light console/terminal backgrounds consider using SURPRISE_ME_LIGHT preset instead.
     */
    SURPRISE_ME,
    /*
     * This theme randomizes banner colors, fonts and additional effects to create a unique and dynamic appearance each time the build is run. It adds an element of surprise and fun to the build process.
     * It is recommended to use this theme if you want to add some fun and excitement to your build process, or if you want to see a different banner each time you run the build.
     * For dark console/terminal backgrounds consider using SURPRISE_ME preset instead.
     */
    SURPRISE_ME_LIGHT
}