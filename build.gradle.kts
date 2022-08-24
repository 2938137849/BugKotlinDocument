import org.gradle.api.JavaVersion.VERSION_17

fun properties(key: String) = project.findProperty(key)?.toString() ?: ""
fun env(key: String) = System.getenv(key)

plugins {
	id("java")
	id("org.jetbrains.intellij") version "1.8.0"
	kotlin("jvm") version "1.7.10"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://jitpack.io")
}

java {
	sourceCompatibility = VERSION_17
	targetCompatibility = VERSION_17
}

dependencies {
	// implementation(kotlin("stdlib"))
	testImplementation("junit:junit:4.13.2")
}

intellij {
	pluginName.set(properties("pluginName"))
	type.set(properties("platformType")) // Target IDE Platform
	downloadSources.set(true)
	updateSinceUntilBuild.set(false)
	version.set(properties("platformVersion"))

	plugins.set(listOf("Kotlin"))
}

tasks {
	buildSearchableOptions {
		enabled = false
	}
	patchPluginXml {
		version.set(properties("pluginVersion"))
		sinceBuild.set(properties("pluginSinceBuild"))
		untilBuild.set(properties("pluginUntilBuild"))
		pluginDescription.set(file("description.html").readText())
		changeNotes.set(file("changeNotes.html").readText())
	}
	wrapper {
		gradleVersion = properties("gradleVersion")
		distributionType = Wrapper.DistributionType.ALL
	}
	withType<JavaCompile> {
		sourceCompatibility = VERSION_17.toString()
		targetCompatibility = VERSION_17.toString()
		options.encoding = "UTF-8"
	}
	compileKotlin {
		kotlinOptions.jvmTarget = VERSION_17.toString()
	}
	compileTestKotlin {
		kotlinOptions.jvmTarget = VERSION_17.toString()
		kotlinOptions.freeCompilerArgs += listOf("-Xcontext-receivers")
	}
	signPlugin {
		certificateChain.set(env("CERTIFICATE_CHAIN"))
		privateKey.set(env("PRIVATE_KEY"))
		password.set(env("PRIVATE_KEY_PASSWORD"))
	}
	publishPlugin {
		token.set(env("PUBLISH_TOKEN"))
	}
}
