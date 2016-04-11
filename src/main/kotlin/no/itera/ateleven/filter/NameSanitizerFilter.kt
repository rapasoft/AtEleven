package no.itera.ateleven.filter

import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.Food

/**
 * Created by Pavol Rajzak, Itera.
 */
class NameSanitizerFilter : DailyMenuFilter {

    override fun filter(dailyMenu: DailyMenu): DailyMenu {
        return DailyMenu(
                dailyMenu.id,
                dailyMenu.restaurantName,
                dailyMenu.date,
                dailyMenu.soups.map { s -> removeBS(s) },
                dailyMenu.mainDishes.map { s -> removeBS(s) },
                dailyMenu.other.map { s -> removeBS(s) }
        )
    }

    fun removeBS(input: Food): Food {
        return Food(input.description
                .replace("""[0-9]+\.""".toRegex(), "") // remove numbering
                .replace("""/{0,1}[0-9]+g/{0,1}""".toRegex(), "") // remove grams
                .replace("""0[,|\.][0-9]+\s{0,1}l{1}""".toRegex(), "") // remove litres
                .replace("""/{0,1}([0-9],*)+/{0,1}""".toRegex(), "") // remove alergens
                .replace("""€""".toRegex(), "") // remove eurosymbol
                .replace("""(Pondelok|Utorok|Streda|Štvrtok|Piatok)\:{0,1}""".toRegex(), "")
                .trim(), input.foodType)
    }

}