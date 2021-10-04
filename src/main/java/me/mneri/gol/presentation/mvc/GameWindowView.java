/*
 * Copyright 2020 Massimo Neri <hello@mneri.me>
 *
 * This file is part of mneri/gol.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private static final long serialVersionUID = -4995897066996858690L;

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
