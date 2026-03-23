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

final class GameplayPanel extends JPanel {
    private static SquarePanel[][] squarePanels;
    private JPanel buttonsPanel;
    private JPanel containerPanel;
    private static JPanel boardPanel;

    public GameplayPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.RED, 5));

        buttonsPanel = getButtonsPanel();

        boardPanel = getBoardPanel();

        containerPanel = getContainerPanel();

        containerPanel.add(boardPanel);

        add(buttonsPanel, BorderLayout.WEST);
        add(containerPanel, BorderLayout.CENTER);
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

    private void resizeBoardPanel() {
        Dimension dimension = containerPanel.getSize();
        int size = Math.min(dimension.height - 20, dimension.width - 20) / 10;
        squarePanels[0][0].setMinimumSize(new Dimension(size, size));
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private JPanel getContainerPanel() {
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
}

public class Main {
    public static void addComponentsToPane(Container pane) {
        pane.add(new GameplayPanel());


//        pane.add(gameplayPanel);
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
