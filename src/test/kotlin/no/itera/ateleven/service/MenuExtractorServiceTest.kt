package no.itera.ateleven.service

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(TestApplication::class))
open class MenuExtractorServiceTest {

    @Autowired lateinit private var menuExtractorService: MenuExtractorService
    @Autowired lateinit private var dailyMenuSourcePageRepository: DailyMenuSourcePageRepository
    @Autowired lateinit private var dailyMenuRepository: DailyMenuRepository

    @Before
    fun before() {
        dailyMenuSourcePageRepository.save(
                DailyMenuSourcePage(
                        "Astra Pub Ruzinov",
                        "https://www.zomato.com/widgets/daily_menu?entity_id=16508164",
                        "#menu-preview > div.tmi-groups > div:eq(0) > div:lt(3) > div.tmi-text-group > div",
                        "#menu-preview > div.tmi-groups > div:eq(0) > div:gt(2) > div.tmi-text-group > div",
                        null
                )
        )
    }

    @After
    fun after() {
        dailyMenuRepository.deleteAll()
    }

    @Test
    fun testExtractData() {
        menuExtractorService.extractData();

        val dailyMenus = dailyMenuRepository.findAll()

        assertNotNull(dailyMenus);
        assertEquals(1, dailyMenus.toList().size)
    }

    @Test
    fun testExtractAndUpdateData() {
        dailyMenuRepository.save(
                DailyMenu(
                        MenuExtractorServiceImpl.ID_IS_GENERATED,
                        "Astra Pub Ruzinov",
                        MenuExtractorServiceImpl.currentDate(),
                        arrayListOf("Drztkova", "Hubickova"),
                        arrayListOf("Szegin", "Gulas", "Salat", "Kura s ryzou"),
                        emptyList()
                )
        )

        val dailyMenusOld = dailyMenuRepository.findAll()

        menuExtractorService.extractData()

        val dailyMenusUpdated = dailyMenuRepository.findAll()

        assertNotEquals(dailyMenusOld, dailyMenusUpdated)

        dailyMenuRepository.deleteAll()
    }

}