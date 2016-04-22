package no.itera.ateleven.filter

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.Food
import no.itera.ateleven.model.FoodAnnotation

/**
 * Created by Pavol Rajzak, Itera.
 */
class FoodAnnotationFilter : DailyMenuFilter {

    val annotationList = arrayListOf(
            FoodAnnotation("kuracie", """(kura.*)""".toRegex(RegexOption.IGNORE_CASE), "{\"backgroundColor\": \"blanchedalmond\"}"),
            FoodAnnotation("hovädzie", """(hov[ä|a]dz.*)""".toRegex(RegexOption.IGNORE_CASE), "{\"backgroundColor\": \"darkred\", \"color\": \"white\"}"),
            FoodAnnotation("bravčové", """(brav[c|č]ov.*)""".toRegex(RegexOption.IGNORE_CASE), "{\"backgroundColor\": \"pink\"}"),
            FoodAnnotation("hamburger", """(hamburg.*)|(burger)""".toRegex(RegexOption.IGNORE_CASE), "{\"backgroundColor\": \"mediumvioletred\", \"color\": \"white\"}"),
            FoodAnnotation("pizza", """(pizza.*)""".toRegex(RegexOption.IGNORE_CASE), "{\"backgroundColor\": \"darkorange\"}"),
            FoodAnnotation("cestoviny",
                    """(cestovin.*|penne|[š|]pagh{0,1}ett{0,1}[i|y]|tagliatelle|bulgur|gnocchi|la[s|z]ag{0,1}ne|fusilli)"""
                            .toRegex(RegexOption.IGNORE_CASE), "{ \"backgroundColor\": \"darkgreen\", \"color\": \"white\" }"),
            FoodAnnotation("šalát", """([š|s]al[a|á]t).*""".toRegex(RegexOption.IGNORE_CASE), "{ \"border\": \"1px darkgreen solid\", \"color\":\"darkgreen\"}")
    )

    override fun filter(dailyMenu: DailyMenu): DailyMenu {
        return DailyMenu(
                dailyMenu.id,
                dailyMenu.restaurantName,
                dailyMenu.date,
                dailyMenu.lastUpdated,
                dailyMenu.soups.map { s -> addAnnotations(s) },
                dailyMenu.mainDishes.map { s -> addAnnotations(s) },
                dailyMenu.other.map { s -> addAnnotations(s) }
        )
    }

    fun addAnnotations(food: Food): Food {
        var mutableInput = food
        annotationList.forEach { annotation -> mutableInput = annotation.apply(mutableInput) }
        return mutableInput
    }

}