package no.itera.ateleven.repository

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenu
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(TestApplication::class))
@WebAppConfiguration
class DailyMenuRepositoryTest {

    @Autowired lateinit var dailyMenuRepository: DailyMenuRepository

    @Test
    fun shouldSaveDailyMenu() {
        var dailyMenu = DailyMenu(
                null,
                "Test",
                "2016-01-18",
                Arrays.asList("Mrkvova", "Cicerova"),
                Arrays.asList("Hovadzi gulas", "Slovenske rizoto"),
                emptyList<String>())

        dailyMenu = dailyMenuRepository.save(dailyMenu)

        assertNotNull(dailyMenu)
        assertNotNull(dailyMenu.id)
        assertEquals(1f, dailyMenu.id!!.toFloat(), 0f)
    }

    @Test(expected = Exception::class)
    fun shouldNotSaveSameMenuForRestaurantAndDay() {
        val firstDailyMenu = DailyMenu(
                null,
                "YetAnotherRestaurant",
                "2016-01-20",
                Arrays.asList("Mrkvova", "Cicerova"),
                Arrays.asList("Hovadzi gulas", "Slovenske rizoto"),
                emptyList<String>())

        val secondDailyMenu = DailyMenu(
                null,
                "YetAnotherRestaurant",
                "2016-01-20",
                Arrays.asList("Mrkvova", "Cicerova"),
                Arrays.asList("Hovadzi gulas", "Slovenske rizoto"),
                emptyList<String>())

        dailyMenuRepository.save(firstDailyMenu)
        dailyMenuRepository.save(secondDailyMenu)
    }

}