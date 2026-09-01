# OpenCASA
An open-source tool for sperm quality analysis. See this article for more details: https://journals.plos.org/ploscompbiol/article?id=10.1371/journal.pcbi.1006691

## Version 2.0
Go to https://github.com/calquezar/OpenCASA/tree/51d10244d0cbab2bfba61628f7e43eb2e060057e in order to download the source code of OpenCASA v2.0.

## Version 1.0
Go to https://github.com/calquezar/OpenCASA/tree/b71936668093a7d9052466b4f97e5537a8ef49ed in order to download the source code of OpenCASA v1.0.
 
## Specifications

OpenCASA is built against ImageJ 1.54p and is intended for current Fiji distributions. The plugin is compiled for Java 8, so it runs with both Fiji's Java 8 bundles and newer Fiji installations that include Java 21. There are no special operating-system requirements, but video analysis requires substantial memory. Allocate at least 5 GB of heap memory, or approximately 2.5 times the size of the largest file to be analyzed. For testing, AVI videos and JPEG or PNG images were used.

### ImageJ compatibility

The standard build targets ImageJ 1.54p. The same source also builds against ImageJ 1.49q using the `legacy-imagej` Maven profile, covering ImageJ 1.x and Fiji installations that run Java 8 or newer. Build a legacy-compatible plugin with `mvn -Plegacy-imagej package`.

ImageJ or Fiji distributions bundled with Java 6 or Java 7 cannot load this plugin because it is compiled for Java 8. Supporting those end-of-life runtimes requires a separate Java 6-compatible build and is not recommended for current installations.

## Installation

### For users

1. Download the current Fiji build for your platform from https://imagej.net/software/fiji/downloads. Fiji is portable: extract the archive to a writable directory in your user profile. On Windows, do not install it beneath `C:\Program Files`, because Fiji must be able to update its own files.
2. Start Fiji once, then run `Help > Update...` to install the latest Fiji components. Restart Fiji when prompted.
3. Build this project with `mvn package`. Copy `OpenCASA_/target/OpenCASA_GitHub-0.1.0-SNAPSHOT.jar` into Fiji's `plugins` directory, then restart Fiji. The generated plugin JAR includes its Java-ML dependency. Alternatively, use `Plugins > Install...` and choose the generated JAR.
4. Open `Plugins > OpenCASA` to launch the plugin.

### For developers

Use a Java 8 or newer JDK and Maven 3.9 or newer. Import `OpenCASA_/pom.xml` as an existing Maven project in your IDE, then build with `mvn package`. The project declares its ImageJ dependency in Maven and includes the required Java-ML JAR under `OpenCASA_/lib`; no Fiji JARs need to be added manually to the IDE classpath.

For development launches, use Fiji's bundled Java runtime or a Java 8+ JDK. To provide 5 GB of heap memory, configure the Java VM arguments as `-Xms5120M -Xmx5120M`. Fiji memory can also be adjusted from `Edit > Options > Memory & Threads...`.


## Icons Credits

* concentration icon made by xnimrodx from www.flaticon.com
* motility icon made by Freepik from www.flaticon.com
* viability icon made by Freepik from www.flaticon.com
* Functionality icon made by Prosymbols from www.flaticon.com
* morphometry icon made by Cursor Creative from www.flaticon.com
* accumulation icon made by Freepik from www.flaticon.com
* chemotaxis icon made by Those Icons from www.flaticon.com 
* scatter icon made by Flat Icons from www.flaticon.com
* simulation icon made by Freepik from www.flaticon.com
* settings icon made by Freepik from www.flaticon.com
