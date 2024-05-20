plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"

    // A properties file containing default secret values. This file can be
    // checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"

    // Configure which keys should be ignored by the plugin by providing regular expressions.
    // "sdk.dir" is ignored by default.
    ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}


android {
    namespace = "it.unimib.greenway"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.unimib.greenway"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    // Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation(libs.play.services.location)

    implementation ("androidx.core:core:1.9.0")
    implementation ("androidx.tracing:tracing:1.1.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha04")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.6.0-alpha01")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.4.0")


    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation(libs.firebase.auth)
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    //mail checker
    implementation("commons-validator:commons-validator:1.8.0")

    implementation("com.google.firebase:firebase-database")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //places
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.0"))
    implementation("com.google.android.libraries.places:places:3.3.0")
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.contrib)

    //room
    val room_version = "2.6.0"
    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    implementation ("com.google.maps.android:android-maps-utils:2.2.0")

    // Dipendenza di Robolectric
    testImplementation ("org.robolectric:robolectric:4.10.3")

    // Dipendenza per le API Android per i test
    testImplementation ("androidx.test:core:1.5.0")

    // Dipendenze per Mockito se lo usi
    testImplementation ("org.mockito:mockito-core:3.12.4")
    testImplementation ("org.mockito:mockito-inline:3.12.4")




}


