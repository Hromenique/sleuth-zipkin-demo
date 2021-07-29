package br.com.hrom.randomnumber2.config.web

import brave.Tracing
import brave.context.slf4j.MDCScopeDecorator
import brave.propagation.ThreadLocalCurrentTraceContext
import brave.sampler.Sampler
import brave.servlet.TracingFilter
import mu.KotlinLogging
import org.slf4j.MDC
import zipkin2.reporter.brave.AsyncZipkinSpanHandler
import zipkin2.reporter.okhttp3.OkHttpSender
import javax.servlet.*

class ZipkinTracingFilter : Filter {
    private val log = KotlinLogging.logger { }

    private lateinit var sender: OkHttpSender
    private lateinit var zipkinSpanHandler: AsyncZipkinSpanHandler
    private lateinit var tracing: Tracing
    private lateinit var delegate: Filter

    override fun init(filterConfig: FilterConfig) {
        log.info("Initializing DelegationTracingFilter...")

        val applicationName = filterConfig.servletContext.getInitParameter("applicationName")
        val zipkinUrl = filterConfig.servletContext.getInitParameter("zipkinUrl")

        sender = OkHttpSender.create("$zipkinUrl/api/v2/spans")
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender)
        tracing = Tracing.newBuilder()
                .currentTraceContext(
                        ThreadLocalCurrentTraceContext.newBuilder()
                                .addScopeDecorator(MDCScopeDecorator.get())
                                .build()
                )
                .localServiceName(applicationName)
                .addSpanHandler(zipkinSpanHandler)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build()
        delegate = TracingFilter.create(tracing)

        log.info("DelegationTracingFilter initialized")
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) =
            delegate.doFilter(request, response, chain)

    override fun destroy() = try {
        tracing.close()
        zipkinSpanHandler.close()
        sender.close()
    } catch (ex: Exception) {
        log.error(ex) { "Error on destroy DelegationTracingFilter: ${ex.message}" }
    }
}