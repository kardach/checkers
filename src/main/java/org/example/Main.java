package org.example;

import org.example.model.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

abstract class ComponentResizedListener extends ComponentAdapter {
    public abstract void componentResized(ComponentEvent e);
}

abstract class ComponentShownListener extends ComponentAdapter {
    public abstract void componentShown(ComponentEvent e);
}

abstract class MouseReleasedListener extends MouseAdapter {
    public abstract void mouseReleased(MouseEvent e);
}

class SquarePanel extends JPanel {
    private Square square;

    public SquarePanel(boolean color) {
        this.square = square;
        setBackground(color ? new java.awt.Color(139, 69, 19) : new java.awt.Color(255, 228, 196));
    }
}

interface ViewController {
    void nextView();
    void previousView();
}

class GameplayPanel extends JPanel {
    private SquarePanel[][] squarePanels;
    private final JPanel buttonsPanel;
    private final JPanel boardPanel;
    private final JPanel boardContainerPanel;

    public GameplayPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        buttonsPanel = getButtonsPanel();
        boardPanel = getBoardPanel();
        boardContainerPanel = getBoardContainerPanel();

        boardContainerPanel.add(boardPanel);

        add(buttonsPanel, BorderLayout.WEST);
        add(boardContainerPanel, BorderLayout.CENTER);
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));

        JButton confirmButton = new JButton("Confirm button");
        confirmButton.setFont(new Font("Dia log", Font.BOLD, 18));

        JButton quitButton = new JButton("Quit button");
        quitButton.setFont(new Font("Dialog", Font.BOLD, 18));

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(quitButton);
        buttonsPanel.add(Box.createVerticalGlue());

        return buttonsPanel;
    }

    private JPanel getBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(10, 10)) {
            @Override
            public Dimension getPreferredSize() {
                Dimension dimension = super.getPreferredSize();
                Dimension preferred;
                Component parent = getParent();

                if (parent != null && parent.getHeight() > dimension.getHeight() &&
                        parent.getWidth() > dimension.getWidth()) {
                    preferred = parent.getSize();
                } else {
                    preferred = dimension;
                }

                int size = Math.min(preferred.height, preferred.width);

                return new Dimension(size, size);
            }
        };
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

        squarePanels = new SquarePanel[10][10];
        for(int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                SquarePanel squarePanel = new SquarePanel((row + col) % 2 == 0);
                squarePanels[row][col] = squarePanel;
                boardPanel.add(squarePanel);
            }
        }

        return boardPanel;
    }

    private JPanel getBoardContainerPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        containerPanel.addComponentListener(new ComponentResizedListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBoardPanel();
            }
        });
        containerPanel.addComponentListener(new ComponentShownListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                resizeBoardPanel();
            }
        });
        return containerPanel;
    }

    private void resizeBoardPanel() {
        Dimension dimension = boardContainerPanel.getSize();
        int size = Math.min(dimension.height - 20, dimension.width - 20) / 10;
        squarePanels[0][0].setMinimumSize(new Dimension(size, size));
        boardPanel.revalidate();
        boardPanel.repaint();
    }
}

class OptionsPanel extends JPanel {
    private final JPanel comboBoxPanel;
    private final JPanel buttonsPanel;
    private final JPanel buttonsContainerPanel;
    private final JPanel formPanel;
    private final JPanel rulesPanel;

    public OptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        comboBoxPanel = getComboBoxPanel();
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

    private JPanel getComboBoxPanel() {
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

        String[] variants = {"International/Polish", "damii/Ghanaian"};
        JComboBox<String> variantComboBox = new JComboBox<>(variants);
        variantComboBox.setFont(new Font("Dialog", Font.BOLD, 18));

        comboBoxPanel.add(variantComboBox, BorderLayout.NORTH);

        return comboBoxPanel;
    }

    private JPanel getButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Dialog", Font.BOLD, 18));

        JButton cancelButton = new JButton("Cancel");
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

class WelcomePanel extends JPanel {
    public WelcomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start new game");
        startButton.setFont(new Font("Dialog", Font.BOLD, 18));

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Dialog", Font.BOLD, 18));

        add(startButton);
        add(exitButton);
    }
}

public class Main {
    public static void addComponentsToPane(Container pane) {
        pane.add(new GameplayPanel());
    }

    private static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        addComponentsToPane(jFrame.getContentPane());

        jFrame.setVisible(true);
    }

    static void main() {
//        GameVariantSetup gameVariantSetup = new GameVariantSetup();
//        GameWindow gameWindow = new GameWindow();
//
//        WelcomePanel welcomePanel = new WelcomePanel(gameWindow);
//        NewGamePanel newGamePanel = new NewGamePanel(gameVariantSetup);
//        GameplayPanel gameplayPanel = new GameplayPanel(gameVariantSetup);
//
//        gameWindow.add(welcomePanel);
//        gameWindow.add(newGamePanel);
//        gameWindow.add(gameplayPanel);
//        gameWindow.display();

        SwingUtilities.invokeLater(Main::createAndShowGUI);

//        Robot robot = new Robot();
//        Point point = welcomePanel.getStartButton().getLocationOnScreen();
//        point.x += welcomePanel.getStartButton().getWidth() / 2;
//        point.y += welcomePanel.getStartButton().getHeight() / 2;
//        robot.mouseMove(point.x, point.y);
//        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//        gameVariantSetup.select("International/Polish");
//        newGamePanel.getJPanel().addComponentListener(new ComponentListener() {
//            private Robot robot;
//            private Point point;
//
//            public ComponentListener init(Robot robot, Point point) {
//                this.robot = robot;
//                this.point = point;
//                return this;
//            }
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent e) {
//                point = newGamePanel.getPlayButton().getLocationOnScreen();
//                point.x += newGamePanel.getPlayButton().getWidth() / 2;
//                point.y += newGamePanel.getPlayButton().getHeight() / 2;
//                robot.mouseMove(point.x, point.y);
//                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent e) {
//
//            }
//        }.init(robot, point));
//        gameplayPanel.getJPanel().addComponentListener(new ComponentListener() {
//            private Robot robot;
//            private Point point;
//
//            public ComponentListener init(Robot robot, Point point) {
//                this.robot = robot;
//                this.point = point;
//                return this;
//            }
//
//            @Override
//            public void componentResized(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent e) {
//                point = gameplayPanel.getBoardPanel().getJButton(3, 0).getLocationOnScreen();
//                point.x = gameplayPanel.getBoardPanel().getJButton(3, 0).getWidth() / 2;
//                point.y = gameplayPanel.getBoardPanel().getJButton(3, 0).getHeight() / 2;
//                robot.mouseMove(point.x, point.y);
//                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent e) {
//
//            }
//        }.init(robot, point));
    }
}
