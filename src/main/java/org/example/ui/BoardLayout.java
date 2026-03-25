package org.example.ui;

import java.awt.*;

public class BoardLayout implements LayoutManager {
    private final int rows;
    private final int cols;

    public BoardLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int componentCount = parent.getComponentCount();
            int rowCount = rows;
            int colCount = cols;

            if (rowCount > 0) {
                colCount = (componentCount + rowCount - 1) / rowCount;
            } else {
                rowCount = (componentCount + colCount - 1) / colCount;
            }
            int width = 0;
            int height = 0;
            for (int i = 0; i < componentCount; i++) {
                Component component = parent.getComponent(i);
                Dimension dimension = component.getPreferredSize();
                width = Math.max(width, dimension.width);
                height = Math.max(height, dimension.width);
            }
            int size = Math.min(insets.left + insets.right + colCount * width, insets.top + insets.bottom + rowCount * height);
            return new Dimension(size, size);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int componentCount = parent.getComponentCount();
            int rowCount = rows;
            int colCount = cols;

            if (rowCount > 0) {
                colCount = (componentCount + rowCount - 1) / rowCount;
            } else {
                rowCount = (componentCount + colCount - 1) / colCount;
            }
            int width = 0;
            int height = 0;
            for (int i = 0; i < componentCount; i++) {
                Component component = parent.getComponent(i);
                Dimension dimension = component.getMinimumSize();
                width = Math.max(width, dimension.width);
                height = Math.max(height, dimension.width);
            }
            int size = Math.min(insets.left + insets.right + colCount * width, insets.top + insets.bottom + rowCount * height);
            return new Dimension(size, size);
        }
    }

    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int componentCount = parent.getComponentCount();
            int rowCount = rows;
            int colCount = cols;

            if (componentCount == 0) {
                return;
            }
            if (rowCount > 0) {
                colCount = (componentCount + rowCount - 1) / rowCount;
            } else {
                rowCount = (componentCount + colCount - 1) / colCount;
            }

            int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
            int widthOnComponent = widthWOInsets / colCount;

            int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
            int heightOnComponent = heightWOInsets / rowCount;

            int sizeOnComponent = Math.min(widthOnComponent, heightOnComponent);
            int extraWidthAvailable = (widthWOInsets - (sizeOnComponent * colCount)) / 2;
            int extraHeightAvailable = (heightWOInsets - (sizeOnComponent * rowCount)) / 2;

            for (int c = 0, x = insets.left + extraWidthAvailable; c < colCount; c++, x += sizeOnComponent) {
                for (int r = 0, y = insets.top + extraHeightAvailable; r < rowCount; r++, y += sizeOnComponent) {
                    int i = r * colCount + c;
                    if (i < componentCount) {
                        parent.getComponent(i).setBounds(x, y, sizeOnComponent, sizeOnComponent);
                    }
                }
            }
        }
    }
}
