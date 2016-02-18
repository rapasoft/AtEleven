package no.itera.ateleven

/**
 * Created by Pavol Rajzak, Itera.
 */
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(no.itera.ateleven.Application::class.java, *args)
}
