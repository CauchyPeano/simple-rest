plugins {
    java
}

group = "de.konoplyanko.test"
version = "1.0-SNAPSHOT"

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