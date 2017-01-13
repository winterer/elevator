//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import javax.swing.*;
import javax.swing.SpringLayout.Constraints;
import java.awt.*;

class SpringUtilities {
    SpringUtilities() {
    }

    public static void printSizes(Component c) {
        System.out.println("minimumSize = " + c.getMinimumSize());
        System.out.println("preferredSize = " + c.getPreferredSize());
        System.out.println("maximumSize = " + c.getMaximumSize());
    }

    public static void makeGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException var19) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getWidth();

        int lastCons;
        Constraints lastRowCons;
        for(lastCons = 1; lastCons < max; ++lastCons) {
            lastRowCons = layout.getConstraints(parent.getComponent(lastCons));
            maxWidthSpring = Spring.max(maxWidthSpring, lastRowCons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, lastRowCons.getHeight());
        }

        for(lastCons = 0; lastCons < max; ++lastCons) {
            lastRowCons = layout.getConstraints(parent.getComponent(lastCons));
            lastRowCons.setWidth(maxWidthSpring);
            lastRowCons.setHeight(maxHeightSpring);
        }

        Constraints var20 = null;
        lastRowCons = null;

        for(int pCons = 0; pCons < max; ++pCons) {
            Constraints cons = layout.getConstraints(parent.getComponent(pCons));
            if(pCons % cols == 0) {
                lastRowCons = var20;
                cons.setX(initialXSpring);
            } else {
                cons.setX(Spring.sum(var20.getConstraint("East"), xPadSpring));
            }

            if(pCons / cols == 0) {
                cons.setY(initialYSpring);
            } else {
                cons.setY(Spring.sum(lastRowCons.getConstraint("South"), yPadSpring));
            }

            var20 = cons;
        }

        Constraints var21 = layout.getConstraints(parent);
        var21.setConstraint("South", Spring.sum(Spring.constant(yPad), var20.getConstraint("South")));
        var21.setConstraint("East", Spring.sum(Spring.constant(xPad), var20.getConstraint("East")));
    }

    private static Constraints getConstraintsForCell(int row, int col, Container parent, int cols) {
        SpringLayout layout = (SpringLayout)parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    public static void makeCompactGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException var14) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        Spring x = Spring.constant(initialX);

        for(int y = 0; y < cols; ++y) {
            Spring pCons = Spring.constant(0);

            int height;
            for(height = 0; height < rows; ++height) {
                pCons = Spring.max(pCons, getConstraintsForCell(height, y, parent, cols).getWidth());
            }

            for(height = 0; height < rows; ++height) {
                Constraints c = getConstraintsForCell(height, y, parent, cols);
                c.setX(x);
                c.setWidth(pCons);
            }

            x = Spring.sum(x, Spring.sum(pCons, Spring.constant(xPad)));
        }

        Spring var15 = Spring.constant(initialY);

        for(int var16 = 0; var16 < rows; ++var16) {
            Spring var18 = Spring.constant(0);

            int var19;
            for(var19 = 0; var19 < cols; ++var19) {
                var18 = Spring.max(var18, getConstraintsForCell(var16, var19, parent, cols).getHeight());
            }

            for(var19 = 0; var19 < cols; ++var19) {
                Constraints constraints = getConstraintsForCell(var16, var19, parent, cols);
                constraints.setY(var15);
                constraints.setHeight(var18);
            }

            var15 = Spring.sum(var15, Spring.sum(var18, Spring.constant(yPad)));
        }

        Constraints var17 = layout.getConstraints(parent);
        var17.setConstraint("South", var15);
        var17.setConstraint("East", x);
    }
}
