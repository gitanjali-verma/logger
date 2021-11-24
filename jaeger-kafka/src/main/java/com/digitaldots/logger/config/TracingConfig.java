package com.digitaldots.logger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.internal.MDCScopeManager;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;

@Configuration
public class TracingConfig {

    @Value("${platform.tracing.sender.host}")
    private String jaegerHost;
    @Value("${platform.tracing.sender.port}")
    private Integer jaegerPort;
    @Value("${platform.tracing.service-name}")
    private String applicationName;
    @Value("${platform.tracing.enabled}")
    private boolean tracing;

    @Bean
    public Tracer tracer() {
        if (!tracing) {
            return NoopTracerFactory.create();
        }

        MDCScopeManager scopeManager = new MDCScopeManager.Builder().build();        
        return io.jaegertracing.Configuration.fromEnv(applicationName).withSampler(
                io.jaegertracing.Configuration.SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1))
                .withReporter(io.jaegertracing.Configuration.ReporterConfiguration.fromEnv().withLogSpans(true)
                        .withFlushInterval(1000).withMaxQueueSize(10000)
                        .withSender(io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
                                .withAgentHost(jaegerHost).withAgentPort(jaegerPort)))
                .getTracerBuilder().withScopeManager(scopeManager).build();
    }

//    @PostConstruct
//    public void registerToGlobalTracer() {
//        if (!GlobalTracer.isRegistered()) {
//            GlobalTracer.registerIfAbsent(tracer());
//        }
//    }
}
