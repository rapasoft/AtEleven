package no.itera.ateleven.controller

import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.repository.DailyMenuRepository
import no.itera.ateleven.repository.FoodRepository
import no.itera.ateleven.service.MenuExtractorServiceImpl
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(TestApplication::class))
class DailyMenuControllerTest {

    @Autowired lateinit var wac: WebApplicationContext
    @Autowired lateinit var dailyMenuRepository: DailyMenuRepository
    @Autowired lateinit var foodRepository: FoodRepository
    lateinit var mockMvc: MockMvc

    @Before
    fun before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
        val dailyMenu = TestApplication.dailyMenuMock()

        foodRepository.save(dailyMenu.mainDishes)
        foodRepository.save(dailyMenu.soups)
        foodRepository.save(dailyMenu.other)

        dailyMenuRepository.save(dailyMenu)
    }

    @After
    fun after() {
        dailyMenuRepository.deleteAll()
    }

    @Test
    fun testFindByDate() {
        val date = MenuExtractorServiceImpl.currentDate()
        mockMvc.perform(get("/daily-menu/$date").accept(MediaType.ALL))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Matchers.containsString("Test")))
    }

}