package uk.gov.dhsc.htbhf.eligibility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.emptyList;

/**
 * Definitions of SpringFox beans to generate Swagger documentation.
 * Also logs application info at startup.
 */
@Configuration
@Import(BeanValidatorPluginsConfiguration.class) // enable documentation of JSR-305 constraints
@Slf4j
public class ApiDocumentation {

    @Value("${app.version:}") // use APP_VERSION env variable if available, otherwise give no version info
    private String appVersion;

    @Value("${instance.index:}") // use INSTANCE_INDEX env variable if available, otherwise give no index info
    private String instanceIndex;

    @Value("${vcap.application.application_id:}") // the id of the application as provided by cf
    private String applicationId;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("N/A")
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.getClass().getPackageName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Eligibility Service",
                "Responsible for deciding whether an applicant is eligible for apply-for-healthy-start",
                appVersion,
                "",
                new Contact("Department Of Health and Social Care", "https://github.com/DepartmentOfHealth-htbhf", "dh-htbhf-team@equalexperts.com"),
                "MIT",
                "https://opensource.org/licenses/MIT",
                emptyList()
        );

    }

    @EventListener(ApplicationReadyEvent.class)
    public void logAfterStartup() {
        log.info("Application started. App version={}, app id={}, instance index={}", appVersion, applicationId, instanceIndex);
    }
}
