import io.github.bhuyanp.gradle.ansi.Attribute.BACK_COLOR
import io.github.bhuyanp.gradle.ansi.Attribute.BLACK_BACK
import io.github.bhuyanp.gradle.ansi.Attribute.BOLD
import io.github.bhuyanp.gradle.ansi.Attribute.BRIGHT_BLACK_BACK
import io.github.bhuyanp.gradle.ansi.Attribute.BRIGHT_BLUE_TEXT
import io.github.bhuyanp.gradle.ansi.Attribute.BRIGHT_CYAN_TEXT
import io.github.bhuyanp.gradle.ansi.Attribute.BRIGHT_WHITE_TEXT
import io.github.bhuyanp.gradle.ansi.Attribute.BRIGHT_YELLOW_TEXT
import io.github.bhuyanp.gradle.ansi.Attribute.CYAN_TEXT
import io.github.bhuyanp.gradle.ansi.Attribute.DESATURATED
import io.github.bhuyanp.gradle.ansi.Attribute.DIM
import io.github.bhuyanp.gradle.ansi.Attribute.GREEN_BACK
import io.github.bhuyanp.gradle.ansi.Attribute.ITALIC
import io.github.bhuyanp.gradle.ansi.Attribute.MAGENTA_BACK
import io.github.bhuyanp.gradle.ansi.Attribute.NONE
import io.github.bhuyanp.gradle.ansi.Attribute.RED_BACK
import io.github.bhuyanp.gradle.ansi.Attribute.TEXT_COLOR
import io.github.bhuyanp.gradle.theme.ThemeConfig
import io.github.bhuyanp.gradle.theme.ThemePreset

plugins {
    java
    id("org.springframework.boot").version("3.4.5")
    id("io.spring.dependency-management").version("1.1.7")
    id("io.github.bhuyanp.spring-banner-generator")
}

group = "io.pbhuyan.gradle"
version = "1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

springBanner {
//    text = "Funky Banner"
//    bannerFonts = listOf("dotmatrix");
//    bannerFonts = listOf(
//        "ansiregular",
//        "ansishadow",
//        "banner3_d",
//        "banner4",
//        "bigmoneyne",
//        "bolger"
//    )
//    caption = """
//    """.trimIndent()

//    themePreset = ThemePreset.SURPRISE_ME

//    bannerTheme = ThemeConfig(BRIGHT_CYAN_TEXT(), NONE(), DESATURATED())
//    captionTheme = ThemeConfig(TEXT_COLOR(231, 247, 225), NONE(), NONE())
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
