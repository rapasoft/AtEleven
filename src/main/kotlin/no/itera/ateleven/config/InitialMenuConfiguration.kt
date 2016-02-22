package no.itera.ateleven.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.InputStream
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@Configuration
open class InitialMenuConfiguration {

    @Autowired lateinit var dailyMenuSourcePageRepository: DailyMenuSourcePageRepository

    @Bean
    open fun initDefaultMenuConfiguration(): Boolean {
        val objectMapper: ObjectMapper = ObjectMapper()
        val typeFactory = TypeFactory.defaultInstance()

        val menuString = readFromStream(InitialMenuConfiguration::class.java.getResourceAsStream("/restaurant-paths.json"))

        val menuConfig: List<DailyMenuSourcePage> =
                objectMapper.readValue(menuString, typeFactory.constructCollectionType(List::class.java, DailyMenuSourcePage::class.java))

        dailyMenuSourcePageRepository.save(menuConfig)

        return true
    }

    private fun readFromStream(stream: InputStream?): String {
        val scanner = Scanner(stream)
        val stringBuilder = StringBuilder()

        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine())

        return stringBuilder.toString()
    }

}