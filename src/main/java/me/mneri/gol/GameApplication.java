package me.mneri.gol;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mneri.gol.data.config.Configuration;
import me.mneri.gol.data.config.ConfigurationProvider;
import me.mneri.gol.presentation.mvc.GameWindowController;

import javax.swing.*;

public class GameApplication extends AbstractModule {
    @Override
    protected void configure() {
        bind(Configuration.class).toProvider(ConfigurationProvider.class);
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
