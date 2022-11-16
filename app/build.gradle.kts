plugins{
	java
	application
}

repositories {
	mavenCentral()
	//maven("https://jitpack.io")
}

dependencies{
    implementation("com.discord4j:discord4j-core:3.2.3")
}


tasks.jar {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	
	manifest {
		attributes["Main-Class"] = "smol.SmolBot"
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
	mainClass.set("smol.SmolBot")
}

//why
tasks.withType(JavaExec::class.java) {
	standardInput = System.`in`
}
