package no.itera.ateleven.repository

import no.itera.ateleven.model.Food
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Pavol Rajzak, Itera.
 */
@Repository
interface FoodRepository : CrudRepository<Food, Int> {
}