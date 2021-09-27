package me.mneri.gol.presentation.mvc;

import com.google.inject.Inject;
import com.google.inject.Provider;
import me.mneri.gol.presentation.component.GamePanel;
import me.mneri.gol.presentation.util.FPS;

import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller for the game window in a Model-View-Controller architecture.
 * <p>
 * The controller is responsible for orchestrating the behaviour of the window and its components.
 *
 * @author Massimo Neri
 * @see GameWindowModel
 * @see GameWindowView
 */
public class GameWindowController {
    /**
     * The window model, holding the data for this controller.
     */
    private final GameWindowModel model;

    /**
     * The game window view, containing the window frame and all the components.
     */
    private final GameWindowView view;

    /**
     * Create a new {@link GameWindowController}.
     * <p>
     * This constructor is {@code protected} as it is meant to be called only by the IoC framework.
     *
     * @param viewProvider    A provider of {@link GameWindowView}.
     * @param modelProvider   A provider of {@link GameWindowModel}.
     */
    @Inject
    protected GameWindowController(
            Provider<GameWindowView> viewProvider,
            Provider<GameWindowModel> modelProvider) {
        this.view = viewProvider.get();
        this.model = modelProvider.get();
    }

    /**
     * Initialise the view and the model. Call {@link GameWindowController#addEventListeners()} to add the listeners to
     * the view's components.
     *
     * @param defaultBackgroundColor The game panel's default background color.
     * @param defaultCellSizePx      The game panel's default cell size.
     * @param defaultForegroundColor The game panel's default foreground color.
     */
    @Inject
    @SuppressWarnings("unused") // Invoked after construction by the IoC framework.
    protected void postConstruct(
            @Named("me.mneri.gol.background-color") Color defaultBackgroundColor,
            @Named("me.mneri.gol.cell-size") int defaultCellSizePx,
            @Named("me.mneri.gol.foreground-color") Color defaultForegroundColor) {
        // Initialise the state of the game panel.
        GamePanel gamePanel = view.getGamePanel();
        gamePanel.setBackgroundColor(defaultBackgroundColor);
        gamePanel.setForegroundColor(defaultForegroundColor);
        gamePanel.setCellSizePx(defaultCellSizePx);
        gamePanel.setWorld(model.getWorld());

        // Initialise the state of the stop button.
        view.getStopButton().setEnabled(false);

        // Initialise the state of the game window.
        view.setLocationRelativeTo(null);

        // Initialise the state of the FPS combo box
        view.getFpsComboBox().setSelectedIndex(FPS.SLOW.ordinal());
        updatePeriodMillis();

        addEventListeners();
    }

    /**
     * Add the event listeners to the components of the game window.
     */
    protected void addEventListeners() {
        // Initialise the listener to the window model to get game updates. Every update should repaint the game panel.
        model.setWorldUpdateListener(() -> {
            view.getGamePanel().repaint();
            Toolkit.getDefaultToolkit().sync();
        });

        // Set the close event listener.
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialise the listeners for the start and the stop buttons:
        //     * when the user clicks the start button the game starts and the stop button becomes enabled;
        //     * when the user clicks the stop button the game stops and the start button becomes enabled.
        final JButton startButton = view.getStartButton();
        final JButton stopButton = view.getStopButton();
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                model.play();
            }
        });
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                model.pause();
            }
        });

        // Initialise the listener for the FPS combo box. When the user changes the value of the combo box the game
        // should speed up or slow down accordingly.
        final JComboBox<FPS> fpsComboBox = view.getFpsComboBox();
        fpsComboBox.addActionListener(ignored -> {
            updatePeriodMillis();
        });

        // Initialise the listeners for the game panel:
        //     * the user can create cells using the left mouse button;
        //     * the user can destroy cells using with the right button;
        //     * cells can be created by either clicking or dragging;
        //     * using middle button the user can drag the panel and move around the world.
        //
        // There is the necessary requirement that the game must be stopped before the user can create or destroy cells.
        // If not, the cell would simply die after just milliseconds and it would be impossible for the user to create
        // shapes and objects.
        //
        // The game panel can display single cells using squares bigger than one pixel. The coordinates x and y of the
        // click must so be translated from the game panel's space to the world's space. The size of a cell is specified
        // in the application.properties file.
        final GamePanel gamePanel = view.getGamePanel();
        gamePanel.addMouseListener(new MouseAdapter() {
            /**
             * Handle the cell creation and cell destruction.
             * @param e The mouse event.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e))) {
                    return;
                }

                if (model.isRunning()) {
                    return;
                }

                int x = gamePanel.translatePanelXCoordToWorldXCoord(e.getX());
                int y = gamePanel.translatePanelYCoordToWorldYCoord(e.getY());

                boolean turnAlive = SwingUtilities.isLeftMouseButton(e);

                model.getWorld().set(x, y, turnAlive);
                gamePanel.repaint();
            }

            /**
             * Handle the start of a drag using the middle mouse button.
             * @param e The mouse event.
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isMiddleMouseButton(e)) {
                    return;
                }

                gamePanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                model.setLastDragX(e.getX());
                model.setLastDragY(e.getY());
            }

            /**
             * Handle the end of a drag using the middle mouse button.
             * @param e The mouse event.
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!SwingUtilities.isMiddleMouseButton(e)) {
                    return;
                }

                gamePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        gamePanel.addMouseMotionListener(new MouseAdapter() {
            /**
             * Handle the dragging motion happening with the left button, middle button or right button.
             * @param e The mouse event.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    int x = e.getX();
                    int y = e.getY();
                    int dx = x - model.getLastDragX();
                    int dy = y - model.getLastDragY();

                    model.setLastDragX(x);
                    model.setLastDragY(y);
                    gamePanel.pan(dx, dy);
                    gamePanel.repaint();
                } else if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
                    if (model.isRunning()) {
                        return;
                    }

                    boolean turnAlive = SwingUtilities.isLeftMouseButton(e);
                    int x = gamePanel.translatePanelXCoordToWorldXCoord(e.getX());
                    int y = gamePanel.translatePanelYCoordToWorldYCoord(e.getY());

                    model.getWorld().set(x, y, turnAlive);
                    gamePanel.repaint();
                }
            }
        });
    }

    /**
     * Launch the game window.
     */
    public void run() {
        view.setVisible(true);
    }

    /**
     * Update the game speed according to the value of the FPS combo box.
     */
    protected void updatePeriodMillis() {
        FPS fps = (FPS) view.getFpsComboBox().getSelectedItem();

        if (fps != null) {
            model.setPeriodMillis(fps.getPeriod());
        }
    }
}
