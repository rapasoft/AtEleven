package no.itera.ateleven.service

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.model.DailyMenuSourcePage
import no.itera.ateleven.model.Food
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.DailyMenuSourcePageRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import java.util.*

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(TestApplication::class))
open class MenuExtractorServiceTest {

    lateinit private var menuExtractorService: MenuExtractorServiceImpl
    lateinit private var dailyMenuSourcePage: DailyMenuSourcePage
    lateinit private var dailyMenuSourcePageRepository: DailyMenuSourcePageRepository
    lateinit private var dailyMenuRepository: DailyMenuRepository
    val dailyMenuMock = TestApplication.dailyMenuMock("TestExtract")

    @Before
    fun before() {
        dailyMenuSourcePage = DailyMenuSourcePage(
                "TestExtract",
                "file://test-page.html",
                ".soups > soup",
                ".main-dishes > .main-dish",
                ".others > other"
        )

        dailyMenuRepository = mock(DailyMenuRepository::class.java)
        dailyMenuSourcePageRepository = mock(DailyMenuSourcePageRepository::class.java)

        menuExtractorService = mock(MenuExtractorServiceImpl::class.java)
        menuExtractorService.dailyMenuRepository = dailyMenuRepository
        menuExtractorService.dailyMenuSourcePageRepository = dailyMenuSourcePageRepository

        Mockito.`when`(dailyMenuSourcePageRepository.findAll()).thenReturn(mockIterable(dailyMenuSourcePage))
        Mockito.`when`(menuExtractorService.extractData()).thenCallRealMethod()
        Mockito.`when`(menuExtractorService.extract(Mockito.any(DailyMenuSourcePage::class.java))).thenReturn(dailyMenuMock)
    }

    private fun mockIterable(dailyMenuSourcePage: DailyMenuSourcePage): Iterable<DailyMenuSourcePage>? {
        return Iterable({ Collections.singletonList(dailyMenuSourcePage).iterator() })
    }

    @Test
    fun testExtractData() {
        menuExtractorService.extractData();

        Mockito.verify(menuExtractorService).extract(dailyMenuSourcePage)
        Mockito.verify(menuExtractorService.dailyMenuRepository).save(dailyMenuMock)
    }

    @Test
    fun testExtractAndUpdateData() {
        val replacement = TestApplication.dailyMenuMock("TestExtract", Collections.singletonList(Food("IbaDrzkova", "")))
        Mockito.`when`(dailyMenuRepository.findByDateAndRestaurantName(MenuExtractorServiceImpl.currentDate(), "TestExtract")).thenReturn(
                Collections.singletonList(dailyMenuMock)
        )
        Mockito.`when`(menuExtractorService.extract(dailyMenuSourcePage)).thenReturn(replacement)

        menuExtractorService.extractData();

        Mockito.verify(menuExtractorService).extract(dailyMenuSourcePage)
        Mockito.verify(menuExtractorService.dailyMenuRepository).delete(dailyMenuMock)
        Mockito.verify(menuExtractorService.dailyMenuRepository).save(replacement)
    }

    @Test
    fun testDoNotUpdateWhenNoDataChanged() {
        Mockito.`when`(dailyMenuRepository.findByDateAndRestaurantName(MenuExtractorServiceImpl.currentDate(), "TestExtract")).thenReturn(
                Collections.singletonList(dailyMenuMock)
        )
        Mockito.`when`(menuExtractorService.extract(dailyMenuSourcePage)).thenReturn(dailyMenuMock)

        menuExtractorService.extractData();

        Mockito.verify(menuExtractorService).extract(dailyMenuSourcePage)
        Mockito.verify(menuExtractorService.dailyMenuRepository, never()).save(dailyMenuMock)
    }

}