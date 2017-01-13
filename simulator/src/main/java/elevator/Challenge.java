//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import javax.swing.*;

class Challenge {
    static int logging = 0;

    Challenge() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Console();
            }
        });
    }
}
