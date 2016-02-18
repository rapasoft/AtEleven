package no.itera.ateleven.config

/**
 * Created by Pavol Rajzak, Itera.
 */
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Controller
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.bind.annotation.RestController

@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan(basePackages = arrayOf("no.itera.ateleven"), excludeFilters = arrayOf(
        Filter(Controller::class, type = FilterType.ANNOTATION),
        Filter(RestController::class, type = FilterType.ANNOTATION),
        Filter(pattern = arrayOf(".*SwaggerConfig"), type = FilterType.REGEX)))
open class TestApplication

fun main(args: Array<String>) {
    SpringApplication.run(no.itera.ateleven.Application::class.java, *args)
}
