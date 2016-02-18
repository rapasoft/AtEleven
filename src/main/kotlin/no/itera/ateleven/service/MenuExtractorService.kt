package no.itera.ateleven.service

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@Service
class MenuExtractorService @Autowired constructor(
        val dailyMenuSourcePageRepository: DailyMenuSourcePageRepository,
        val dailyMenuRepository: DailyMenuRepository
) {

    private val log: org.slf4j.Logger = LoggerFactory.getLogger(MenuExtractorService::class.qualifiedName)

    fun extractData() {
        val sourceConfig = dailyMenuSourcePageRepository.findAll()

        sourceConfig.forEach { sc -> dailyMenuRepository.save(extract(sc)) }
    }

    fun extract(dailyMenuSourcePage: DailyMenuSourcePage?): DailyMenu {
        if (dailyMenuSourcePage == null)
            throw IllegalArgumentException("Parameters are $dailyMenuSourcePage")

        val html = Jsoup.connect(dailyMenuSourcePage.url).get()

        val extracted = DailyMenu(
                null,
                dailyMenuSourcePage.restaurantName,
                SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now())),
                html.select(dailyMenuSourcePage.soupsPath ?: "no").map { el -> el.text() },
                html.select(dailyMenuSourcePage.mainDishesPath ?: "no").map { el -> el.text() },
                html.select(dailyMenuSourcePage.otherPath ?: "no").map { el -> el.text() }
        )

        log.info("Extracted $extracted")

        return extracted
    }

}