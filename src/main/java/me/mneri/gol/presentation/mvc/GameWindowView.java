package me.mneri.gol.presentation.mvc;

import com.google.inject.Inject;
import lombok.Getter;
import me.mneri.gol.data.config.Configuration;
import me.mneri.gol.presentation.component.GamePanel;
import me.mneri.gol.presentation.util.FPS;

import javax.swing.*;
import java.awt.*;

public class GameWindowView extends JFrame {
    private final Configuration config;

    @Getter
    private JComboBox<FPS> fpsComboBox;

    @Getter
    private GamePanel gamePanel;

    @Getter
    private JButton startButton;

    @Getter
    private JButton stopButton;

    @Inject
    public GameWindowView(Configuration config) {
        this.config = config;
        buildLayout();
    }

    private void buildLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        fpsComboBox = new JComboBox<>(FPS.values());
        fpsComboBox.setSelectedIndex(FPS.SLOW.ordinal());
        topPanel.add(fpsComboBox);

        startButton = new JButton("Start");
        topPanel.add(startButton);

        stopButton = new JButton("Stop");
        topPanel.add(stopButton);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(config.getDefaultPanelWidth(), config.getDefaultPanelHeight()));
        add(gamePanel, BorderLayout.CENTER);

        pack();
    }
}
