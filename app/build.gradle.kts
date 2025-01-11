plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.toeicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.toeicapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.0")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")


    implementation ("androidx.core:core-ktx:latest_version")
    //Gson
    implementation("com.google.code.gson:gson:2.10.1")
    //glider
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //picasso
    implementation("com.squareup.picasso:picasso:2.8")
    //lottie
    implementation("com.airbnb.android:lottie:3.4.0")
    //spech to text
//    implementation("com.google.android.gms:play-services-speech:21.0.0")
}