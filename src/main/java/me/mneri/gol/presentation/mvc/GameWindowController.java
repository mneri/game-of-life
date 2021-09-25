package me.mneri.gol.presentation.mvc;

import com.google.inject.Inject;
import com.google.inject.Provider;
import me.mneri.gol.business.service.GameService;
import me.mneri.gol.presentation.component.GamePanel;
import me.mneri.gol.presentation.util.FPS;

import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindowController {
    private final GameWindowModel model;

    private final GameService service;

    private final GameWindowView view;

    @Inject
    public GameWindowController(
            Provider<GameWindowView> viewProvider,
            Provider<GameWindowModel> modelProvider,
            Provider<GameService> serviceProvider) {
        this.view = viewProvider.get();
        this.model = modelProvider.get();
        this.service = serviceProvider.get();
    }

    @Inject
    private void postConstruct(
            @Named("me.mneri.gol.background-color") String defaultBackgroundColor,
            @Named("me.mneri.gol.cell-size") int defaultCellSizePx,
            @Named("me.mneri.gol.foreground-color") String defaultForegroundColor) {
        GamePanel gamePanel = view.getGamePanel();
        gamePanel.setBackgroundColor(Color.decode(defaultBackgroundColor));
        gamePanel.setForegroundColor(Color.decode(defaultForegroundColor));
        gamePanel.setCellSizePx(defaultCellSizePx);
        gamePanel.setWorld(model.getWorld());

        view.getStopButton().setEnabled(false);

        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setLocationRelativeTo(null);

        updatePeriodMillis();
        addEventListeners();
    }

    private void addEventListeners() {
        final JButton startButton = view.getStartButton();
        final JButton stopButton = view.getStopButton();
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                play();
            }
        });
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pause();
            }
        });

        final JComboBox<FPS> fpsComboBox = view.getFpsComboBox();
        fpsComboBox.addActionListener(ignored -> {
            updatePeriodMillis();
        });

        final GamePanel gamePanel = view.getGamePanel();
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model.isRunning()) {
                    return;
                }

                int x = gamePanel.translatePanelXCoordToWorldXCoord(e.getX());
                int y = gamePanel.translatePanelYCoordToWorldYCoord(e.getY());

                boolean turnAlive = SwingUtilities.isLeftMouseButton(e);

                model.getWorld().set(x, y, turnAlive);
                gamePanel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isMiddleMouseButton(e)) {
                    return;
                }

                gamePanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                model.setLastDragX(e.getX());
                model.setLastDragY(e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gamePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        gamePanel.addMouseMotionListener(new MouseAdapter() {
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
                } else {
                    if (model.isRunning()) {
                        return;
                    }

                    boolean turnAlive = SwingUtilities.isLeftMouseButton(e);
                    int x = gamePanel.translatePanelXCoordToWorldXCoord(e.getX());
                    int y = gamePanel.translatePanelYCoordToWorldYCoord(e.getY());

                    model.getWorld().set(x, y, turnAlive);
                }

                gamePanel.repaint();
            }
        });
    }

    private void pause() {
        model.setRunning(false);
    }

    private void play() {
        model.setRunning(true);

        (new Thread(() -> {
            try {
                while (model.isRunning()) {
                    service.evolve(model.getWorld());

                    view.getGamePanel().repaint();
                    Toolkit.getDefaultToolkit().sync();

                    Thread.sleep(model.getPeriodMillis());
                }
            } catch (InterruptedException ignored) {
            }
        })).start();
    }

    public void run() {
        view.setVisible(true);
    }

    private void updatePeriodMillis() {
        FPS fps = (FPS) view.getFpsComboBox().getSelectedItem();

        if (fps != null) {
            model.setPeriodMillis(fps.getPeriod());
        }
    }
}
