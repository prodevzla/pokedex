plugins {
    id("java-library")

    kotlin("jvm")
//    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.apollo)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    implementation(project(":domain"))

    implementation(libs.apollo.runtime)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)

    implementation("javax.inject:javax.inject:1")

}

apollo {
    service("service") {
        packageName.set("com.prodevzla.pokedex.data")
        introspection {
            endpointUrl.set("https://beta.pokeapi.co/graphql/v1beta")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}
