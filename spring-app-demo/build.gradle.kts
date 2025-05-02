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

    text = "Funky Banner"
    bannerFonts = listOf("ansiregular")

//    text = "Funky Banner"

//    bannerFonts = listOf(
//        "ansiregular",
//        "ansishadow",
//        "banner3_d",
//        "banner4",
//        "bigmoneyne",
//        "bolger"
//    )

//    caption = """
//        Caption text line 1
//        Caption text line 2
//        Caption text line 3
//    """.trimIndent()

//     theme = Theme.LIGHT

//      bannerTheme = ThemeBuilder(BRIGHT_CYAN_TEXT(), BACK_COLOR(45, 45, 45))
//      captionTheme = ThemeBuilder(BRIGHT_CYAN_TEXT(), BACK_COLOR(45, 35, 15),  BOLD())
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
