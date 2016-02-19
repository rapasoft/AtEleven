package no.itera.ateleven.service

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.transaction.Transactional

/**
 * Created by Pavol Rajzak, Itera.
 */
@Service
open class MenuExtractorServiceImpl @Autowired constructor(
        val dailyMenuSourcePageRepository: DailyMenuSourcePageRepository,
        val dailyMenuRepository: DailyMenuRepository
) : MenuExtractorService {
    companion object {
        val LOG: org.slf4j.Logger = LoggerFactory.getLogger(MenuExtractorServiceImpl::class.java.name)
        val ID_IS_GENERATED = null
        const val HOUR_IN_MS: Long = 1000 * 60 * 60

        fun currentDate() = SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()))
    }

    @Scheduled(fixedDelay = HOUR_IN_MS)
    override fun extractData() {
        val sourceConfig = dailyMenuSourcePageRepository.findAll()

        sourceConfig.forEach {
            sc ->
            val menusForRestaurantAndDate = dailyMenuRepository.findByDateAndRestaurantName(currentDate(), sc.restaurantName)
            val extracted = extract(sc)

            if (menusForRestaurantAndDate.isEmpty()) {
                dailyMenuRepository.save(extracted)
            } else if (menusForRestaurantAndDate.size == 1 && !menusForRestaurantAndDate[0].equals(extracted)) {
                LOG.info("There was a change in menu for ${currentDate()} and ${sc.restaurantName}, updating the menu now.")
                dailyMenuRepository.delete(menusForRestaurantAndDate[0])
                dailyMenuRepository.save(extracted)
            } else {
                LOG.info("Menu already exists for ${currentDate()} and ${sc.restaurantName}, it won't be updated.")
            }
        }
    }

    @Override
    override fun extract(dailyMenuSourcePage: DailyMenuSourcePage?): DailyMenu {
        if (dailyMenuSourcePage == null)
            throw IllegalArgumentException("Parameters are $dailyMenuSourcePage")

        val html = Jsoup.connect(dailyMenuSourcePage.url).get()

        val extracted = DailyMenu(
                ID_IS_GENERATED,
                dailyMenuSourcePage.restaurantName,
                currentDate(),
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