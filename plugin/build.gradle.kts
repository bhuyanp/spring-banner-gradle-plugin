
plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1"
}

repositories {
    mavenCentral()
}

version = "1.0"
group = "io.github.bhuyanp"


gradlePlugin {
    website.set("https://github.com/bhuyanp/GradlePlugins")
    vcsUrl.set("https://github.com/bhuyanp/GradlePlugins")
    plugins {
        create("springBannerGenerator") {
            id = "io.github.bhuyanp.spring-banner-generator"
            implementationClass = "io.github.bhuyanp.gradle.SpringBannerGeneratorPlugin"
            displayName = "Spring Banner Generator"
            description = "Generates colorful banners for SpringBoot applications."
            tags.set(listOf("spring", "spring-boot", "spring-framework", "java", "gradle", "plugin", "banner"))
        }
    }
}


dependencies {
    implementation("com.diogonunes:JColor:5.5.1")
    implementation("com.github.dtmo.jfiglet:jfiglet:1.0.1")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.12.1")
        }

        // Create a new test suite
        val functionalTest by registering(JvmTestSuite::class) {
            dependencies {
                // functionalTest test suite depends on the production code in tests
                implementation(project())
                implementation("org.assertj:assertj-core:3.26.0")
            }

            targets {
                all {
                    // This test suite should run after the built-in test suite has run its tests
                    testTask.configure { shouldRunAfter(test) } 
                }
            }
        }
    }
}


gradlePlugin.testSourceSets.add(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    // Include functionalTest as part of the check lifecycle
    dependsOn(testing.suites.named("functionalTest"))
}
