import com.soywiz.korge.gradle.*

val korgeEcsVersion: String by project

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
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
	maven { url = uri("https://dl.bintray.com/nanodeath/korge") }
}

apply<KorgeGradlePlugin>()

korge {
	id = "com.sample.demo"
}

kotlin {
	dependencies {
		add("commonMainApi", "org.korge:korge-ecs:$korgeEcsVersion")
	}
}