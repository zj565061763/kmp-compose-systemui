import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.androidLibrary)
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.mavenPublish)
}

kotlin {
  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  androidTarget {
    publishLibraryVariants("release")
    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_1_8)
    }
  }

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "com_sd_lib_kmp_compose_systemui"
      isStatic = true
    }
  }

  sourceSets {
    androidMain.dependencies {
      implementation(libs.androidx.activity.compose)
    }
    commonMain.dependencies {
      implementation(compose.foundation)
    }
  }
}

android {
  namespace = "com.sd.lib.kmp.compose_systemui"
  compileSdk = 34
  defaultConfig {
    minSdk = 21
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
