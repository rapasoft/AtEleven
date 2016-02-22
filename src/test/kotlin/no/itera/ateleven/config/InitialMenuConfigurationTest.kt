package no.itera.ateleven.config

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

/**
 * Created by Pavol Rajzak, Itera.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(TestApplication::class, InitialMenuConfiguration::class))
class InitialMenuConfigurationTest {

    @Autowired @Qualifier("defaultMenuConfigurationInitialized") var menuConfigInitialized: Boolean = false

    @Test
    fun testDefaultMenuConfigInitialized() {
        assertTrue(menuConfigInitialized)
    }

}