plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "ru.mirea.kolpakovap.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        // applicationId у library-модуля нет
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":domain"))

}