package io.github.bhuyanp.gradle.theme;

import io.github.bhuyanp.gradle.SpringBannerExtension;
import io.github.bhuyanp.gradle.common.Constants;
import io.github.bhuyanp.gradle.figlet.FigletBannerRenderer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.bhuyanp.gradle.ansi.Ansi.colorize;
import static io.github.bhuyanp.gradle.common.Constants.BLANK;
import static io.github.bhuyanp.gradle.common.Constants.FONTS_WITH_PADDING_CORRECTION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringBannerGenerator {
    public static final SpringBannerGenerator INSTANCE = new SpringBannerGenerator();

    public String generate(SpringBannerExtension springBannerExtension, String projectName) {
        if(!springBannerExtension.getGenerateBannerValue()) return BLANK;
        String bannerFont = getBannerFont(springBannerExtension);
        return generate(springBannerExtension, projectName, bannerFont);
    }

    public String generate(SpringBannerExtension springBannerExtension, String projectName, String bannerFont) {
        if(!springBannerExtension.getGenerateBannerValue()) return BLANK;
        String text = springBannerExtension.getBannerTextValue(projectName).trim();
        if (text.isBlank()) return text;
        THEME_PRESET themePreset = springBannerExtension.getThemePresetValue();
        ThemeConfig bannerTheme = Theme.getBannerTheme(themePreset);


        text = capitalizeProjectName(text);
        String banner = FigletBannerRenderer.SINGLETON.render(bannerFont, text);
        TextPadding textPadding;
        //For no background banners no left/right padding needed
        if (bannerTheme.hasBackColor()||bannerTheme.hasFrame()) {
            textPadding = TextPadding.getBannerPadding(bannerFont);
            if(FONTS_WITH_PADDING_CORRECTION.contains(bannerFont) && hasLowerCaseTallCharacter(text)){
                textPadding = TextPadding.getBannerPadding(bannerFont+"-withtall");
            }
            banner = textPadding.apply(banner);
        }
        banner = colorize(banner, bannerTheme);
        return banner;
    }


    boolean hasLowerCaseTallCharacter(String text){
        boolean hasL = false;
        for (char c : text.toCharArray()) {
            hasL = switch (c){
                case 'g', 'j', 'p', 'q', 'y' -> true;
                default -> false;
            };
            if(hasL)break;
        }
        return hasL;
    }


    private String getBannerFont(SpringBannerExtension springBannerExtension) {
        List<String> bannerFonts = springBannerExtension.getBannerFontsValue();
        if(bannerFonts.isEmpty()){
            bannerFonts = Constants.DEFAULT_FONTS;
        }
        if(bannerFonts.size()==1){
            return bannerFonts.get(0);
        }
        return bannerFonts.get(new java.util.Random().nextInt(bannerFonts.size()));
    }

    /**
     *
     * @param projectName Name of the project
     * @return The capitalized project name and dashes replaces with spaces
     */
    private String capitalizeProjectName(String projectName) {
        return Arrays.stream(projectName.replace('-', ' ').split(" ")).map(name ->
                        name.substring(0, 1).toUpperCase() + name.substring(1))
                .collect(Collectors.joining(" "));
    }


}
