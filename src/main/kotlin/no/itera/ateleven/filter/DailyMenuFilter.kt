package no.itera.ateleven.filter

import no.itera.ateleven.model.DailyMenu

/**
 * Created by Pavol Rajzak, Itera.
 */
interface DailyMenuFilter {

    fun filter(dailyMenu: DailyMenu): DailyMenu

}