package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import no.itera.ateleven.service.MenuExtractorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RestController
@RequestMapping(path = arrayOf("config"))
class MenuConfigController @Autowired constructor(val dailyMenuSourcePageRepository: DailyMenuSourcePageRepository) {

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    @ResponseStatus(HttpStatus.OK)
    fun testMenuExtraction(@RequestBody dailyMenuSourcePages: List<DailyMenuSourcePage>) {
        dailyMenuSourcePageRepository.save(dailyMenuSourcePages);
    }

}