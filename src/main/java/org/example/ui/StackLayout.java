package org.example.ui;

import java.awt.*;

public class StackLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
//        Insets insets = parent.getInsets();
//        int componentCount = parent.getComponentCount();
//
//        int width = 0;
//        int height = 0;
//        for (int i = 0 ; i < componentCount ; i++) {
//            Component component = parent.getComponent(i);
//            Dimension dimension = component.getPreferredSize();
//            width = Math.max(width, dimension.width);
//            height = Math.max(height, dimension.width);
//        }
//        Dimension dimension = new Dimension();
//        dimension.width = insets.left + insets.right + width;
//        dimension.height = insets.top + insets.bottom + height;
//        return dimension;
        return new Dimension(100, 100);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
//        Insets insets = parent.getInsets();
//        int componentCount = parent.getComponentCount();
//
//        int width = 0;
//        int height = 0;
//        for (int i = 0 ; i < componentCount ; i++) {
//            Component component = parent.getComponent(i);
//            Dimension dimension = component.getMinimumSize();
//            width = Math.max(width, dimension.width);
//            height = Math.max(height, dimension.width);
//        }
//        Dimension dimension = new Dimension();
//        dimension.width = insets.left + insets.right + width;
//        dimension.height = insets.top + insets.bottom + height;
//        return dimension;
        return new Dimension(100, 100);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
        int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);

        for(Component component: parent.getComponents()) {
            component.setBounds(insets.left, insets.top, widthWOInsets, heightWOInsets);
        }
    }
}
