plugins {
	id("java")
	id("org.jetbrains.kotlin.jvm") version "1.6.20"
	id("org.jetbrains.intellij") version "1.8.0"
}

group = "com.github.bin"
version = "0.3.0"

repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.20")
	testImplementation("junit:junit:4.13.2")
}

intellij {
	type.set("IC") // Target IDE Platform
	updateSinceUntilBuild.set(false)
	version.set("2022.2")

	plugins.set(listOf("Kotlin"))
}

tasks {
	patchPluginXml {
		pluginDescription.set(file("description.html").readText())
		changeNotes.set(file("changeNotes.html").readText())
	}

	// Set the JVM compatibility versions
	withType<JavaCompile> {
		sourceCompatibility = "11"
		targetCompatibility = "11"
	}
	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions.jvmTarget = "11"
	}

}
