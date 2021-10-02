package me.mneri.gol;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mneri.gol.presentation.mvc.GameWindowController;

import javax.swing.UIManager;

/**
 * Application entry point.
 *
 * @author Massimo Neri
 */
public final class GameApplication {
    private GameApplication() {
    }

    /**
     * Application entry point.
     *
     * @param args List of command line arguments.
     */
    public static void main(final String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        Injector injector = Guice.createInjector(new GameModule());
        GameWindowController controller = injector.getInstance(GameWindowController.class);
        controller.run();
    }
}
