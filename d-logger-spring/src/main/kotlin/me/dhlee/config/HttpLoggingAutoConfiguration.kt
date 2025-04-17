package me.dhlee.config

import me.dhlee.filter.DefaultPathFilter
import me.dhlee.filter.HttpLoggingFilter
import me.dhlee.filter.HttpLoggingInterceptor
import me.dhlee.filter.PathFilter
import me.dhlee.format.DefaultHttpLogFormatter
import me.dhlee.format.HttpLogFormatter
import me.dhlee.logger.HttpLogger
import me.dhlee.properties.SpringHttpLoggingProperties
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableConfigurationProperties(SpringHttpLoggingProperties::class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@AutoConfiguration
class HttpLoggingAutoConfiguration(
    private val props: SpringHttpLoggingProperties
) {
    @Bean
    @ConditionalOnMissingBean(HttpLogFormatter::class)
    fun httpLogFormatter(props: SpringHttpLoggingProperties): HttpLogFormatter {
        return DefaultHttpLogFormatter(props.toCoreProperties())
    }

    @Bean
    @ConditionalOnMissingBean(HttpLogger::class)
    fun httpLogger(formatter: HttpLogFormatter): HttpLogger {
        return HttpLogger(formatter)
    }

    @Bean
    @ConditionalOnMissingBean(PathFilter::class)
    fun pathFilter(): PathFilter {
        return DefaultPathFilter(
            include = props.includePaths,
            exclude = props.excludePaths
        )
    }

    @Bean
    @ConditionalOnMissingBean(HttpLoggingFilter::class)
    fun httpLoggingFilter(
        httpLogger: HttpLogger,
        pathFilter: PathFilter,
    ): FilterRegistrationBean<HttpLoggingFilter> {
        val filter = HttpLoggingFilter(props, httpLogger, pathFilter)
        return FilterRegistrationBean(filter).apply { order = Ordered.HIGHEST_PRECEDENCE }
    }

    @Bean
    @ConditionalOnMissingBean(HttpLoggingInterceptor::class)
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Configuration(proxyBeanMethods = false)
    internal class InterceptorRegistrationConfiguration(
        private val httpLoggingInterceptor: HttpLoggingInterceptor
    ) : WebMvcConfigurer {
        override fun addInterceptors(registry: InterceptorRegistry) {
            registry.addInterceptor(httpLoggingInterceptor)
                .addPathPatterns("/**")
                .order(Ordered.HIGHEST_PRECEDENCE)
        }
    }
}