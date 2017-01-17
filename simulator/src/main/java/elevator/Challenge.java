//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class Challenge {
    private static Logger logger = LoggerFactory.getLogger(Challenge.class);

    static int logging = 0;

    Challenge() {
    }

    public static void main(String[] args) {
        try(InputStream inputStream = new FileInputStream("bin/elevator.properties")) {
            System.getProperties().load(inputStream);
        } catch (FileNotFoundException ex) {
            logger.info("Properties file elevator.properties not found. Using default settings.");
        } catch (IOException ex) {
            logger.error("Error reading elevator.properties file", ex);
        }

        SwingUtilities.invokeLater(Console::new);
    }
}
