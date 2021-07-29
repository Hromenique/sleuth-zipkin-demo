package br.com.hrom.randomnumber2

import br.com.hrom.randomnumber2.api.RandomNumber2Controller
import br.com.hrom.randomnumber2.config.web.ZipkinTracingFilter
import br.com.hrom.randomnumber2.core.RandomNumberProvider2
import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.slf4j.MDC
import java.util.*
import javax.servlet.DispatcherType


fun main() {
    val properties = readPropertiesFile()
    val port = properties.getProperty("server.port").toInt()
    val applicationName = properties.getProperty("application.name")
    val zipkinUrl = properties.getProperty("zipkin.url")

    val app = Javalin.create { config ->
        config.server {
            val server = Server()

            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            context.contextPath = "/"
            context.setInitParameter("applicationName", applicationName)
            context.setInitParameter("zipkinUrl", zipkinUrl)

            context.addFilter(ZipkinTracingFilter::class.java, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST))
            server.handler = context

            server
        }
    }.before {
        MDC.put("applicationName", applicationName)
    }

    RandomNumber2Controller(RandomNumberProvider2(), app)

    app.start(port)
}

private fun readPropertiesFile(): Properties {
    val s = ClassLoader.getSystemResourceAsStream("application.properties")
    return Properties().apply { this.load(s) }
}

