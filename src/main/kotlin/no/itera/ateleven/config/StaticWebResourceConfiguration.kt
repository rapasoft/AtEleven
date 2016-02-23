package no.itera.ateleven.config

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry

/**
 * Created by Pavol Rajzak, Itera.
 */
@Configuration
open class StaticWebResourceConfiguration : WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter() {

    companion object {
        val RESOURCE_LOCATION: String = "classpath:/resources/static"
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(RESOURCE_LOCATION)
        }
    }
}