package org.example;

import org.example.model.Game;
import org.example.model.Square;
import org.example.options.GameVariantSetup;
import org.example.ui.GameplayPanel;
import org.example.ui.OptionsPanel;
import org.example.ui.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class MouseReleasedListener extends MouseAdapter {
    public abstract void mouseReleased(MouseEvent e);
}

class SquarePanel extends JPanel {
    private Square square;
    private boolean selected;
    private boolean color;

    public SquarePanel(boolean color) {
        this.square = square;
        this.color = color;
        if(selected) {
            setBackground(Color.GREEN);
        } else {
            setBackground(color ? new java.awt.Color(139, 69, 19) : new java.awt.Color(255, 228, 196));
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        revalidate();
        repaint();
    }
}
//
//interface ViewController {
//    void nextView();
//    void previousView();
//}
//
//class DefaultViewController implements ViewController {
//    private Container parent;
//    private Component current;
//    private CardLayout cardLayout;
//    private LinkedHashMap<Component, String> views;
//
//
//    public DefaultViewController(Container parent, CardLayout cardLayout) {
//        this.parent = parent;
//        this.cardLayout = cardLayout;
//        views = new LinkedHashMap<>();
//    }
//
//    public Container getParent() {
//        return parent;
//    }
//
//    public CardLayout getCardLayout() {
//        return cardLayout;
//    }
//
//    public void addView(Component component, String name) {
//        if(views.isEmpty()) {
//            current = component;
//        }
//        views.put(component, name);
//        getParent().add(component);
//    }
//
//    public void removeView(Component component, String name) {
//        views.remove(component, name);
//        if(views.isEmpty()) {
//            current = null;
//        }
//        getParent().remove(component);
//    }
//
//    @Override
//    public void nextView() {
//        if(!views.isEmpty()) {
//            views
//            getCardLayout().show(getParent(), views.get(current).ne);
//        }
//    }
//
//    @Override
//    public void previousView() {
//
//    }
//}

public class Main {
    private final JFrame jFrame;
    private final GameVariantSetup gameVariantSetup;

    public void addComponentsToPane(Container pane) {
        CardLayout cardLayout = new CardLayout();
        pane.setLayout(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel();
        OptionsPanel optionsPanel = new OptionsPanel(gameVariantSetup);
        GameplayPanel gameplayPanel = new GameplayPanel();

//        pane.add("WELCOME", welcomePanel);
//        pane.add("OPTIONS", optionsPanel);
        pane.add("GAMEPLAY", gameplayPanel);
        gameplayPanel.setGame(new Game(gameVariantSetup.getSelectedVariant()));

//        welcomePanel.getStartButton().addActionListener(_ -> cardLayout.show(pane, "OPTIONS"));
//        welcomePanel.getExitButton().addActionListener(_ -> jFrame.dispose());

//        JComboBox<String> variantComboBox = optionsPanel.getVariantComboBox();
//        variantComboBox.addActionListener(_ -> {
//            String name = variantComboBox.getItemAt(variantComboBox.getSelectedIndex());
//            gameVariantSetup.select(name);
//        });
//        optionsPanel.getPlayButton().addActionListener(_ -> {
//            gameplayPanel.setGame(new Game(gameVariantSetup.getSelectedVariant()));
//            cardLayout.show(pane, "GAMEPLAY");
//        });
//        optionsPanel.getCancelButton().addActionListener(_ -> cardLayout.show(pane, "WELCOME"));
//
//        gameplayPanel.getQuitButton().addActionListener(_ -> {
//            gameplayPanel.removeGame();
//            cardLayout.show(pane, "OPTIONS");
//        });
    }

    public Main() {
        jFrame = new JFrame("Checkers");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(640, 480);

        gameVariantSetup = new GameVariantSetup();

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

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
            }
        });

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
