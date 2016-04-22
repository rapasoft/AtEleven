package no.itera.ateleven.model

import java.sql.Timestamp
import java.time.Instant
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
        val lastUpdated: Timestamp,
        @OneToMany(fetch = EAGER) val soups: List<Food>,
        @OneToMany(fetch = EAGER) val mainDishes: List<Food>,
        @OneToMany(fetch = EAGER) val other: List<Food>) {

    constructor() : this(null, "", "", Timestamp.from(Instant.now()), emptyList(), emptyList(), emptyList())

    companion object {
        fun textify(foodList: List<Food>): String {
            return foodList.map { food -> food.description }.joinToString(",")
        }
    }

    fun menuEquals(other: DailyMenu): Boolean =
            textify(this.soups).equals(textify(other.soups)) &&
                    textify(this.mainDishes).equals(textify(other.mainDishes)) &&
                    textify(this.other).equals(textify(other.other))

}