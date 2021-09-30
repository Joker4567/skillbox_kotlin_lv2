buildscript {
    val kotlin_version by extra("1.5.21")
    val dagger_version by extra("2.38.1")
    val dagger_viewmodel_version by extra("1.0.0-alpha03")
    val moshi_version by extra("1.12.0")
    val kotlin_coroutines by extra("1.5.0")
    val room_version by extra("2.3.0")
    val retrofit_version by extra("2.9.0")
    val okHttp_version by extra("4.9.1")
    val lifecycle_version by extra("2.3.1")
    val navigation_version by extra("1.0.0-rc02")
    val navigation_viewmodel_version by extra("2.3.1")
    val ktx_version by extra("1.3.5")
    val hilt_work_version by extra("1.0.0")
    val multidex_version by extra( "2.0.1")
    val corountines_version by extra("1.5.0")
    val coreCtx_version by extra("1.6.0-rc01")

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
            content {
                includeModule("com.google.dagger", "hilt-android-gradle-plugin")
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.0-alpha11")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    val minSdkVersion by extra(23)
    val compileSdkVersion by extra(30)
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
