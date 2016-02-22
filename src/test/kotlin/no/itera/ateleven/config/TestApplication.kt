package no.itera.ateleven.config

/**
 * Created by Pavol Rajzak, Itera.
 */
import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.service.MenuExtractorServiceImpl
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScan.Filter
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.bind.annotation.RestController
import java.util.*

@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan(basePackages = arrayOf("no.itera.ateleven"), excludeFilters = arrayOf(
        Filter(Controller::class, type = FilterType.ANNOTATION),
        Filter(RestController::class, type = FilterType.ANNOTATION),
        Filter(pattern = arrayOf(".*SwaggerConfig"), type = FilterType.REGEX)))
open class TestApplication {
    companion object {
        fun dailyMenuMock() = DailyMenu(
                null,
                "Test" + (Math.random() * 100).toInt(),
                MenuExtractorServiceImpl.currentDate(),
                Arrays.asList("Mrkvova", "Cicerova"),
                Arrays.asList("Hovadzi gulas", "Slovenske rizoto"),
                emptyList<String>())
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(no.itera.ateleven.Application::class.java, *args)
}
