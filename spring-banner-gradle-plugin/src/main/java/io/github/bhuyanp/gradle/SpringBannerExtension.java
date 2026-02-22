package io.github.bhuyanp.gradle;


import io.github.bhuyanp.gradle.common.Constants;
import io.github.bhuyanp.gradle.model.CaptionSetting;
import io.github.bhuyanp.gradle.theme.THEME_PRESET;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import java.util.List;

import static io.github.bhuyanp.gradle.common.Constants.DEFAULT_CAPTION_BULLET_STYLE;

/**
 * Extension for the plugin with various customization option
 */
public interface SpringBannerExtension {

    /**
     * Extension name
     */
    String NAME = "springBanner";

    static void create(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        extensions.create(NAME, SpringBannerExtension.class);
    }

    /**
     * Flag to generate top section of the banner. Default is true.
     */
    Property<Boolean> getGenerateBanner();

    default boolean getGenerateBannerValue() {
        return getGenerateBanner().getOrElse(true);
    }

    /**
     * Flag to generate top section(caption) of the banner. Default is true.
     */
    Property<Boolean> getGenerateCaption();

    default boolean getGenerateCaptionValue() {
        return getGenerateCaption().getOrElse(true);
    }

    /**
     * Custom banner text. Default is project name(capitalized).
     */
    Property<String> getBannerText();

    default String getBannerTextValue(String projectName) {
        return getBannerText().getOrElse(projectName);
    }

    /**
     * Theme preset to use for the banner and caption styling. Default is SURPRISE_ME. Other options include DARK, LIGHT, SURPRISE_ME_LIGHT etc.
     *
     * @see THEME_PRESET
     */
    Property<THEME_PRESET> getThemePreset();

    default THEME_PRESET getThemePresetValue() {
        return getThemePreset().getOrElse(THEME_PRESET.SURPRISE_ME);
    }

    /**
     * One or more fonts to use for the banner text. Default is a random font from the list of available fonts. You can specify multiple fonts and the plugin will randomly pick one for each build.
     * <br/>
     * By default, plugin uses a default set of fonts that are tested to work well with the banner text.
     */
    ListProperty<String> getBannerFonts();

    default List<String> getBannerFontsValue() {
        return getBannerFonts().get().isEmpty() ? Constants.DEFAULT_FONTS : getBannerFonts().get();
    }

    /**
     * Caption settings for the fine grain control over banner caption. It includes options like caption bullet style, whether to hide several versions etc.
     *
     * @see CaptionSetting
     */
    Property<CaptionSetting> getCaptionSetting();

    default CaptionSetting getCaptionSettingValue() {
        return getCaptionSetting().getOrElse(CaptionSetting.builder()
                //.hideActiveProfiles(true)
                .captionBulletStyle(DEFAULT_CAPTION_BULLET_STYLE)
                .build());
    }

}
