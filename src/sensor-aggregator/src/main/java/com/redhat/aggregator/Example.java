package com.redhat.aggregator;

import org.apache.camel.builder.RouteBuilder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Example extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&period=60000")
        .setBody().simple("body !")
            .to("stream:out");
    }
}