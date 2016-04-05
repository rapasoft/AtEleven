package no.itera.ateleven.filter

import no.itera.ateleven.model.Food
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Pavol Rajzak, Itera.
 */
class FoodAnnotationFilterTest {

    @Test
    fun annotatePork() {
        val input = Food("Bravčová rolka s prosciuttom a bazalkou, zemiakovo-petržlenové pyré, maslová omáčka", "")
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, "bravcove").type, output.type);
    }

    @Test
    fun annotateChicken() {
        val input = Food("Grilovaný kurací steak na anglickej zelenine podávaný s dusenou ryžou", "")
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, "kuracie").type, output.type);
    }

    @Test
    fun annotateBeef() {
        val input = Food("Ponuka dňa: HOVÄDZIE LÍĆKA NA KOREŃOVEJ ZELENINE SO ŹEMĹOVÝMI KNEDĹAMI", "")
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, "hovadzie").type, output.type);
    }

    @Test
    fun annotatePizza() {
        val input = Food("Pizza Cartago: pomodoro, mozzarella, šunka, paprika, vajce", "")
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, "pizza").type, output.type);
    }


}