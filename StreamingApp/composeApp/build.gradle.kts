import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.kotlinSerialization)

    //alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    /*
    js {
        browser()
        binaries.executable()
    }*/

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
            implementation("androidx.navigation:navigation-compose:2.5.3")
            implementation("io.insert-koin:koin-compose:4.0.0")

  //          implementation("io.ktor:ktor-client-okhttp:3.0.0")


            implementation(libs.ktor.client.okhttp)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            //ksafe
            //implementation(libs.ksafe)
            //implementation(libs.ksafe.compose)

        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)

            // koin
            implementation("io.insert-koin:koin-compose:4.0.0")

            // serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")



            implementation(libs.jetbrains.navigation3.ui)

            // opcionalno
            implementation(libs.jetbrains.lifecycle.viewmodelNavigation3)
            implementation(libs.jetbrains.material3.adaptiveNavigation3)

            implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")

            implementation("io.coil-kt.coil3:coil-compose:3.0.0-rc01")
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.0-rc01")
//            implementation("io.ktor:ktor-client-core:3.0.0")


            //ktor
            implementation(libs.bundles.ktor)

            //koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)

            //ksafe
            implementation(libs.ksafe)
            implementation(libs.ksafe.compose)

            //material3 windows size class
            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.5.0")

            implementation(compose.foundation)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        wasmJsMain.dependencies {
            implementation(libs.navigation3.browser)

            implementation(libs.ktor.client.js)

        }
    }
}

android {
    namespace = "org.streaming.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.streaming.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

