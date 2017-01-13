//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;
import java.util.ArrayList;

class Simulator extends JInternalFrame implements InternalFrameListener, ActionListener, ChangeListener {
    private static final int NEW = 0;
    private static final int PLAY = 1;
    private static final int PAUSE = 2;
    private static final int STOP = 3;
    private static final int DONE = 4;
    private static final int RESTART = 5;
    private static final int displayPeriod = 50;
    private static final int simPeriod = 100;
    private static final int resultsPeriod = 1000;
    private int frameTime;
    private int status = 0;
    private int speed;
    private Editor editor;
    private Model model;
    private Console console;
    private IController controller;
    private Engine engine;
    static int simCount = 0;
    static int simActive = 0;
    private JButton simPlay;
    private JButton simStop;
    private JButton simSpeed;
    private JSlider simSpeedSlider;
    private JLabel simClock1;
    private JLabel simClock2;
    private JLabel simClock3;
    private JPanel simPanel;
    private JPanel passengerPanel;
    private JPanel statsPanel;
    private JPanel resultsPane;
    private JPanel summaryPane;
    private ArrayList<Passenger> passengers = new ArrayList(10);
    private long clock = 0L;
    private PassengerTableModel passengerTable;
    private NumberFormat nf;
    private JLabel passengerCountLabel;
    private JLabel averageWaitLabel;
    private JLabel maximumWaitLabel;
    private JLabel averageTravelLabel;
    private JLabel maximumTravelLabel;
    private JLabel scenarioLabel;
    private JLabel controllerLabel;
    private JLabel controllerTextLabel;
    private JTextField passengerCountValue;
    private JTextField averageWaitValue;
    private JTextField maximumWaitValue;
    private JTextField averageTravelValue;
    private JTextField maximumTravelValue;
    private JTextField scenarioValue;
    private JTextField controllerValue;
    private JTextField controllerTextValue;
    private ImageIcon playButton;
    private ImageIcon pauseButton;
    private ImageIcon stopButton;
    private ImageIcon resumeButton;
    private Timer simTimer;
    private Timer displayTimer;
    private Timer resultsTimer;
    private int x1Size = 200;
    private int y1Size = 22;
    private int x2Size = 60;
    private int y2Size = 22;

    Simulator(Console console, Editor editor, IController controller) {
        this.console = console;
        this.editor = editor;
        this.model = new Model();
        this.model.cloneModel(editor.getModel());
        this.controller = controller;
        this.engine = new Engine(this.model, this.controller);
        this.setTitle("Simulation #" + ++simCount + ": \'" + this.model.getTitle() + "\'" + " using \'" + controller.getClass().getName() + "\'");
        ++simActive;
        this.setResizable(true);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setMaximizable(true);
        this.addInternalFrameListener(this);
        this.setDefaultCloseOperation(0);
        this.setSize(410, 500);
        this.setLocation(150 + 30 * simCount, 30 * simCount);
        this.setVisible(true);
        this.setFrameIcon(Console.createImageIcon("debugt_obj.gif", ""));
        this.setLayout(new BorderLayout());
        JTabbedPane simulatorPane = new JTabbedPane(1);
        this.speed = 50;
        this.simTimer = new Timer(100 - this.speed, this);
        this.simTimer.setInitialDelay(0);
        this.simTimer.setCoalesce(true);
        this.displayTimer = new Timer(50, this);
        this.displayTimer.setInitialDelay(0);
        this.displayTimer.setCoalesce(true);
        this.resultsTimer = new Timer(1000, this);
        this.resultsTimer.setInitialDelay(0);
        this.resultsTimer.setCoalesce(true);
        Border lowerEtched = BorderFactory.createEtchedBorder(1);
        JToolBar simToolBar = new JToolBar();
        simToolBar.setLayout(new FlowLayout(0));
        this.playButton = Console.createImageIcon("play.gif", "");
        this.pauseButton = Console.createImageIcon("pause.gif", "");
        this.stopButton = Console.createImageIcon("stop.gif", "");
        this.resumeButton = Console.createImageIcon("resume.gif", "");
        this.simPlay = new JButton("Play", this.playButton);
        this.simPlay.setVerticalTextPosition(3);
        this.simPlay.setHorizontalTextPosition(0);
        this.simPlay.addActionListener(this);
        this.simPlay.setEnabled(true);
        this.simPlay.setFocusable(false);
        this.simPlay.setPreferredSize(new Dimension(50, 40));
        this.simPlay.setIconTextGap(2);
        this.simPlay.setBorder(lowerEtched);
        this.simStop = new JButton("Stop", this.stopButton);
        this.simStop.setVerticalTextPosition(3);
        this.simStop.setHorizontalTextPosition(0);
        this.simStop.addActionListener(this);
        this.simStop.setFocusable(false);
        this.simStop.setEnabled(false);
        this.simStop.setPreferredSize(new Dimension(50, 40));
        this.simStop.setIconTextGap(2);
        this.simStop.setBorder(lowerEtched);
        JLabel simSpeedLabel = new JLabel("Speed");
        this.simSpeedSlider = new JSlider();
        this.simSpeedSlider.addChangeListener(this);
        this.simSpeedSlider.setMinorTickSpacing(10);
        this.simSpeedSlider.setMaximum(100);
        this.simSpeedSlider.setMinimum(0);
        this.simSpeedSlider.setValue(this.speed);
        this.simSpeedSlider.setPaintTicks(true);
        this.simSpeedSlider.setPaintLabels(false);
        this.simSpeedSlider.setFocusable(false);
        this.simSpeedSlider.setSnapToTicks(true);
        this.simSpeedSlider.setPreferredSize(new Dimension(125, 20));
        this.simSpeed = new JButton();
        this.simSpeed.setLayout(new FlowLayout(0));
        this.simSpeed.setFocusable(false);
        this.simSpeed.add(simSpeedLabel);
        this.simSpeed.add(this.simSpeedSlider);
        this.simSpeed.setPreferredSize(new Dimension(190, 40));
        this.simSpeed.setBorder(lowerEtched);
        simToolBar.add(this.simPlay);
        simToolBar.add(this.simStop);
        simToolBar.add(this.simSpeed);
        simToolBar.setFloatable(false);
        simToolBar.setBorderPainted(true);
        this.add(simToolBar, "First");
        JToolBar simToolBar2 = new JToolBar();
        this.simClock1 = new JLabel("");
        this.simClock1.setBorder(this.simPlay.getBorder());
        this.simClock1.setFocusable(false);
        this.simClock2 = new JLabel("");
        this.simClock2.setBorder(this.simPlay.getBorder());
        this.simClock2.setFocusable(false);
        this.simClock3 = new JLabel("");
        this.simClock3.setBorder(this.simPlay.getBorder());
        this.simClock3.setFocusable(false);
        simToolBar2.add(this.simClock1);
        simToolBar2.add(this.simClock2);
        simToolBar2.add(this.simClock3);
        simToolBar2.setFloatable(false);
        simToolBar2.setBorderPainted(true);
        this.add(simToolBar2, "Last");
        this.simPanel = new SimPanel();
        this.simPanel.setAutoscrolls(true);
        this.simPanel.setLayout((LayoutManager)null);
        this.simPanel.setDoubleBuffered(true);
        JScrollPane simPane = new JScrollPane(this.simPanel);
        simulatorPane.addTab("Elevator", simPane);
        this.passengerTable = new PassengerTableModel();
        JTable passengerJTable = new JTable(this.passengerTable);
        JScrollPane passengerPane = new JScrollPane(passengerJTable);
        simulatorPane.addTab("Passenger", passengerPane);
        this.statsPanel = new JPanel(false);
        this.statsPanel.setLayout(new BoxLayout(this.statsPanel, 1));
        this.statsPanel.setAutoscrolls(true);
        this.statsPanel.setAlignmentY(0.0F);
        this.setupStatsPanel();
        JScrollPane statsPane = new JScrollPane(this.statsPanel);
        simulatorPane.addTab("Results", statsPane);
        this.add(simulatorPane);
        this.engine.setupSim();
        this.updateClockLabels();
        console.desktop.add(this);

        try {
            this.setSelected(true);
        } catch (PropertyVetoException var14) {
            ;
        }

        this.simTimer.start();
        this.displayTimer.start();
        this.resultsTimer.start();
    }

    private void setupStatsPanel() {
        this.scenarioLabel = new JLabel("Scenario title:");
        this.scenarioValue = new JTextField(this.model.getTitle(), 20);
        this.scenarioValue.setEditable(false);
        this.scenarioValue.setHorizontalAlignment(10);
        this.setSize(this.scenarioValue, this.x1Size, this.y1Size);
        this.controllerLabel = new JLabel("Run with Controller:");
        this.controllerValue = new JTextField(this.controller.getClass().getName(), 20);
        this.controllerValue.setEditable(false);
        this.controllerValue.setHorizontalAlignment(10);
        this.setSize(this.controllerValue, this.x1Size, this.y1Size);
        this.controllerTextLabel = new JLabel("Controller Description:");
        this.controllerTextValue = new JTextField(this.controller.getText(), 20);
        this.controllerTextValue.setEditable(false);
        this.controllerTextValue.setHorizontalAlignment(10);
        this.setSize(this.controllerTextValue, this.x1Size, this.y1Size);
        this.summaryPane = new JPanel(new SpringLayout());
        this.summaryPane.add(this.scenarioLabel);
        this.summaryPane.add(this.scenarioValue);
        this.summaryPane.add(this.controllerLabel);
        this.summaryPane.add(this.controllerValue);
        this.summaryPane.add(this.controllerTextLabel);
        this.summaryPane.add(this.controllerTextValue);
        SpringUtilities.makeCompactGrid(this.summaryPane, 3, 2, 6, 6, 6, 6);
        this.summaryPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Summary"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.summaryPane.setAlignmentX(0.0F);
        this.statsPanel.add(this.summaryPane);
        this.nf = NumberFormat.getNumberInstance();
        this.nf.setMaximumFractionDigits(1);
        this.passengerCountLabel = new JLabel("Passengers processed:");
        this.passengerCountValue = new JTextField(String.valueOf(this.engine.passengersProcessed), 10);
        this.passengerCountValue.setEditable(false);
        this.passengerCountValue.setHorizontalAlignment(11);
        this.setSize(this.passengerCountValue, this.x2Size, this.y2Size);
        this.averageWaitLabel = new JLabel("Average wait time:");
        this.averageWaitValue = new JTextField(String.valueOf(this.nf.format(this.engine.averageWait / 10.0D)), 10);
        this.averageWaitValue.setEditable(false);
        this.averageWaitValue.setHorizontalAlignment(11);
        this.setSize(this.averageWaitValue, this.x2Size, this.y2Size);
        this.maximumWaitLabel = new JLabel("Maximium wait time:");
        this.maximumWaitValue = new JTextField(String.valueOf((double)this.engine.maxWait / 10.0D), 10);
        this.maximumWaitValue.setEditable(false);
        this.maximumWaitValue.setHorizontalAlignment(11);
        this.setSize(this.maximumWaitValue, this.x2Size, this.y2Size);
        this.averageTravelLabel = new JLabel("Average total time:");
        this.averageTravelValue = new JTextField(String.valueOf(this.nf.format(this.engine.averageTravel / 10.0D)), 10);
        this.averageTravelValue.setEditable(false);
        this.averageTravelValue.setHorizontalAlignment(11);
        this.setSize(this.averageTravelValue, this.x2Size, this.y2Size);
        this.maximumTravelLabel = new JLabel("Maximum total time:");
        this.maximumTravelValue = new JTextField(String.valueOf((double)this.engine.maxTravel / 10.0D), 10);
        this.maximumTravelValue.setEditable(false);
        this.maximumTravelValue.setHorizontalAlignment(11);
        this.setSize(this.maximumTravelValue, this.x2Size, this.y2Size);
        this.resultsPane = new JPanel(new SpringLayout());
        this.resultsPane.add(this.passengerCountLabel);
        this.resultsPane.add(this.passengerCountValue);
        this.resultsPane.add(this.averageWaitLabel);
        this.resultsPane.add(this.averageWaitValue);
        this.resultsPane.add(this.maximumWaitLabel);
        this.resultsPane.add(this.maximumWaitValue);
        this.resultsPane.add(this.averageTravelLabel);
        this.resultsPane.add(this.averageTravelValue);
        this.resultsPane.add(this.maximumTravelLabel);
        this.resultsPane.add(this.maximumTravelValue);
        SpringUtilities.makeCompactGrid(this.resultsPane, 5, 2, 6, 6, 6, 6);
        this.resultsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Scenario"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.resultsPane.setAlignmentX(0.0F);
        this.statsPanel.add(this.resultsPane);
    }

    private void tickSim() {
        if(this.status == 1 && this.engine.tickSim()) {
            this.status = 4;
        }

    }

    private void tickDisplay() {
        if(this.status == 1) {
            this.passengerTable.fireTableDataChanged();
            this.updateClockLabels();
        } else if(this.status == 5) {
            this.engine.resetSim();
            this.status = 1;
        } else if(this.status == 4) {
            this.simPlay.setIcon(this.playButton);
            this.simPlay.setText("Play");
            this.simStop.setEnabled(false);
            if(this.model.getPassengerNum() > 0) {
                this.engine.clearPassengers();
            }
        }

        this.simPanel.repaint();
    }

    private void updateClockLabels() {
        this.engine.updateClockText();
        this.simClock1.setText(this.engine.simClocks[0]);
        this.simClock2.setText(this.engine.simClocks[1]);
        this.simClock3.setText(this.engine.simClocks[2]);
    }

    private void updateResults() {
        this.passengerCountValue.setText(String.valueOf(this.engine.passengersProcessed));
        this.averageWaitValue.setText(String.valueOf(this.nf.format(this.engine.averageWait / 10.0D)));
        this.maximumWaitValue.setText(String.valueOf((double)this.engine.maxWait / 10.0D));
        this.averageTravelValue.setText(String.valueOf(this.nf.format(this.engine.averageTravel / 10.0D)));
        this.maximumTravelValue.setText(String.valueOf((double)this.engine.maxTravel / 10.0D));
    }

    boolean closeSim() {
        boolean n = false;
        boolean bail = false;
        if(this.status == 1) {
            int n1 = JOptionPane.showConfirmDialog(this, "The Simulation \'" + this.getTitle() + "\' is currently running. Are you sure you would like to close it?\'", "Close Simulation?", 0);
            if(n1 == 0) {
                bail = false;
            } else if(n1 == 1) {
                bail = true;
            } else {
                bail = true;
            }
        }

        if (bail) {

        }

        return !bail;
    }

    private void setSize(JComponent obj, int x, int y) {
        Dimension size = new Dimension(x, y);
        obj.setPreferredSize(size);
        obj.setMaximumSize(size);
        obj.setMinimumSize(size);
    }

    public void internalFrameActivated(InternalFrameEvent arg0) {
        this.console.setMenuEditorActive(false);
    }

    public void internalFrameClosed(InternalFrameEvent arg0) {
        --simActive;
        this.console.updateFocus();
    }

    public void internalFrameClosing(InternalFrameEvent arg0) {
        boolean bail = !this.closeSim();
        if(!bail) {
            this.dispose();
            this.simTimer.stop();
            this.displayTimer.stop();
            this.resultsTimer.stop();

            if (controller != null) {
                controller.dispose();
            }
        }

    }

    public void internalFrameDeactivated(InternalFrameEvent arg0) {
    }

    public void internalFrameDeiconified(InternalFrameEvent arg0) {
    }

    public void internalFrameIconified(InternalFrameEvent arg0) {
    }

    public void internalFrameOpened(InternalFrameEvent arg0) {
    }

    public void actionPerformed(ActionEvent e) {
        boolean n = false;
        int n1;
        if(e.getSource() == this.simPlay) {
            if(this.status == 0) {
                this.clock = 0L;
                this.simPlay.setIcon(this.pauseButton);
                this.simPlay.setText("Pause");
                this.simStop.setEnabled(true);
                this.status = 1;
            } else if(this.status == 2) {
                this.simPlay.setIcon(this.pauseButton);
                this.simPlay.setText("Pause");
                this.simStop.setEnabled(true);
                this.status = 1;
            } else if(this.status == 1) {
                this.simPlay.setIcon(this.resumeButton);
                this.simPlay.setText("Resume");
                this.simStop.setEnabled(true);
                this.status = 2;
            } else if(this.status == 4) {
                n1 = JOptionPane.showConfirmDialog(this, "Do you want to restart the simulation?", "Restart Simulation?", 0);
                if(n1 == 0) {
                    this.clock = 0L;
                    this.simPlay.setIcon(this.pauseButton);
                    this.simPlay.setText("Pause");
                    this.simStop.setEnabled(true);
                    this.status = 5;
                }
            }
        } else if(e.getSource() == this.simStop) {
            if(this.status == 2 || this.status == 1) {
                n1 = JOptionPane.showConfirmDialog(this, "Do you want to stop the simulation?", "Stop Simulation?", 0);
                if(n1 == 0) {
                    this.simPlay.setIcon(this.playButton);
                    this.simPlay.setText("Play");
                    this.simStop.setEnabled(false);
                    this.status = 4;
                }
            }
        } else if(e.getSource() == this.simTimer) {
            this.tickSim();
        } else if(e.getSource() == this.displayTimer) {
            this.tickDisplay();
        } else if(e.getSource() == this.resultsTimer) {
            this.updateResults();
        }

    }

    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == this.simSpeedSlider) {
            this.speed = this.simSpeedSlider.getValue();
            this.simTimer.setDelay(100 - this.speed);
            if(this.speed == 100) {
                this.displayTimer.setDelay(100);
            } else {
                this.displayTimer.setDelay(50);
            }

            if(Challenge.logging > 1) {
                System.out.println("Speed = " + this.speed);
            }
        }

    }

    public class SimPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private int floors;
        private int height;
        private int elevators;
        private int order;
        private int buildingX;
        private int buildingY;
        private int elevatorX;
        private int elevatorY;
        private int elevatorHeight;
        private int elevatorWidth;
        private int elevatorPosition;
        private int offsetX = 120;
        private int offsetY = 60;
        private int buildingSizeX;
        private int buildingSizeY;
        private int floorHeight = 16;
        private int shaftWidth = 40;
        private int frameWidth = 2;
        private int border = 10;
        private int textOffset = 12;
        private int shaftOffset = 70;
        private int floorOffset = 20;
        private ImageIcon[] elevatorImage = new ImageIcon[20];
        private int[][] waitingPassengers = new int[99][2];
        private double heightScale = 0.0D;
        private double elevatorScale = 0.0D;
        private ImageIcon elevatorUpOpenImage;
        private ImageIcon elevatorDownOpenImage;
        private ImageIcon elevatorStoppedOpenImage;
        private ImageIcon elevatorAtPositionOpenImage;
        private ImageIcon elevatorUpClosedImage;
        private ImageIcon elevatorDownClosedImage;
        private ImageIcon elevatorStoppedClosedImage;
        private ImageIcon elevatorAtPositionClosedImage;

        SimPanel() {
            this.floors = Simulator.this.model.getBuildingFloors();
            this.height = Simulator.this.model.getBuildingFloorHeight();
            this.elevators = Simulator.this.model.getElevators();
            this.order = Simulator.this.console.desktop.getComponentZOrder(Simulator.this.simPanel) + 1;
            this.buildingX = 2 * this.frameWidth + (this.elevators + 1) * this.shaftWidth;
            this.buildingY = 2 * this.frameWidth + this.floors * this.floorHeight;
            this.heightScale = (double)(this.floors * this.height) / (double)(this.buildingY - 2 * this.frameWidth);
            this.elevatorStoppedOpenImage = Console.createImageIcon("ElevatorStoppedOpen.jpg", "");
            this.elevatorX = this.elevatorStoppedOpenImage.getIconWidth();
            this.elevatorY = this.elevatorStoppedOpenImage.getIconHeight();
            this.elevatorScale = Math.max((double)this.elevatorX / (double)this.shaftWidth, (double)this.elevatorY / (double)this.floorHeight);
            this.elevatorWidth = Math.round((float)((double)this.elevatorX / this.elevatorScale));
            this.elevatorHeight = Math.round((float)((double)this.elevatorY / this.elevatorScale));
            this.elevatorStoppedOpenImage.setImage(this.elevatorStoppedOpenImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorUpOpenImage = Console.createImageIcon("ElevatorGoingUpOpen.jpg", "");
            this.elevatorUpOpenImage.setImage(this.elevatorUpOpenImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorDownOpenImage = Console.createImageIcon("ElevatorGoingDownOpen.jpg", "");
            this.elevatorDownOpenImage.setImage(this.elevatorDownOpenImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorAtPositionOpenImage = Console.createImageIcon("ElevatorAtPositionOpen.jpg", "");
            this.elevatorAtPositionOpenImage.setImage(this.elevatorAtPositionOpenImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorStoppedClosedImage = Console.createImageIcon("ElevatorStoppedClosed.jpg", "");
            this.elevatorStoppedClosedImage.setImage(this.elevatorStoppedClosedImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorUpClosedImage = Console.createImageIcon("ElevatorGoingUpClosed.jpg", "");
            this.elevatorUpClosedImage.setImage(this.elevatorUpClosedImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorDownClosedImage = Console.createImageIcon("ElevatorGoingDownClosed.jpg", "");
            this.elevatorDownClosedImage.setImage(this.elevatorDownClosedImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));
            this.elevatorAtPositionClosedImage = Console.createImageIcon("ElevatorAtPositionClosed.jpg", "");
            this.elevatorAtPositionClosedImage.setImage(this.elevatorAtPositionClosedImage.getImage().getScaledInstance(this.elevatorWidth, this.elevatorHeight, 4));

            for(int i = 0; i < this.elevators; ++i) {
                this.elevatorImage[i] = this.elevatorStoppedOpenImage;
            }

            this.setMinimumSize(new Dimension(this.offsetX + this.buildingX + this.border, this.offsetY + this.buildingY + this.border));
            this.setPreferredSize(new Dimension(this.offsetX + this.buildingX + this.border, this.offsetY + this.buildingY + this.border));
        }

        protected void paintComponent(Graphics g) {
            boolean i = false;
            boolean j = false;
            boolean direction = false;
            if(this.isOpaque()) {
                g.setColor(this.getBackground());
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }

            g.setColor(Color.WHITE);
            g.fillRect(this.offsetX, this.offsetY, this.buildingX, this.buildingY);
            g.setColor(Color.BLACK);
            g.drawRect(this.offsetX, this.offsetY, this.buildingX, this.buildingY);
            g.drawRect(this.offsetX + 1, this.offsetY + 1, this.buildingX - 2, this.buildingY - 2);
            g.setColor(Color.DARK_GRAY);

            int var7;
            for(var7 = 1; var7 < this.floors; ++var7) {
                g.drawLine(this.offsetX + this.frameWidth, this.offsetY + this.buildingY - this.frameWidth - var7 * this.floorHeight, this.offsetX + this.buildingX - this.frameWidth, this.offsetY + this.buildingY - this.frameWidth - var7 * this.floorHeight);
            }

            for(var7 = 1; var7 <= this.elevators; ++var7) {
                g.drawLine(this.offsetX + this.frameWidth + var7 * this.shaftWidth, this.offsetY + this.frameWidth, this.offsetX + this.frameWidth + var7 * this.shaftWidth, this.offsetY + this.buildingY - this.frameWidth);
            }

            g.setColor(Color.GRAY);

            for(var7 = 1; var7 <= this.elevators; ++var7) {
                for(int var8 = 0; var8 < this.floors; ++var8) {
                    if(Simulator.this.model.getServicesFloor(var7 - 1, var8)) {
                        g.fillRect(this.offsetX + this.frameWidth + var7 * this.shaftWidth - 1, this.offsetY + this.buildingY - this.frameWidth - this.floorHeight / 2 - this.floorHeight * var8 - 1, 3, 3);
                    }
                }
            }

            g.setColor(Color.LIGHT_GRAY);

            for(var7 = 0; var7 < 3; ++var7) {
                g.fillRect(this.shaftOffset, this.offsetY - (var7 + 1) * this.floorHeight, this.offsetX - this.shaftOffset + this.buildingX - this.frameWidth, this.floorHeight - 2);
            }

            g.setColor(Color.BLACK);
            g.drawString("Target:", this.shaftOffset + 10, this.offsetY - 3 * this.floorHeight + this.textOffset);
            g.drawString("Passengers:", this.shaftOffset + 10, this.offsetY - 2 * this.floorHeight + this.textOffset);
            g.drawString("Weight:", this.shaftOffset + 10, this.offsetY - 1 * this.floorHeight + this.textOffset);

            for(var7 = 0; var7 < this.elevators; ++var7) {
                g.drawString(String.valueOf(Simulator.this.model.getElevatorTarget(var7) + 1), this.offsetX + this.frameWidth + (var7 + 1) * this.shaftWidth - 5, this.offsetY - 3 * this.floorHeight + this.textOffset);
                g.drawString(String.valueOf(Simulator.this.model.getElevatorPassengerCount(var7)), this.offsetX + this.frameWidth + (var7 + 1) * this.shaftWidth - 5, this.offsetY - 2 * this.floorHeight + this.textOffset);
                g.drawString(String.valueOf(Simulator.this.model.getElevatorWeight(var7)), this.offsetX + this.frameWidth + (var7 + 1) * this.shaftWidth - 5, this.offsetY - 1 * this.floorHeight + this.textOffset);
            }

            g.setColor(Color.LIGHT_GRAY);

            for(var7 = 0; var7 < this.floors; ++var7) {
                g.fillRect(this.floorOffset, this.offsetY + this.buildingY - this.frameWidth - (var7 + 1) * this.floorHeight + 1, this.offsetX - this.floorOffset - this.frameWidth, this.floorHeight - 2);
            }

            g.setColor(Color.BLACK);
            boolean y = false;
            this.waitingPassengers = Simulator.this.model.getWaitingPassengers();

            for(var7 = 0; var7 < this.floors; ++var7) {
                int var10 = this.offsetY + this.buildingY - this.frameWidth - (var7 + 1) * this.floorHeight + this.textOffset;
                g.drawString("#" + String.valueOf(var7 + 1), this.floorOffset + 10, var10);
                g.drawString("U:" + String.valueOf(this.waitingPassengers[var7][0]), this.floorOffset + 40, var10);
                g.drawString("D:" + String.valueOf(this.waitingPassengers[var7][1]), this.floorOffset + 70, var10);
            }

            for(var7 = 0; var7 < this.elevators; ++var7) {
                int var9 = Simulator.this.model.getElevatorDirection(var7);
                int doors = Simulator.this.model.getElevatorDoors(var7);
                if(Simulator.this.model.getElevatorDirection(var7) == 0) {
                    if(doors == 0) {
                        this.elevatorImage[var7] = this.elevatorUpOpenImage;
                    } else {
                        this.elevatorImage[var7] = this.elevatorUpClosedImage;
                    }
                } else if(var9 == 1) {
                    if(doors == 0) {
                        this.elevatorImage[var7] = this.elevatorDownOpenImage;
                    } else {
                        this.elevatorImage[var7] = this.elevatorDownClosedImage;
                    }
                } else if(var9 == 2) {
                    if(doors == 0) {
                        this.elevatorImage[var7] = this.elevatorStoppedOpenImage;
                    } else {
                        this.elevatorImage[var7] = this.elevatorStoppedClosedImage;
                    }
                } else if(doors == 0) {
                    this.elevatorImage[var7] = this.elevatorAtPositionOpenImage;
                } else {
                    this.elevatorImage[var7] = this.elevatorAtPositionClosedImage;
                }

                this.elevatorPosition = 1 + this.offsetY + this.buildingY - this.frameWidth - this.elevatorHeight - Math.round((float)((double)Simulator.this.model.getElevatorPosition(var7) / this.heightScale));
                g.drawImage(this.elevatorImage[var7].getImage(), this.offsetX + this.frameWidth + (var7 + 1) * this.shaftWidth - this.elevatorWidth / 2, this.elevatorPosition, this.elevatorImage[var7].getImageObserver());
            }

        }
    }

    class PassengerTableModel extends AbstractTableModel {
        private String[] columnNames = new String[]{"ID", "Depart", "Target", "Waiting", "Riding", "On"};

        PassengerTableModel() {
        }

        public int getColumnCount() {
            return 6;
        }

        public int getRowCount() {
            return Simulator.this.model.getPassengerNum();
        }

        public String getColumnName(int col) {
            return this.columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return "   " + String.valueOf(Simulator.this.model.returnTableValue(row, col));
        }

        public void setValueAt(int row, int col) {
            this.fireTableCellUpdated(row, col);
        }

        public void updatePassengerTable() {
            this.fireTableDataChanged();
        }
    }
}
