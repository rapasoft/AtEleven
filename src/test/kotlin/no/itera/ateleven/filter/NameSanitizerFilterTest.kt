package no.itera.ateleven.filter

import no.itera.ateleven.model.Food
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Pavol Rajzak, Itera.
 */
class NameSanitizerFilterTest {

    @Test
    fun removeBS() {
        val testString = Food("2. 150g 0,25l Morská šťuka na grile, smotanový hrášok s čiernym koreňom, pečený citrón /4,7/ 1,3,7");
        val output = NameSanitizerFilter().removeBS(testString)

        assertEquals(Food("Morská šťuka na grile, smotanový hrášok s čiernym koreňom, pečený citrón"), output)
    }
}