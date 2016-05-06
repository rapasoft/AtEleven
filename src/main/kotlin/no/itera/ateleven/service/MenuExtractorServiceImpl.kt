package no.itera.ateleven.service

import no.itera.ateleven.filter.DailyMenuFilter
import no.itera.ateleven.filter.FoodAnnotationFilter
import no.itera.ateleven.filter.NameSanitizerFilter
import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.model.Food
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import no.itera.ateleven.repository.FoodRepository
import no.itera.ateleven.repository.FoodTypeRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.net.SocketTimeoutException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@Service
open class MenuExtractorServiceImpl @Autowired constructor(
        var dailyMenuSourcePageRepository: DailyMenuSourcePageRepository,
        var dailyMenuRepository: DailyMenuRepository,
        var foodTypeRepository: FoodTypeRepository,
        var foodRepository: FoodRepository
) : MenuExtractorService {

    companion object {
        val LOG: org.slf4j.Logger = LoggerFactory.getLogger(MenuExtractorServiceImpl::class.java.name)
        val ID_IS_GENERATED = null
        const val HOUR_IN_MS: Long = 1000 * 60 * 60

        fun currentDate() = SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()))
    }

    val dailyMenuFilters: List<DailyMenuFilter> = listOf(
            NameSanitizerFilter(),
            FoodAnnotationFilter()
    )

    @Scheduled(fixedDelay = HOUR_IN_MS)
    @Async
    override fun extractData() {
        val sourceConfig = dailyMenuSourcePageRepository.findAll()

        sourceConfig.forEach {
            sc ->
            val menusForRestaurantAndDate = dailyMenuRepository.findByDateAndRestaurantName(currentDate(), sc.restaurantName)
            val extracted = extract(sc)

            if (menusForRestaurantAndDate.isEmpty()) {
                persistExtracted(extracted)
            } else if (menusForRestaurantAndDate.size == 1 && !menusForRestaurantAndDate[0].menuEquals(extracted)) {
                LOG.info("There was a change in menu for ${currentDate()} and ${sc.restaurantName}, updating the menu now.")
                dailyMenuRepository.delete(menusForRestaurantAndDate[0])
                persistExtracted(extracted)
            } else {
                LOG.info("Menu already exists for ${currentDate()} and ${sc.restaurantName}, it won't be updated.")
            }
        }
    }

    private fun persistExtracted(extracted: DailyMenu) {
        dailyMenuRepository.save(DailyMenu(
                extracted.id,
                extracted.restaurantName,
                extracted.date,
                extracted.lastUpdated,
                extracted.soups.map { food -> persistFoodTypes(food) },
                extracted.mainDishes.map { food -> persistFoodTypes(food) },
                extracted.other.map { food -> persistFoodTypes(food) }
        ))
    }

    private fun persistFoodTypes(food: Food): Food {
        return foodRepository.save(Food(
                food.description,
                food.foodTypes.map { foodType ->
                    if (!foodTypeRepository.exists(foodType.type)) {
                        foodTypeRepository.save(foodType)
                    } else {
                        foodTypeRepository.findOne(foodType.type);
                    }
                },
                food.id
        ))
    }

    @Override
    override fun extract(dailyMenuSourcePage: DailyMenuSourcePage?): DailyMenu {
        if (dailyMenuSourcePage == null)
            throw IllegalArgumentException("Parameters are $dailyMenuSourcePage")

        val html = fetchPageWithReconnects(dailyMenuSourcePage, 3)

        var extracted = DailyMenu(
                ID_IS_GENERATED,
                dailyMenuSourcePage.restaurantName,
                currentDate(),
                Timestamp.from(Instant.now().atZone(ZoneId.of("GMT+2")).toInstant()),
                retrieveList(dailyMenuSourcePage.soupsPath, html),
                retrieveList(dailyMenuSourcePage.mainDishesPath, html),
                retrieveList(dailyMenuSourcePage.otherPath, html)
        )

        LOG.info("Extracted $extracted")

        dailyMenuFilters.forEach { d -> extracted = d.filter(extracted) }

        return extracted
    }

    private fun fetchPageWithReconnects(dailyMenuSourcePage: DailyMenuSourcePage, retries: Int): Document {
        try {
            return Jsoup.connect(dailyMenuSourcePage.url).get()
        } catch(e: SocketTimeoutException) {
            LOG.warn("Failed to download from ${dailyMenuSourcePage.url}, $retries more retries left")
            if (retries > 0) {
                return fetchPageWithReconnects(dailyMenuSourcePage, retries - 1)
            } else {
                throw e
            }
        }
    }

    private fun retrieveList(path: String?, html: Document): List<Food> {
        if (path != null && !path.equals("")) {
            return html.select(path).map { el -> Food(el.text(), emptyList()) }
        }
        return emptyList()
    }

}