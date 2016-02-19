package no.itera.ateleven.repository

import no.itera.ateleven.model.DailyMenu
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by Pavol Rajzak, Itera.
 */
@RepositoryRestResource(collectionResourceRel = "daily-menu", path = "daily-menu")
interface DailyMenuRepository : CrudRepository<DailyMenu, Int> {

    fun findByDate(@Param(value = "date") date: String): List<DailyMenu>

    fun findByDateAndRestaurantName(@Param(value = "date") date: String, @Param(value = "restaurantName") restaurantName: String): List<DailyMenu>

}