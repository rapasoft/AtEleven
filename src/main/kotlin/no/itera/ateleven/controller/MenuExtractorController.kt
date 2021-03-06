package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.service.MenuExtractorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RestController
@RequestMapping(path = arrayOf("extractor"))
class MenuExtractorController @Autowired constructor(val menuExtractorService: MenuExtractorService) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.OK)
    fun testMenuExtraction(@RequestBody dailyMenuSourcePage: DailyMenuSourcePage): DailyMenu {
        return menuExtractorService.extract(dailyMenuSourcePage)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun extractAll() {
        menuExtractorService.extractData()
    }

}