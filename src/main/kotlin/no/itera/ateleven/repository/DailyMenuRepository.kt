package no.itera.ateleven.repository

import no.itera.ateleven.model.DailyMenu
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by Pavol Rajzak, Itera.
 */
@RepositoryRestResource(collectionResourceRel = "daily-menu", path = "daily-menu")
interface DailyMenuRepository : CrudRepository<DailyMenu, Int> {

    fun findByDate(date : String) : DailyMenu

}