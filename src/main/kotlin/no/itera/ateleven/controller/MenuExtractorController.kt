package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.service.MenuExtractorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

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
    fun extractAll() {
        menuExtractorService.extractData()
    }


}