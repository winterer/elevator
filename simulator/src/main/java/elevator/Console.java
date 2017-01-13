//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

class Console implements ActionListener, ItemListener, WindowListener {
    JDesktopPane desktop;
    JButton playButton;
    JButton pauseButton;
    JButton openButton;
    String controllerText = "";
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private String version = "1.0";
    private String menuNewEditor = "New Scenario";
    private String menuOpenEditor = "Open Scenario";
    private String menuSaveEditor = "Save Scenario";
    private String menuSaveAsEditor = "Save As";
    private String menuSimRun = "Run Simulation";
    private String menuCloseText = "Close";
    private String menuCloseAllText = "Close All";
    private String menuExitText = "Exit";
    private String menuControllerText = "Controller";
    private JMenuItem menuNew;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;
    private JMenuItem menuSim;
    private JMenuItem menuClose;
    private JMenuItem menuCloseAll;
    private JMenuItem menuExit;
    private JMenuItem menuController;
    private JRadioButtonMenuItem[] controlButton;
    private ButtonGroup controlGroup;
    private Class<?>[] controlClass = null;
    private int controlCount = 0;
    private String[] controlList = null;
    private String[] controlText = null;

    Console() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException var2) {
            ;
        } catch (ClassNotFoundException var3) {
            ;
        } catch (InstantiationException var4) {
            ;
        } catch (IllegalAccessException var5) {
            ;
        }

        if (!this.getControllers()) {
            JOptionPane.showMessageDialog(this.desktop, "Could not find any controller objects.", "File System Error", 0);
        }

        this.frame = new JFrame("Elevator Challenge v" + this.version);
        this.frame.setDefaultCloseOperation(0);
        this.frame.setPreferredSize(new Dimension(800, 600));
        this.frame.setResizable(true);
        this.frame.setLayout(new GridBagLayout());
        this.frame.setIconImage(createImageIcon("elevatorGoingUp.jpg", "").getImage());
        this.frame.setBounds(150, 50, 950, 650);
        this.frame.addWindowListener(this);
        this.menuBar = new JMenuBar();
        this.menu = new JMenu("File");
        this.menu.setMnemonic(70);
        this.menuBar.add(this.menu);
        this.menuNew = new JMenuItem(this.menuNewEditor);
        this.menuNew.setIcon(createImageIcon("newModel.gif", ""));
        this.menuNew.setEnabled(true);
        this.menuNew.addActionListener(this);
        this.menu.add(this.menuNew);
        this.menuOpen = new JMenuItem(this.menuOpenEditor);
        this.menuOpen.setIcon(createImageIcon("fldr_obj.gif", ""));
        this.menuOpen.setAlignmentX(0.0F);
        this.menuOpen.setEnabled(true);
        this.menuOpen.addActionListener(this);
        this.menu.add(this.menuOpen);
        this.menu.addSeparator();
        this.menuSave = new JMenuItem(this.menuSaveEditor);
        this.menuSave.setIcon(createImageIcon("save_edit.gif", ""));
        this.menuSave.setAlignmentX(0.0F);
        this.menuSave.setEnabled(false);
        this.menuSave.addActionListener(this);
        this.menu.add(this.menuSave);
        this.menuSaveAs = new JMenuItem(this.menuSaveAsEditor);
        this.menuSaveAs.setIcon(createImageIcon("saveas_edit.gif", ""));
        this.menuSaveAs.setEnabled(false);
        this.menuSaveAs.addActionListener(this);
        this.menu.add(this.menuSaveAs);
        this.menu.addSeparator();
        this.menuClose = new JMenuItem(this.menuCloseText);
        this.menuClose.setAlignmentX(0.0F);
        this.menuClose.setEnabled(false);
        this.menuClose.addActionListener(this);
        this.menuExit = new JMenuItem(this.menuExitText);
        this.menuExit.setAlignmentX(0.0F);
        this.menuExit.setEnabled(true);
        this.menuExit.addActionListener(this);
        this.menu.add(this.menuClose);
        this.menu.addSeparator();
        this.menu.add(this.menuExit);
        this.menuBar.add(this.menu);
        this.menu = new JMenu("Simulation");
        this.menu.setMnemonic(83);
        this.menuBar.add(this.menu);
        this.menuSim = new JMenuItem(this.menuSimRun);
        this.menuSim.setIcon(createImageIcon("debugt_obj.gif", ""));
        this.menuSim.setEnabled(false);
        this.menuSim.addActionListener(this);
        this.menu.add(this.menuSim);
        this.menu.addSeparator();
        this.controlGroup = new ButtonGroup();
        this.controlButton = new JRadioButtonMenuItem[this.controlCount];

        for (int i = 0; i < this.controlCount; ++i) {
            this.controlButton[i] = new JRadioButtonMenuItem(this.controlList[i]);
            this.controlButton[i].setActionCommand(this.controlList[i]);
            this.controlGroup.add(this.controlButton[i]);
            this.menu.add(this.controlButton[i]);
        }

        if (this.controlCount > 0) {
            this.controlButton[0].setSelected(true);
        }
        this.frame.setJMenuBar(this.menuBar);
        this.desktop = new JDesktopPane();
        this.desktop.setBackground(Color.gray);
        this.frame.setContentPane(this.desktop);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    protected static ImageIcon createImageIcon(String path, String description) {
        URL imgURL = Console.class.getClassLoader().getResource("elevator/images/" + path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn\'t find file: " + path);
            return null;
        }
    }

    private boolean getControllers() {
        List<ClassLoader> classLoaders = new ArrayList<>();

        ConfigurationBuilder builder = new ConfigurationBuilder();
        try {
            Path controllersDir = Paths.get(".", "controllers");
            if (Files.exists(controllersDir)) {
                ClassLoader parent = getClass().getClassLoader();

                Collection<URL> libraryURLs = ControllerUtils.getLibraryURLs(controllersDir);
                addControllerLibs(builder, libraryURLs, parent);

                try (DirectoryStream<Path> dirs = Files.newDirectoryStream(controllersDir, Files::isDirectory)) {
                    for (Path dir : dirs) {
                        Collection<URL> urls = ControllerUtils.getControllerURLs(dir);
                        addControllerLibs(builder, urls, parent);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // TODO
        }

        Reflections reflections = new Reflections(builder
                .setScanners(new SubTypesScanner(false)));
        Set<Class<? extends IController>> controllerClasses = reflections.getSubTypesOf(IController.class);

        this.controlCount = controllerClasses.size();

        this.controlClass = new Class[this.controlCount];
        this.controlList = new String[this.controlCount];
        this.controlText = new String[this.controlCount];

        int i = 0;
        for (Class<? extends IController> controllerClass : controllerClasses) {
            if (Challenge.logging > 2) {
                System.out.println("Registering controller class " + controllerClass.getName());
            }

            this.controlClass[i] = controllerClass;
            this.controlList[i] = controllerClass.getName();
        }

        return !controllerClasses.isEmpty();
    }

    private void addControllerLibs(ConfigurationBuilder builder, Collection<URL> urls, ClassLoader parent) {
        if (!urls.isEmpty()) {
            builder.addUrls(urls);
            ClassLoader cl = new URLClassLoader(urls.toArray(new URL[urls.size()]), parent);
            builder.addClassLoader(cl);
        }
    }

    void frameTitle(String title) {
        this.frame.setTitle("Elevator Challenge v" + this.version + " - " + title);
        this.frame.repaint();
    }

    void displayMessage(String title, String message) {
        JOptionPane.showMessageDialog((Component) null, message, title, 1);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == this.menuNewEditor) {
            new Editor(this, false);
        } else {
            JInternalFrame frame;
            if (e.getActionCommand() == this.menuSaveEditor) {
                frame = this.desktop.getSelectedFrame();
                if (frame instanceof Editor) {
                    ((Editor) frame).saveModel(false);
                } else if (Challenge.logging > 1) {
                    System.out.println("Menu error - action on non-Editor frame!");
                }
            } else if (e.getActionCommand() == this.menuSaveAsEditor) {
                frame = this.desktop.getSelectedFrame();
                if (frame instanceof Editor) {
                    ((Editor) frame).saveModel(true);
                } else if (Challenge.logging > 1) {
                    System.out.println("Menu error - action on non-Editor frame!");
                }
            } else if (e.getActionCommand() == this.menuOpenEditor) {
                new Editor(this, true);
            } else if (e.getActionCommand() == this.menuSimRun) {
                this.newSimRun();
            } else if (e.getActionCommand() == this.menuCloseText) {
                Boolean bail = Boolean.valueOf(true);
                JInternalFrame startFrame = this.desktop.getSelectedFrame();
                if (startFrame instanceof Editor & startFrame != null) {
                    bail = Boolean.valueOf(!((Editor) startFrame).closeModel());
                } else if (startFrame instanceof Simulator & startFrame != null) {
                    bail = Boolean.valueOf(!((Simulator) startFrame).closeSim());
                }

                if (!bail.booleanValue()) {
                    startFrame.dispose();
                }
            } else if (e.getActionCommand() == this.menuExitText) {
                this.closeAll();
            }
        }

    }

    void newSimRun() {
        int i = 0;
        boolean foundControl = false;
        IController controlObject = null;
        JInternalFrame frame = this.desktop.getSelectedFrame();
        if (frame instanceof Editor) {
            while (true) {
                if (!(i < this.controlCount & !foundControl)) {
                    if (!foundControl & Challenge.logging > 1) {
                        System.out.println("Controller not found!");
                    } else if (foundControl & !((Editor) frame).applyChangesSim()) {
                        try {
                            controlObject = (IController) this.controlClass[i].newInstance();
                        } catch (InstantiationException var6) {
                            var6.printStackTrace();
                        } catch (IllegalAccessException var7) {
                            var7.printStackTrace();
                        }

                        new Simulator(this, (Editor) frame, controlObject);
                    }
                    break;
                }

                if (this.controlButton[i].isSelected()) {
                    foundControl = true;
                } else {
                    ++i;
                }
            }
        } else if (Challenge.logging > 1) {
            System.out.println("Menu error - action on non-Editor frame!");
        }

    }

    void setMenuEditorActive(boolean on) {
        this.menuSave.setEnabled(on);
        this.menuSaveAs.setEnabled(on);
        this.menuSim.setEnabled(on);
        if (on) {
            this.menuClose.setEnabled(on);
        } else if (Simulator.simActive == 0) {
            this.menuClose.setEnabled(on);
        }

    }

    void updateFocus() {
        if (this.desktop.getAllFrames().length != 0) {
            try {
                this.desktop.getAllFrames()[0].setSelected(true);
            } catch (PropertyVetoException var2) {
                var2.printStackTrace();
            }
        }

    }

    private void closeAll() {
        boolean bail = false;
        JInternalFrame frame = null;
        JInternalFrame startFrame = null;
        boolean i = false;
        boolean j = false;
        startFrame = this.desktop.getSelectedFrame();
        if (startFrame instanceof Editor & startFrame != null) {
            bail = !((Editor) startFrame).closeModel();
        } else if (startFrame instanceof Simulator & startFrame != null) {
            bail = !((Simulator) startFrame).closeSim();
        }

        int var9 = this.desktop.getAllFrames().length;
        int var10 = 0;

        while (var10 < var9 && !bail) {
            frame = this.desktop.getAllFrames()[var10++];
            if (frame != startFrame) {
                try {
                    frame.setSelected(true);
                } catch (PropertyVetoException var8) {
                    var8.printStackTrace();
                }

                if (frame instanceof Editor) {
                    bail = !((Editor) frame).closeModel();
                }

                if (frame instanceof Simulator) {
                    bail = !((Simulator) frame).closeSim();
                }
            }
        }

        if (!bail) {
            for (var9 = this.desktop.getAllFrames().length; var9 > 0; --var9) {
                this.desktop.getAllFrames()[var9 - 1].dispose();
            }

            System.exit(0);
        } else if (bail) {
            for (var9 = this.desktop.getAllFrames().length; var9 > 0; --var9) {
                try {
                    this.desktop.getAllFrames()[var9 - 1].setClosed(false);
                } catch (PropertyVetoException var7) {
                    var7.printStackTrace();
                }
            }
        }

    }

    public void itemStateChanged(ItemEvent e) {
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        this.closeAll();
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
    }
}
