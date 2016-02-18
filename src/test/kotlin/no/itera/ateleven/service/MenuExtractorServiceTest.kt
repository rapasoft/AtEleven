package no.itera.ateleven.service

import no.itera.ateleven.Application
import no.itera.ateleven.config.SwaggerConfig
import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
open public class MenuExtractorServiceTest {

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

    @Test
    fun testExtractData() {
        menuExtractorService.extractData();

        val dailyMenus = dailyMenuRepository.findAll()

        assertNotNull(dailyMenus);
        assertEquals(1, dailyMenus.first().id)
    }

}