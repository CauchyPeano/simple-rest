plugins {
    java
    application
}

group = "de.konoplyanko.test"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "de.konoplyanko.test.rest.WebApplication"
}

repositories {
    mavenCentral()
}

dependencies {
    compile("com.sparkjava:spark-core:2.7.2")
    compile("org.slf4j:slf4j-simple:1.7.25")
    compile("com.squareup.okhttp3:okhttp:3.10.0")
    testCompile("junit", "junit", "4.12")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

task<JavaExec>("runProcessingJob") {
    if (project.hasProperty("vehicles")) {
        classpath = java.sourceSets["main"].runtimeClasspath
        description = "Runs processing job, need to pass as argument file name"
        main = "de.konoplyanko.test.processing.ProcessingJob"
        args(listOf(project.property("vehicles")))
    } else {
        println("Please provide parameter `vehicles` like following: -Pvehicles=C:\\dev\\simple-rest\\src\\test\\resources\\vehicles.csv")
    }
}