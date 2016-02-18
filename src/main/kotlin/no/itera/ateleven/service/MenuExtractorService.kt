package no.itera.ateleven.service

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
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
    companion object {
        val LOG: org.slf4j.Logger = LoggerFactory.getLogger(MenuExtractorService::class.java.name)
        val ID_IS_GENERATED = null
    }

    fun extractData() {
        val sourceConfig = dailyMenuSourcePageRepository.findAll()

        sourceConfig.forEach { sc -> dailyMenuRepository.save(extract(sc)) }
    }

    fun extract(dailyMenuSourcePage: DailyMenuSourcePage?): DailyMenu {
        if (dailyMenuSourcePage == null)
            throw IllegalArgumentException("Parameters are $dailyMenuSourcePage")

        val html = Jsoup.connect(dailyMenuSourcePage.url).get()

        val extracted = DailyMenu(
                ID_IS_GENERATED,
                dailyMenuSourcePage.restaurantName,
                SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now())),
                retrieveList(dailyMenuSourcePage.soupsPath, html),
                retrieveList(dailyMenuSourcePage.mainDishesPath, html),
                retrieveList(dailyMenuSourcePage.otherPath, html)
        )

        LOG.info("Extracted $extracted")

        return extracted
    }

    private fun retrieveList(path: String?, html: Document): List<String> {
        if (path != null && !path.equals("")) {
            return html.select(path).map { el -> el.text() }
        }
        return emptyList()
    }

}