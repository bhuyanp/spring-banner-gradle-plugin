
plugins {
    `java-gradle-plugin`
    signing
    id("com.gradleup.shadow") version "9.0.0-beta13"
    id("com.gradle.plugin-publish").version("1.3.1")
}

repositories {
    mavenCentral()
}

version = property("pluginVersion")!!
group = property("pluginGroup")!!

gradlePlugin {
    website.set("https://github.com/bhuyanp/spring-banner-generator")
    vcsUrl.set("https://github.com/bhuyanp/spring-banner-generator")
    plugins {
        create("springBannerGenerator") {
            id = "io.github.bhuyanp.spring-banner-generator"
            implementationClass = "io.github.bhuyanp.gradle.SpringBannerGeneratorPlugin"
            displayName = "Spring Banner Generator"
            description = "Generates colorful banners for SpringBoot applications."
            tags.set(listOf("spring", "spring-boot", "spring-framework", "java", "banner"))
        }
    }

}

dependencies {
    implementation("com.github.dtmo.jfiglet:jfiglet:1.0.1")
    compileOnly("org.projectlombok:lombok:${property("lombokVersion")}")
    annotationProcessor("org.projectlombok:lombok:${property("lombokVersion")}")
    testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
    testImplementation("org.mockito:mockito-core:${property("mockitoVersion")}")
    testImplementation("org.mockito:mockito-junit-jupiter:${property("mockitoVersion")}")
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
                implementation("org.assertj:assertj-core:${property("assertJVersion")}")
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



publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "spring-banner-generator"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name = "Spring Banner Generator"
                description = "Generates colorful banners for SpringBoot applications."
                url = "https://github.com/bhuyanp/spring-banner-generator"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "bhuyanp"
                        name = "Prasanta K Bhuyan"
                        email = "prasanta.k.bhuyan@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/bhuyanp/spring-banner-generator.git"
                    developerConnection = "scm:git:ssh://github.com/bhuyanp/spring-banner-generator.git"
                    url = "https://github.com/bhuyanp/spring-banner-generator"
                }
            }
        }
    }
}

tasks.shadowJar {
    archiveClassifier = ""
}

signing {
    sign(publishing.publications["mavenJava"])
}


tasks.named<Task>("check") {
    dependsOn(testing.suites.named("functionalTest"))
}
