// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false //aplication versi 8.3.0
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false //kotlin fersi 1.9.0

    id("com.google.devtools.ksp") version("1.9.10-1.0.13") apply false
}