package me.mneri.gol;

import com.google.inject.AbstractModule;
import com.google.inject.CreationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.spi.Message;
import me.mneri.gol.presentation.mvc.GameWindowController;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class GameApplication extends AbstractModule {
    @Override
    protected void configure() {
        try {
            InputStream input = null;

            try {
                Properties properties = new Properties();
                String propertiesFileName = "application.properties";
                input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

                if (input == null) {
                    throw new FileNotFoundException(propertiesFileName + " not found in the classpath.");
                }

                properties.load(input);

                Names.bindProperties(binder(), properties);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            throw new CreationException(List.of(new Message(e.getMessage())));
        }
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        Injector injector = Guice.createInjector(new GameApplication());
        GameWindowController controller = injector.getInstance(GameWindowController.class);
        controller.run();
    }
}
