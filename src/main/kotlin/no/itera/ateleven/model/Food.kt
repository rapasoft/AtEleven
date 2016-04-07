package no.itera.ateleven.model

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
data class Food(@Id @GeneratedValue val id: Int? = null,
                val description: String = "",
                @ElementCollection val foodType: List<FoodType> = emptyList()) {
}