package me.mneri.gol;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mneri.gol.presentation.mvc.GameWindowController;

import javax.swing.*;

public class GameApplication {
    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        Injector injector = Guice.createInjector(new GameModule());
        GameWindowController controller = injector.getInstance(GameWindowController.class);
        controller.run();
    }
}
