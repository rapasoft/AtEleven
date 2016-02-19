package no.itera.ateleven.repository

import no.itera.ateleven.model.DailyMenu
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Created by Pavol Rajzak, Itera.
 */
@Repository
interface DailyMenuRepository : CrudRepository<DailyMenu, Int> {

    fun findByDate(date: String): List<DailyMenu>

    fun findByDateAndRestaurantName(date: String, restaurantName: String): List<DailyMenu>

}