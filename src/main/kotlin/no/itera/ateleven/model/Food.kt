package no.itera.ateleven.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
data class Food(val description: String = "",
                @ManyToMany val foodType: List<FoodType> = emptyList(),
                @Id @GeneratedValue val id: Int? = null) {
}