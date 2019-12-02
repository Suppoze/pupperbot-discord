buildscript {
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }
}

plugins {
    kotlin("jvm") version "1.3.60"
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }

    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "application")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.5")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.11"
    }
}
