package org.ibo.manager;

import javafx.application.Application;
import org.ibo.manager.bot.KDRSBot;
import org.ibo.manager.gui.GUIApplication;
import org.ibo.manager.repositories.TelegramUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Main SpringBoot start point
 * since javaFX is used Application Context is setup in the javaFX {@link GUIApplication#init()} method.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
@EnableWebSecurity
public class ManagerApplication {
    private static final Logger logger = LoggerFactory.getLogger( ManagerApplication.class );


    public static void main( String[] args ) {
        Application.launch( GUIApplication.class, args );

        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
        TelegramUserRepository repository = appContext.getBean(TelegramUserRepository.class);

        KDRSBot bot = new KDRSBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            logger.info("Registered Telegram Bot (username: {}, token: {}).", bot.getBotUsername(), bot.getBotToken());
        }
        catch (TelegramApiException exception) {
            logger.error("Could not register Telegram Bot (username: {}, token: {}).\n Exception thrown: {}",
                    bot.getBotUsername(), bot.getBotToken(), exception.getMessage());
        }
    }
}
