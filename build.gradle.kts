import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion: String by project
	val korgeEcsVersion: String by project

	repositories {
		mavenLocal()
		maven { url = uri("https://dl.bintray.com/nanodeath/korge") }
		maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
		maven { url = uri("https://jitpack.io") }
		mavenCentral()
		google()
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
	}
}

plugins {
	kotlin("multiplatform") version "1.3.72"
}

repositories {
	mavenLocal()
}

apply<KorgeGradlePlugin>()

korge {
	id = "com.sample.demo"
}

val korgeEcsVersion = "0.1.0"
//val korgeEcsVersion = "1.0-SNAPSHOT" // for local development
kotlin {
	dependencies {
		add("commonMainApi", "org.korge:korge-ecs:$korgeEcsVersion")
	}
}