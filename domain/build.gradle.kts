plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    //implementation(project(":data"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


}