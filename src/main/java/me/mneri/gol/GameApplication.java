package me.mneri.gol;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import me.mneri.gol.data.config.Configuration;
import me.mneri.gol.presentation.mvc.GameWindowController;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameApplication extends AbstractModule {
    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        Injector injector = Guice.createInjector(new GameApplication());
        GameWindowController controller = injector.getInstance(GameWindowController.class);
        controller.run();
    }

    @Provides
    public Configuration configuration() throws IOException {
        InputStream input = null;

        try {
            Properties properties = new Properties();
            String propertiesFileName = "application.properties";
            input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

            if (input == null) {
                throw new FileNotFoundException(propertiesFileName + " not found in the classpath.");
            }

            properties.load(input);

            return new Configuration(properties);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
