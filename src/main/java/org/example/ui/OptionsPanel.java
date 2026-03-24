package org.example.ui;

import org.example.options.GameVariantSetup;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    private final JPanel comboBoxPanel;
    private final JPanel buttonsPanel;
    private final JPanel buttonsContainerPanel;
    private final JPanel formPanel;
    private final JPanel rulesPanel;
    private JComboBox<String> variantComboBox;
    private JButton playButton;
    private JButton cancelButton;
    private GameVariantSetup gameVariantSetup;

    public OptionsPanel(GameVariantSetup gameVariantSetup) {
        this.gameVariantSetup = gameVariantSetup;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        comboBoxPanel = getComboBoxPanel(gameVariantSetup);
        buttonsPanel = getButtonsPanel();
        buttonsContainerPanel = getButtonsContainerPanel();
        formPanel = getFormPanel();
        rulesPanel = getRulesPanel();

        buttonsContainerPanel.add(buttonsPanel);
        buttonsContainerPanel.add(Box.createHorizontalGlue());

        formPanel.add(comboBoxPanel);
        formPanel.add(buttonsContainerPanel);

        add(formPanel);
        add(rulesPanel);
    }

    public JComboBox<String> getVariantComboBox() {
        return variantComboBox;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    private JPanel getComboBoxPanel(GameVariantSetup gameVariantSetup) {
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

        variantComboBox = new JComboBox<>(gameVariantSetup.getVariantNames());
        variantComboBox.setFont(new Font("Dialog", Font.BOLD, 18));

        comboBoxPanel.add(variantComboBox, BorderLayout.NORTH);

        return comboBoxPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        playButton = new JButton("Play");
        playButton.setFont(new Font("Dialog", Font.BOLD, 18));

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Dialog", Font.BOLD, 18));

        buttonsPanel.add(Box.createVerticalGlue());
        buttonsPanel.add(playButton);
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }

    private JPanel getButtonsContainerPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        return containerPanel;
    }

    private JPanel getFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));

        return formPanel;
    }

    private JPanel getRulesPanel() {
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new BoxLayout(rulesPanel, BoxLayout.Y_AXIS));
        rulesPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        JLabel rules = new JLabel("<html><i>Lorem ipsum</i></html>");
        rules.setFont(new Font("Dialog", Font.BOLD, 18));

        rulesPanel.add(rules);
        rulesPanel.add(Box.createVerticalGlue());

        return rulesPanel;
    }
}