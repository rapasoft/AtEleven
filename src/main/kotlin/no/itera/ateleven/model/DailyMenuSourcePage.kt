package no.itera.ateleven.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
data class DailyMenuSourcePage(
        @Id val restaurantName: String,
        val url: String,
        @Column(columnDefinition = "varchar(1000)") val soupsPath: String?,
        @Column(columnDefinition = "varchar(1000)") val mainDishesPath: String?,
        @Column(columnDefinition = "varchar(1000)") val otherPath: String?) {
        constructor() : this("", "", "", "", "")
}