import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ComposeGraalTest"
            packageVersion = "1.0.0"
        }
    }
}


tasks.shadowJar {
    manifest {
        attributes(mapOf("Main-Class" to "MainKt"))
    }
}

//tasks.register("nativeDist") {
//    dependsOn("nativeCompile")
//
//    doLast {
//        // 실행 파일과 DLL 복사
//        copy {
//            mkdir("build/dist")
//            from("build/native/nativeCompile")
//            into("build/dist")
//            include("*.exe")
//            include("*.dll")
//        }
//
//        // 폰트 설정 파일 복사
//        val javaHome = System.getProperty("java.home")
//        println(javaHome)
//
//        copy {
//            mkdir("build/dist/lib")
//            from("$javaHome/lib")
//            into("build/dist/lib")
//            include("fontconfig.bfc")
//            include("fontconfig.properties.src")
//            include("psfont.properties.ja")
//            include("psfontj2d.properties")
//        }
//    }
//}


//tasks {
//    register<Jar>("uberJar") {
//        archiveClassifier.set("uber")
//
//        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//
//        manifest {
//            attributes["Main-Class"] = "MainKt"
//        }
//
//        from(sourceSets.main.get().output)
//
//        dependsOn(configurations.runtimeClasspath)
//        from({
//            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
//        })
//    }
//}
