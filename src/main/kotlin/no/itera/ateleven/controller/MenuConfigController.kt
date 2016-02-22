package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
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
    @ResponseStatus(HttpStatus.CREATED)
    fun addMenuConfiguration(@RequestBody dailyMenuSourcePages: List<DailyMenuSourcePage>) {
        dailyMenuSourcePageRepository.save(dailyMenuSourcePages);
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getAll(): ResponseEntity<List<DailyMenuSourcePage>> {
        return ResponseEntity.ok(dailyMenuSourcePageRepository.findAll().toArrayList());
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), path = arrayOf("{name}"))
    fun getOne(@PathVariable name: String): ResponseEntity<DailyMenuSourcePage> {
        val dailyMenuSourcePage: DailyMenuSourcePage? = dailyMenuSourcePageRepository.findOne(name);

        if (dailyMenuSourcePage != null) {
            return ResponseEntity.ok(dailyMenuSourcePage);
        }

        return ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT), path = arrayOf("{name}"))
    fun update(@PathVariable name: String, @RequestBody updated: DailyMenuSourcePage): ResponseEntity<DailyMenuSourcePage> {
        val dailyMenuSourcePage: DailyMenuSourcePage? = dailyMenuSourcePageRepository.findOne(name);

        if (dailyMenuSourcePage != null) {
            dailyMenuSourcePageRepository.delete(dailyMenuSourcePage)
        }

        dailyMenuSourcePageRepository.save(updated);

        return ResponseEntity(updated, HttpStatus.CREATED)
    }

    @RequestMapping(method = arrayOf(RequestMethod.DELETE), path = arrayOf("{name}"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable name: String) {
        dailyMenuSourcePageRepository.delete(name)
    }

}