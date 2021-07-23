#Person and Vehicle API Project
##Description
This project is an implementation of a simple API with 2 related entities: Person and Vehicle<br>
Project implemented with Kotlin 1.5 (compiled to Java 11), SpringBoot 2.6, Gradle 6.8<br>
DB used is H2 in-memory. H2 console is available at `http://<server:port>/h2-console`<br>
API authentication is done using api key provided in `X-API-KEY` header (configured via `application.properties`)<br>
API documentation is provided using Swagger2 (ui access via `http://<server:port>/swagger-ui.html`)<br>
Default port is set to 8081<br>

##Building and running the application
###Prerequisites:
Following tools are required to build and run the project:
- Gradle 6.8 (or higher)
- Java JDK 11

Run following command to build the project:<br>
`gradle build`

Run following command to run tests:<br>
`gradle test --tests '*'`

Run following command to compile jar file:<br>
`gradle bootJar`

Run following command to run the application:<br>
`java -jar ./build/lib/regoApi-0.0.1-SNAPSHOT.jar`
##Potential improvements:
Following amendments could be done to further improve quality and performance of the project:
- Add Dockerfile to containerise application for CI/CD and/or local development
- Add checkstyle validation
- Use lighter `@WebMvcTest` tests to test controllers separately from heavier e2e tests (currently implemented with `@SpringBootTest`)
- Better tests segregation to avoid potential side effects
- Stronger authentication + authorisation mechanism. Current implementation only uses simplified authentication with api key 
- Separate dev, test and production profiles by adding corresponding `application-<profile>.properties`. Ideally, inject secrets at deploy or run time
- Configure `SSL` (`HTTPS` instead of `HTTP`)
- Add logging, preferably `Slf4j`
- Optimise tests to get rid of repeating code
