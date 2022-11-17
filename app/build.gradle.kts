plugins{
	kotlin("jvm") version "1.7.21"
    //kotlin("plugin.serialization") version "1.7.21"
	application
}

repositories {
	mavenCentral()
	maven("https://jitpack.io")
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies{
    implementation("dev.kord:kord-core:0.8.0-M10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    
    //i ponder for a moment
    implementation("com.github.Anuken.Arc:arc-core:v140.3")
}


tasks.jar {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	
	manifest {
		attributes["Main-Class"] = "smol.SmolBotKt"
	}
	
	from(*configurations.runtimeClasspath.files.map { if (it.isDirectory()) it else zipTree(it) }.toTypedArray())
}

tasks.register<Copy>("deploy") {
	dependsOn("jar")
	
	from("build/libs/app.jar")
	into("../build/")
	
	doLast {
		delete("build/libs/app.jar")
	}
}

application.apply {
	mainClass.set("smol.SmolBotKt")
}

//why
tasks.withType(JavaExec::class.java) {
	standardInput = System.`in`
}
