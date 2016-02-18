package no.itera.ateleven.model

import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
data class DailyMenuSourcePage (
        @Id val restaurantName: String,
        val url: String,
        val soupsPath: String?,
        val mainDishesPath: String?,
        val otherPath: String?) {
    constructor() : this("","","","","")
}