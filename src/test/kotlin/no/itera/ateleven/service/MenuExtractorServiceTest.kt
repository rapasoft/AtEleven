package no.itera.ateleven.service

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenu
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
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

    lateinit private var menuExtractorService: MenuExtractorServiceImpl
    lateinit private var dailyMenuSourcePage: DailyMenuSourcePage
    @Autowired lateinit private var dailyMenuSourcePageRepository: DailyMenuSourcePageRepository
    @Autowired lateinit private var dailyMenuRepository: DailyMenuRepository

    @Before
    fun before() {
        dailyMenuSourcePage = DailyMenuSourcePage(
                "Astra Pub Ruzinov",
                "file://test-page.html",
                ".soups > soup",
                ".main-dishes > .main-dish",
                ".others > other"
        )
        dailyMenuSourcePageRepository.save(dailyMenuSourcePage)
        menuExtractorService = mock(MenuExtractorServiceImpl::class.java)
        menuExtractorService.dailyMenuRepository = dailyMenuRepository
        menuExtractorService.dailyMenuSourcePageRepository = dailyMenuSourcePageRepository
        Mockito.`when`(menuExtractorService.extractData()).thenCallRealMethod()
        Mockito.`when`(menuExtractorService.extract(Mockito.any(DailyMenuSourcePage::class.java))).thenReturn(TestApplication.dailyMenuMock())
    }

    @After
    fun after() {
        dailyMenuRepository.deleteAll()
    }

    @Test
    fun testExtractData() {
        dailyMenuRepository.deleteAll()
        menuExtractorService.extractData();
        Mockito.verify(menuExtractorService).extract(dailyMenuSourcePage)

        assertFalse(dailyMenuRepository.findByDate(MenuExtractorServiceImpl.currentDate()).isEmpty());
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