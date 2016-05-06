# @11 / AtEleven
AtEleven is a simple web application that gathers daily menus from a restaurants in a near area. The idea came from: https://github.com/ERNICommunity/at11

## Technologies
Backend is written in [Kotlin](http://try.kotlinlang.org) powered by [Spring boot](http://projects.spring.io/spring-boot/) and frontend is written mainly in [React.js](https://facebook.github.io/react/)

## How to build
* Download and install [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Download and install [Node.js](https://nodejs.org/en/download/) version 4.3.x (This one is optional but highly recommended)
* Download and store somewhere [Maven version 3.3.x](https://maven.apache.org/download.cgi), make sure you have `mvn` command available from command line, e.g. on `PATH`
* You can use your favorite IDE for Java/Kotlin (I've tested in on IntelliJ IDEA Ultimate, but Eclipse with Kotlin plugin can be used as well)
* Clone the repository (with Git, of course) and execute `mvn clean package` in a root directory (with pom.xml)
* You can run the application with `mvn spring-boot:run`
* The application is then available on `http://localhost:8080/at11`

