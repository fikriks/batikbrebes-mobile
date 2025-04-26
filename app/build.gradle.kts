plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 29
        targetSdk = 33
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Android UI dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Barcode scanner library
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.android.volley:volley:1.2.1")


    // Unit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Untuk operasi kriptografi DES
    implementation ("commons-codec:commons-codec:1.15")

    // Untuk parsing JSON
    implementation ("org.json:json:20231013")

    // Untuk keamanan dan penyimpanan kunci di Android KeyStore
    implementation ("androidx.security:security-crypto:1.1.0-alpha06")
    implementation ("com.google.code.gson:gson:2.10.1")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
// Converter JSON untuk Retrofit
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
// Gson (jika belum ada)
    implementation ("com.google.code.gson:gson:2.10.1")

}

