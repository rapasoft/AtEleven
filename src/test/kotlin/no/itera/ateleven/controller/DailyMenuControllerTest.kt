package no.itera.ateleven.controller

import jdk.nashorn.internal.runtime.regexp.joni.Matcher
import no.itera.ateleven.config.TestApplication
import no.itera.ateleven.repository.DailyMenuRepository
import org.hamcrest.Matchers
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
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
    lateinit var mockMvc: MockMvc

    @Before
    fun before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
        dailyMenuRepository.save(TestApplication.dailyMenuMock)
    }

    @Test
    fun testFindByDate() {
        val date = "2016-02-19"
        mockMvc.perform(get("/daily-menu/$date").accept(MediaType.ALL))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(Matchers.containsString("Test")))
    }

}