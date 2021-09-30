plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {

    buildToolsVersion = "30.0.3"
    compileSdk = 30

    defaultConfig {
        applicationId = "ru.skillbox.presentation_patterns"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    val kotlinCoreCtxVersion = rootProject.extra["coreCtx_version"]
    implementation("androidx.core:core-ktx:$kotlinCoreCtxVersion")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    //Coroutines
    val coroutines = rootProject.extra["corountines_version"]
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
    //Activity result API
    val ktx = rootProject.extra["ktx_version"]
    implementation("androidx.fragment:fragment:$ktx")
    implementation("androidx.fragment:fragment-ktx:$ktx")
    //Lifecycle
    val lifecycle = rootProject.extra["lifecycle_version"]
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle")
    //Dagger-Hilt
    val dagger = rootProject.extra["dagger_version"]
    val daggerViewModel = rootProject.extra["dagger_viewmodel_version"]
    val daggerWork = rootProject.extra["hilt_work_version"]
    implementation("com.google.dagger:hilt-android:$dagger")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:$daggerViewModel")
    kapt("com.google.dagger:hilt-android-compiler:$dagger")
    kapt("androidx.hilt:hilt-compiler:$daggerWork")
    //Room
    val roomVersion = rootProject.extra["room_version"]
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    //Retrofit
    val retrofit = rootProject.extra["retrofit_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    //Stetho
    implementation("com.facebook.stetho:stetho-okhttp3:1.6.0")
    //Okhttp
    val okhttp = rootProject.extra["okHttp_version"]
    implementation("com.squareup.okhttp3:okhttp:$okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp")
    //SharedPreferences
    implementation("androidx.preference:preference-ktx:1.1.1")
    //Navigation
    implementation("com.ncapdevi:frag-nav:3.3.0")
    //RecyclerViewDelegates
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl:4.3.0")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:4.3.0")
    //Kotlin coroutines
    var kotlinVersion = rootProject.extra["kotlin_version"]
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha02")
}