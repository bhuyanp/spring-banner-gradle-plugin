import io.github.bhuyanp.gradle.theme.ThemePreset

plugins {
    java
    id("org.springframework.boot").version("3.0.0")
    id("io.spring.dependency-management").version("1.1.7")
    id("io.github.bhuyanp.spring-banner-generator")
}

group = "io.pbhuyan.gradle"
version = "1.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
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
