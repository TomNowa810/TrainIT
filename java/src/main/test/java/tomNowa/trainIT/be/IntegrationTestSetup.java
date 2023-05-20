package tomNowa.trainIT.be;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class IntegrationTestSetup {

    public static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withInitScript("data.sql").withReuse(true);

    @BeforeAll
    static void start() {
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProps(final DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    /*
       xX THERE ARE A PERFORMANT SETUP Xx

                Using the:

                    container.withReusable(true); - Method

                would only start once a Test-Container that took like 15 > sec.
                and all IntegrationTests would access that Container with the same DB
                from other Tests (if the Entities not deleted yet).
                Is the container once started the access process for all Child-IntegrationTests
                is like 2 seconds.

                For that you must create a propertie-File called .testcontainers on your home directory
                like : C:\Users\TomNo\.testcontainers
                and put this prop in it: testcontainers.reuse.enable=true

                You also must delete the @Testcontainers & @Container Annotation because they
                shut the Containers p.d. down.

                You also must define a @BeforeAll - setUp-Method with the methodcall: container.start();

                The big disadvantages are:

                   - The Dockercontainer doesn't stop and still runs after all executed tests
                   - You work with an already used or integrated Dataset

     */

    /*
        xX OLD SCHOOL WAY: Xx

            @Testcontainers
            public abstract class IntegrationTestSetup {

                 @Container
                 public static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:latest")
                         .withInitScript("data.sql").withReuse(true);

                @DynamicPropertySource
                public static void overrideProps(final DynamicPropertyRegistry registry){
                    registry.add("spring.datasource.url", container::getJdbcUrl);
                    registry.add("spring.datasource.username", container::getUsername);
                    registry.add("spring.datasource.password", container::getPassword);
                }
     */
}
