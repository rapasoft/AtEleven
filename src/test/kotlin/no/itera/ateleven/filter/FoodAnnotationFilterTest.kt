package no.itera.ateleven.filter

import no.itera.ateleven.model.Food
import no.itera.ateleven.model.FoodType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Pavol Rajzak, Itera.
 */
class FoodAnnotationFilterTest {

    @Test
    fun annotatePork() {
        val input = Food("Bravčová rolka s prosciuttom a bazalkou, zemiakovo-petržlenové pyré, maslová omáčka", emptyList())
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, arrayListOf(FoodType("bravčové", "{\"backgroundColor\": \"pink\"}"))).foodType, output.foodType);
    }

    @Test
    fun annotateChicken() {
        val input = Food("Grilovaný kurací steak na anglickej zelenine podávaný s dusenou ryžou", emptyList())
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, arrayListOf(FoodType("kuracie", "{\"backgroundColor\": \"blanchedalmond\"}"))).foodType, output.foodType);
    }

    @Test
    fun annotateBeef() {
        val input = Food("Ponuka dňa: HOVÄDZIE LÍĆKA NA KOREŃOVEJ ZELENINE SO ŹEMĹOVÝMI KNEDĹAMI", emptyList())
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, arrayListOf(FoodType("hovädzie", "{\"backgroundColor\": \"darkred\", \"color\": \"white\"}"))).foodType, output.foodType);
    }

    @Test
    fun annotatePizza() {
        val input = Food("Pizza Cartago: pomodoro, mozzarella, šunka, paprika, vajce", emptyList())
        val output = FoodAnnotationFilter().addAnnotations(input);

        assertEquals(Food(input.description, arrayListOf(FoodType("pizza", "{\"backgroundColor\": \"darkorange\"}"))).foodType, output.foodType);
    }

    @Test
    fun annotateMultiple() {
        val input = Food("Kuracia polievka so zeleninou a cestovinou", emptyList())
        val output = FoodAnnotationFilter().addAnnotations(input)

        assertEquals(Food(input.description, arrayListOf(FoodType("kuracie", "{\"backgroundColor\": \"blanchedalmond\"}"),
                FoodType("cestoviny", "{ \"backgroundColor\": \"darkgreen\", \"color\": \"white\" }"))).foodType, output.foodType)
    }


}