import java.util.*
import org.gradle.api.tasks.bundling.AbstractArchiveTask

plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow)
}

version = libs.versions.lwc.get()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get().toInt()))
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.glaremasters.me/repository/towny/")
    maven("https://ci.ender.zone/plugin/repository/everything/")
    maven("https://repo.glaremasters.me/repository/public/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.worldedit.core) {
        isTransitive = false
    }
    compileOnly(libs.worldedit.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.worldguard.core) {
        isTransitive = false
    }
    compileOnly(libs.worldguard.bukkit) {
        isTransitive = false
    }
    compileOnly(libs.towny)
    compileOnly(libs.factions)
    compileOnly(libs.vault.api)
    compileOnly(libs.commons.lang3)
    implementation(libs.gson)
    compileOnly(libs.guava)
    implementation(libs.bstats.bukkit)
}

tasks {
    withType<AbstractArchiveTask> {
        destinationDirectory.set(layout.projectDirectory.dir("target"))
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    jar {
        archiveClassifier.set("noshade")
        from("LICENSE") {
            into("/")
        }
    }
    processResources {
        filesMatching("plugin.yml") {
            expand(
                "version" to project.version,
                "apiVersion" to libs.versions.paper.plugin.api.get(),
            )
        }
    }
    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("${rootProject.name.uppercase(Locale.getDefault())}-${project.version}.jar")
        relocate("org.bstats", "${project.group}.${rootProject.name}.lib.bstats")
        relocate("com.google.gson", "${project.group}.${rootProject.name}.lib.gson")
        from("LICENSE") {
            into("/")
        }
        manifest {
            attributes("paperweight-mappings-namespace" to "mojang")
        }
    }
    build {
        dependsOn(shadowJar)
    }
}

publishing {
    repositories {
        if (project.hasProperty("mavenUsername") && project.hasProperty("mavenPassword")) {
            maven {
                credentials {
                    username = "${project.property("mavenUsername")}"
                    password = "${project.property("mavenPassword")}"
                }
                url = uri("https://repo.codemc.io/repository/maven-releases/")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = project.name
            version = "${project.version}"
            from(components["java"])
        }
    }
}
