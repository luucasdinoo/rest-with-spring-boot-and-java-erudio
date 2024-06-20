package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static final MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:8.0");

        private static void startContainers() {
            Startables.deepStart(Stream.of(mySQL)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mySQL.getJdbcUrl(),
                    "spring.datasource.username", mySQL.getUsername(),
                    "spring.datasource.password", mySQL.getPassword()
            );
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();;
            MapPropertySource testcontainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());

            environment.getPropertySources().addFirst(testcontainers);
        }
    }
}
