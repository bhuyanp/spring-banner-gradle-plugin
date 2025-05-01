
plugins {
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
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

gradlePlugin {
    // Define the plugin
    val springBanner by plugins.creating {
        id = "io.pbhuyan.gradle.spring-banner-generator"
        implementationClass = "io.pbhuyan.gradle.spring.SpringBannerGeneratorPlugin"
    }
}



gradlePlugin.testSourceSets.add(sourceSets["functionalTest"])

tasks.named<Task>("check") {
    // Include functionalTest as part of the check lifecycle
    dependsOn(testing.suites.named("functionalTest"))
}
