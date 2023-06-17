package org.ibo.manager;

import org.ibo.manager.repositories.Database;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * This class is used to initialize the Database on spring {@link ApplicationReadyEvent}
 * it passes the context to the database
 */
@Component
public final class StartUpAction {
    private final ApplicationContext applicationContext;

    public StartUpAction( ApplicationContext applicationContext ) {
        this.applicationContext = applicationContext;
    }

    @EventListener( ApplicationReadyEvent.class)
    public void setUpDatabase() {
        Database.setUpDatabase( applicationContext );
    }
}
