package io.github.bhuyanp.gradle.common;

import io.github.bhuyanp.gradle.model.CaptionSetting;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String SPACE = " ";
    public static final String BLANK = "";
    public static final String LINE_SEPARATOR = System.lineSeparator() + "-".repeat(100);

    public static final CaptionSetting.CAPTION_BULLET_STYLE DEFAULT_CAPTION_BULLET_STYLE = CaptionSetting.CAPTION_BULLET_STYLE.GT;
    public static final List<String> DEFAULT_FONTS = List.of(
            "3d",
            "3d",
            "4max",
            "ansiregular",
            "ansishadow",
            "ansishadow",
            "banner3_d",
            "banner3",
            "bigmoneyne",
            "block",
            "bolger",
            "calvins",
            "colossal",
            "computer",
            "cyberlarge",
            "cyberlarge",
            "doom",
            "elite",
            "elite",
            "epic",
            "fender",
            "georgia11",
            "lean",
            "lineblocks",
            "lineblocks",
            "nancyj",
            "nancyjunderlined",
            "poison",
            "puffy",
            "roman",
            "small",
            "smslant",
            "slant",
            "soft",
            "standard",
            "starwars",
            "stop",
            "univers",
            "usaflag",
            "whimsy");
    public static final List<String> FONTS_WITH_PADDING_CORRECTION = List.of("bigmoneyne", "doom", "lean", "stop");
}
