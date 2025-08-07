plugins {
	java
	war
  checkstyle
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
  id("com.github.spotbugs") version "6.2.3"
}

group = "br.dev.mestretramador"
version = "0.0.1"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

checkstyle {
  toolVersion = "10.26.1"
}

spotbugs {
  toolVersion = "4.9.3"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Ref: https://medium.com/@yiotiskl/boost-your-productivity-enabling-hot-reload-for-your-dockerized-spring-boot-kotlin-project-9aea60a53db1
tasks.register<Copy>("getDependencies") {
  from(sourceSets.main.get().runtimeClasspath)
  into("runtime/")

  doFirst {
    val runtimeDir = File("runtime")

    runtimeDir.deleteRecursively()
    runtimeDir.mkdir()
  }

  doLast {
    File("runtime").deleteRecursively()
  }
}
