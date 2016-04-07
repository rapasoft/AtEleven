package no.itera.ateleven.model

/**
 * Created by Pavol Rajzak, Itera.
 */
data class FoodAnnotation(val name: String, val regex: Regex, val style: String) {

    fun apply(input: Food): Food {
        input.description.split(" ").forEach { e ->
            if (regex.matches(e.trim())) {
                return Food(input.id, input.description, input.foodType.plus(FoodType(name, style)))
            }
        }

        return input
    }

}