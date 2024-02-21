package com.redhat;

import org.jboss.logging.Logger;

import io.quarkus.runtime.Startup;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
public class AppLifecycle extends Application {
    private static final Logger LOGGER = Logger.getLogger("AppLifecycle");
    
    @Startup
    void onStart() {               
        LOGGER.info("The application is starting...");
    }


}
