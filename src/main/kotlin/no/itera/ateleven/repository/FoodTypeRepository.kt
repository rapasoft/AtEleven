package no.itera.ateleven.repository

import no.itera.ateleven.model.FoodType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Created by Pavol Rajzak, Itera.
 */
@Repository
interface FoodTypeRepository : CrudRepository<FoodType, String> {}