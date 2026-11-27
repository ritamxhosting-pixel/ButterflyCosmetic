ButterflyCosmetic 1.1.0 - source project
Target: Paper 26.2 build 135, Java 25

IMPORTANT
This is a source project, not a prebuilt plugin JAR. The previous 1.0.0 JAR was compiled against homemade API stubs and failed on your server. Do not use that old JAR.

BUILD
1. Install a Java 25 JDK and Gradle 9.1 or newer on a computer with internet access.
2. Open a terminal in this project folder.
3. Run: gradle build
4. The plugin JAR will be at: build/libs/ButterflyCosmetic-1.1.0.jar

INSTALL
1. Stop the Paper server.
2. Remove the old ButterflyCosmetic-1.0.0.jar from plugins.
3. Upload the newly built ButterflyCosmetic-1.1.0.jar to plugins.
4. Start the server and check the console for: ButterflyCosmetic 1.1.0 enabled.
5. In game use /butterfly to toggle the cosmetic effect.

This effect is particle-based decoration, not a 3D butterfly model or knife/inspect animation. Nearby players should see the particles. The source uses the Paper API dependency declared in build.gradle; it still needs to be built against that dependency before installation.

OPTIONAL: BUILD WITH GITHUB ACTIONS
The project includes .github/workflows/build.yml. Push the project contents to a GitHub repository, open Actions, select "Build ButterflyCosmetic", and click "Run workflow". When it finishes, download the ButterflyCosmetic-1.1.0 artifact; it contains the JAR.
