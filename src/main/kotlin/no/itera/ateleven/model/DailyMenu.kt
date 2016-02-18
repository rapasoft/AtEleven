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
        @Column(unique = true) val date: String,
        @ElementCollection val soups: List<String>,
        @ElementCollection val mainDishes: List<String>,
        @ElementCollection val other: List<String>) {
    constructor() : this(null, "", "", emptyList(), emptyList(), emptyList())
}