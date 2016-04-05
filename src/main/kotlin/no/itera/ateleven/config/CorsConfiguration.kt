package no.itera.ateleven.config

import io.swagger.models.HttpMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Pavol Rajzak, Itera.
 */
@Configuration
open class CorsConfiguration {

    @Bean
    open fun corsFilter(): OncePerRequestFilter {
        return object : OncePerRequestFilter() {
            override fun doFilterInternal(request: HttpServletRequest?, response: HttpServletResponse?, filterChain: FilterChain?) {
                response?.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:9000");
                response?.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

                if (request?.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null && HttpMethod.OPTIONS.name.equals(request?.method)) {
                    // CORS "pre-flight" request
                    response?.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
                    response?.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.CONTENT_TYPE);
                    response?.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1");
                }

                filterChain?.doFilter(request, response);
            }

        }
    }

}