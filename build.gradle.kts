plugins {
	id("java")
	id("org.jetbrains.intellij") version "1.8.0"
	id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

group = "com.github.bin"
version = "0.3.0"

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://jitpack.io")
}
java {
	sourceCompatibility = JavaVersion.VERSION_11
}
dependencies {
	// implementation(kotlin("stdlib"))
	testImplementation("junit:junit:4.13.2")
}

intellij {
	type.set("IC") // Target IDE Platform
	updateSinceUntilBuild.set(false)
	version.set("2022.2.1")

	plugins.set(listOf("Kotlin"))
}

tasks {
	buildSearchableOptions {
		enabled = false
	}
	patchPluginXml {
		version.set("${project.version}")
		pluginDescription.set(file("description.html").readText())
		changeNotes.set(file("changeNotes.html").readText())
	}
	compileKotlin {
		kotlinOptions.jvmTarget = "11"
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = "11"
		kotlinOptions.freeCompilerArgs += listOf("-Xcontext-receivers")
	}
}
