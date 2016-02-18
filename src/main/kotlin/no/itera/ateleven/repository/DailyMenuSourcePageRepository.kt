package no.itera.ateleven.repository

import no.itera.ateleven.model.DailyMenuSourcePage
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by Pavol Rajzak, Itera.
 */
@RepositoryRestResource(collectionResourceRel = "menu-config", path = "menu-config")
interface DailyMenuSourcePageRepository : CrudRepository<DailyMenuSourcePage, String> {
}