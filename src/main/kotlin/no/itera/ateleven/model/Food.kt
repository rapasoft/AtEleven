package no.itera.ateleven.model

import javax.persistence.Embeddable

/**
 * Created by Pavol Rajzak, Itera.
 */
@Embeddable
data class Food(val description: String, val type: String, val style: String = "") {
    constructor() : this("", "", "")
}