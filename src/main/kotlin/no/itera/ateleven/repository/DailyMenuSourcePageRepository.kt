package no.itera.ateleven.repository

import no.itera.ateleven.model.DailyMenuSourcePage
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Pavol Rajzak, Itera.
 */
@Repository
interface DailyMenuSourcePageRepository : CrudRepository<DailyMenuSourcePage, String> {
}