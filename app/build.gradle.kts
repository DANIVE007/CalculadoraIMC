plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.calculadoraimc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.calculadoraimc"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
  //  implementation(libs.androidx.material3)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.compose.ui:ui-text")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
// Para ViewModel en Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

// Para LiveData (opcional si decides usar LiveData en el futuro)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    // Agrega esta línea para manejar los tipos de teclado
    implementation("androidx.compose.ui:ui-text:1.5.1")
// Dependencia para Material tradicional
    implementation("androidx.compose.material:material:1.5.0")

    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.androidx.material3.android)
    // Agrega las dependencias para Jetpack Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("androidx.navigation:navigation-compose:2.5.3") // Dependencia para navegación

    implementation("androidx.compose.material3:material3:1.0.0") // Asegúrate de que esto esté presente


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}