package no.itera.ateleven.model

import javax.persistence.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@Entity
@Table(uniqueConstraints = arrayOf(UniqueConstraint(name = "unique_date_and_restaurant", columnNames = arrayOf("date", "restaurantName"))))
data class DailyMenu(
        @Id @GeneratedValue val id: Int?,
        val restaurantName: String,
        val date: String,
        @ElementCollection(fetch = javax.persistence.FetchType.EAGER) val soups: List<Food>,
        @ElementCollection(fetch = javax.persistence.FetchType.EAGER) val mainDishes: List<Food>,
        @ElementCollection(fetch = javax.persistence.FetchType.EAGER) val other: List<Food>) {
    constructor() : this(null, "", "", emptyList(), emptyList(), emptyList())

    fun menuEquals(other: DailyMenu): Boolean =
            soups.equals(other.soups) &&
                    mainDishes.equals(other.mainDishes) &&
                    other.other.equals(other.other)

}