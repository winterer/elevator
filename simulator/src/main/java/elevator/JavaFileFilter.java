//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import javax.swing.filechooser.FileFilter;
import java.io.File;

class JavaFileFilter extends FileFilter {
    JavaFileFilter() {
    }

    public boolean accept(File f) {
        if(f.isDirectory()) {
            return true;
        } else {
            String name = f.getName();
            return name.matches(".*\\.xml");
        }
    }

    public String getDescription() {
        return "Elevator model files (*.xml)";
    }
}
