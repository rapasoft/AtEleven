package no.itera.ateleven.model

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
data class FoodType(@Id val type: String, val style: String) {
    constructor() : this("", "")
}