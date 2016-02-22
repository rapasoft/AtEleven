package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.service.MenuExtractorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RestController
@RequestMapping(path = arrayOf("extractor"))
class MenuExtractorController @Autowired constructor(val menuExtractorService: MenuExtractorService) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun testMenuExtraction(@RequestBody dailyMenuSourcePage: DailyMenuSourcePage): ResponseEntity<DailyMenu> {
        return ResponseEntity.ok(menuExtractorService.extract(dailyMenuSourcePage))
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    @ResponseStatus(HttpStatus.OK)
    fun extractAll() {
        menuExtractorService.extractData()
    }

}