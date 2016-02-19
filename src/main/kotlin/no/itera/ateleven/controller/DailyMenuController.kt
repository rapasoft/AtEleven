package no.itera.ateleven.controller

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.service.MenuExtractorServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RestController
@RequestMapping(path = arrayOf("daily-menu"), method = arrayOf(RequestMethod.GET))
@ResponseStatus(HttpStatus.OK)
class DailyMenuController @Autowired constructor(val dailyMenuRepository: DailyMenuRepository) {

    companion object {
        val LOG : Logger = LoggerFactory.getLogger(DailyMenuController::class.java.name)
    }

    @RequestMapping
    fun get() = dailyMenuRepository.findByDate(MenuExtractorServiceImpl.currentDate())

    @RequestMapping(path = arrayOf("/{date}"))
    fun findByDate(@PathVariable date: String): List<DailyMenu> {
        return dailyMenuRepository.findByDate(date)
    }

    @ExceptionHandler(value = Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exceptionHandler(httpRequest: HttpRequest, exception: Exception) {
        LOG.error("Could not process request ${httpRequest.uri}, the cause is ${exception.cause}")
    }

}