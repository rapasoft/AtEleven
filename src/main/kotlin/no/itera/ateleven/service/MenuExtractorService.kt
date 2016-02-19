package no.itera.ateleven.service

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import javax.transaction.Transactional

/**
 * Created by Pavol Rajzak, Itera.
 */
interface MenuExtractorService {

    open fun extractData()

    open fun extract(dailyMenuSourcePage: DailyMenuSourcePage?): DailyMenu
}