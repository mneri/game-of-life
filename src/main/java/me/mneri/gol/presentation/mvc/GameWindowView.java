package me.mneri.gol.presentation.mvc;

import lombok.Getter;
import me.mneri.gol.presentation.component.GamePanel;
import me.mneri.gol.presentation.util.FPS;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * View for the game window in a Model-View-Controller architecture.
 * <p>
 * The view is responsible for constructing and display the game window.
 *
 * @author Massimo Neri
 * @see GameWindowModel
 * @see GameWindowController
 */
public class GameWindowView extends JFrame {
    /**
     * The FPS combo box.
     */
    @Getter
    private JComboBox<FPS> fpsComboBox;

    /**
     * The game panel, where the cells are displayed.
     */
    @Getter
    private GamePanel gamePanel;

    /**
     * The start button.
     */
    @Getter
    private JButton startButton;

    /**
     * The stop button.
     */
    @Getter
    private JButton stopButton;

    /**
     * Initialise the view. Construct the components and the layout.
     *
     * @param defaultPanelHeight The default height of the game panel.
     * @param defaultPanelWidth  The default width of the game panel.
     */
    @Inject
    @SuppressWarnings("unused") // Invoked after construction by the IoC framework.
    private void postConstruct(
            @Named("me.mneri.gol.panel-height") final int defaultPanelHeight,
            @Named("me.mneri.gol.panel-width") final int defaultPanelWidth) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        fpsComboBox = new JComboBox<>(FPS.values());
        topPanel.add(fpsComboBox);

        startButton = new JButton("Start");
        topPanel.add(startButton);

        stopButton = new JButton("Stop");
        topPanel.add(stopButton);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(defaultPanelWidth, defaultPanelHeight));
        add(gamePanel, BorderLayout.CENTER);

        pack();
    }
}
