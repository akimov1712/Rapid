plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    id("androidx.room")
    id("kotlin-parcelize")
}


android {
    namespace = "ru.topbun.rapid"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.topbun.rapid"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("com.google.maps.android:maps-compose:6.4.1")
    implementation ("com.google.maps.android:maps-compose-utils:6.4.1")
    implementation ("com.google.maps.android:maps-compose-widgets:6.4.1")

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.coil.compose)

    // JsonSerialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore
    implementation (libs.androidx.datastore.datastore.preferences2)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Voyager
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.koin)
    implementation(libs.voyager.tab.navigator)

    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}