//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.event.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyVetoException;
import java.io.*;

class Editor extends JInternalFrame implements ActionListener, ChangeListener, DocumentListener, InternalFrameListener {
    private static final long serialVersionUID = 1L;
    private static int editorCount = 0;
    private static int editorX = 30;
    private static int editorY = 30;
    private static int editorActive = 0;
    private Model model;
    private Console console;
    private JPanel[] scenarioStagePane = new JPanel[3];
    private SpinnerModel[] stageTimeSpinnerModel = new SpinnerModel[3];
    private SpinnerModel[] stageGroundArrivalSpinnerModel = new SpinnerModel[3];
    private SpinnerModel[] stageOtherArrivalSpinnerModel = new SpinnerModel[3];
    private SpinnerModel[] stageOtherExitSpinnerModel = new SpinnerModel[3];
    private SpinnerModel stageSpinnerModel;
    private SpinnerModel repeatSpinnerModel;
    private SpinnerModel floorNumSpinnerModel;
    private SpinnerModel floorHeightSpinnerModel;
    private SpinnerModel elevatorNumSpinnerModel;
    private SpinnerModel elevatorCapacitySpinnerModel;
    private SpinnerModel elevatorSpeedSpinnerModel;
    private SpinnerModel elevatorAccelSpinnerModel;
    private JSpinner[] stageTimeSpinner = new JSpinner[3];
    private JSpinner[] stageGroundArrivalSpinner = new JSpinner[3];
    private JSpinner[] stageOtherArrivalSpinner = new JSpinner[3];
    private JSpinner[] stageOtherExitSpinner = new JSpinner[3];
    private JSpinner stageSpinner;
    private JSpinner repeatSpinner;
    private JSpinner floorNumSpinner;
    private JSpinner floorHeightSpinner;
    private JSpinner elevatorNumSpinner;
    private JSpinner elevatorCapacitySpinner;
    private JSpinner elevatorSpeedSpinner;
    private JSpinner elevatorAccelSpinner;
    private JTextField simNameText;
    private JTextArea simDescriptionText;
    private JPanel simulationPanel;
    private JPanel buildingPanel;
    private JPanel elevatorPanel;
    private JPanel notesPanel;
    private JPanel buildingPane;
    private JPanel elevatorPane;
    private JPanel scenarioDescriptionPane;
    private JButton editorApply;
    private JButton editorRestore;
    private JButton editorRun;
    private boolean isDirty = false;
    private boolean isSaved = false;
    private int x1Size = 109;
    private int y1Size = 22;
    private int x2Size = 50;
    private int y2Size = 22;
    private Document document;
    private String saveFile = null;
    private String saveAsFile = null;
    private String tempFile = null;
    private String docString;
    private File directory = null;
    private File file = null;
    private JavaFileFilter fileFilter = new JavaFileFilter();
    private Interface controllerInterface;
    private static final String TAG_SCENARIO = "Scenario";
    private static final String TAG_TITLE = "Title";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_FLOORS = "Floors";
    private static final String TAG_FLOOR_HEIGHT = "FloorHeight";
    private static final String TAG_ELEVATORS = "Elevators";
    private static final String TAG_ELEVATOR_SPEED = "ElevatorSpeed";
    private static final String TAG_ELEVATOR_CAPACITY = "ElevatorCapacity";
    private static final String TAG_ELEVATOR_ACCEL = "ElevatorAccel";
    private static final String TAG_STAGES = "Stages";
    private static final String TAG_REPEATS = "Repeats";
    private static final String TAG_STAGE_TIME = "StageTime";
    private static final String TAG_GROUND_ARRIVAL = "GroundArrival";
    private static final String TAG_OTHER_ARRIVAL = "OtherArrival";
    private static final String TAG_OTHER_EXIT = "OtherExit";
    private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "UTF-8";

    Editor(Console console, boolean open) {
        boolean success = true;
        this.console = console;
        this.model = new Model();
        if(open) {
            success = this.openModel();
        }

        if(!success) {
            this.model = null;
        } else {
            this.setTitle("Scenario #" + ++editorCount);
            this.setResizable(true);
            this.setIconifiable(true);
            this.setClosable(true);
            this.setMaximizable(true);
            this.addInternalFrameListener(this);
            this.setDefaultCloseOperation(0);
            this.setSize(300, 400);
            this.setLocation(editorX * editorCount, editorY * editorCount);
            this.setVisible(true);
            this.setFrameIcon(Console.createImageIcon("scenario.gif", ""));
            this.setLayout(new BorderLayout());
            JTabbedPane editorPane = new JTabbedPane(1);
            JToolBar editorToolBar = new JToolBar();
            this.editorApply = new JButton("Apply");
            this.editorApply.addActionListener(this);
            this.editorApply.setEnabled(false);
            this.editorApply.setFocusable(false);
            this.editorRestore = new JButton("Restore");
            this.editorRestore.addActionListener(this);
            this.editorRestore.setEnabled(false);
            this.editorRestore.setFocusable(false);
            this.editorRun = new JButton("Run Sim");
            this.editorRun.addActionListener(this);
            this.editorRun.setEnabled(true);
            this.editorRun.setFocusable(false);
            editorToolBar.add(this.editorApply);
            editorToolBar.add(this.editorRestore);
            editorToolBar.add(this.editorRun);
            editorToolBar.setFloatable(false);
            editorToolBar.setBorderPainted(true);
            this.add(editorToolBar, "First");
            this.simulationPanel = new JPanel(false);
            this.simulationPanel.setAutoscrolls(true);
            this.simulationPanel.setLayout(new BoxLayout(this.simulationPanel, 1));
            this.setupSimulationPanel(this.simulationPanel);
            JScrollPane simulationPane = new JScrollPane(this.simulationPanel);
            editorPane.addTab("Simulation", simulationPane);
            this.buildingPanel = new JPanel(false);
            this.buildingPanel.setAutoscrolls(true);
            this.buildingPanel.setLayout(new BoxLayout(this.buildingPanel, 1));
            this.buildingPanel.setAlignmentY(0.0F);
            this.setupBuildingPanel(this.buildingPanel);
            JScrollPane buildingPane = new JScrollPane(this.buildingPanel);
            editorPane.addTab("Building", buildingPane);
            this.elevatorPanel = new JPanel(false);
            this.elevatorPanel.setAutoscrolls(true);
            this.elevatorPanel.setLayout(new BoxLayout(this.elevatorPanel, 1));
            this.setupElevatorPanel(this.elevatorPanel);
            JScrollPane elevatorPane = new JScrollPane(this.elevatorPanel);
            editorPane.addTab("Elevator", elevatorPane);
            this.notesPanel = new JPanel(false);
            this.notesPanel.setAutoscrolls(true);
            this.notesPanel.setLayout(new BoxLayout(this.notesPanel, 1));
            this.simDescriptionText = new JTextArea(this.model.getDescription(), 3, 15);
            this.simDescriptionText.setLineWrap(true);
            this.simDescriptionText.setWrapStyleWord(true);
            this.simDescriptionText.setEditable(true);
            this.simDescriptionText.getDocument().addDocumentListener(this);
            this.simDescriptionText.setFont(this.simNameText.getFont());
            this.notesPanel.add(this.simDescriptionText);
            JScrollPane notesPane = new JScrollPane(this.notesPanel);
            editorPane.addTab("Notes", notesPane);
            this.add(editorPane);
            console.desktop.add(this);

            try {
                this.setSelected(true);
            } catch (PropertyVetoException var11) {
                ;
            }
        }

    }

    private void setupSimulationPanel(JPanel simulationPanel) {
        this.simNameText = new JTextField(this.model.getTitle(), 15);
        this.simNameText.setEditable(true);
        this.simNameText.getDocument().addDocumentListener(this);
        JLabel simNameLabel = new JLabel("Scenario Name: ", 11);
        simNameLabel.setLabelFor(this.simNameText);
        this.setSize(this.simNameText, this.x1Size, this.y1Size);
        this.stageSpinnerModel = new SpinnerNumberModel(this.model.getStageNum(), 1, 3, 1);
        this.stageSpinner = new JSpinner(this.stageSpinnerModel);
        JLabel stageSpinnerLabel = new JLabel("Number of Stages: ", 11);
        stageSpinnerLabel.setLabelFor(this.stageSpinner);
        this.stageSpinner.addChangeListener(this);
        this.setSize(this.stageSpinner, this.x1Size, this.y1Size);
        this.repeatSpinnerModel = new SpinnerNumberModel(this.model.getRepeat(), 1, 1000, 1);
        this.repeatSpinner = new JSpinner(this.repeatSpinnerModel);
        JLabel repeatSpinnerLabel = new JLabel("Number of Repeats: ", 11);
        repeatSpinnerLabel.setLabelFor(this.repeatSpinner);
        this.repeatSpinner.addChangeListener(this);
        this.setSize(this.repeatSpinner, this.x1Size, this.y1Size);
        this.scenarioDescriptionPane = new JPanel(new SpringLayout());
        this.scenarioDescriptionPane.add(simNameLabel);
        this.scenarioDescriptionPane.add(this.simNameText);
        this.scenarioDescriptionPane.add(stageSpinnerLabel);
        this.scenarioDescriptionPane.add(this.stageSpinner);
        this.scenarioDescriptionPane.add(repeatSpinnerLabel);
        this.scenarioDescriptionPane.add(this.repeatSpinner);
        SpringUtilities.makeCompactGrid(this.scenarioDescriptionPane, 3, 2, 6, 6, 6, 6);
        this.scenarioDescriptionPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Scenario"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        simulationPanel.add(this.scenarioDescriptionPane);
        this.scenarioDescriptionPane.setAlignmentX(0.0F);
        this.scenarioDescriptionPane.setVisible(true);
        JLabel[] stageTimeSpinnerLabel = new JLabel[10];
        JLabel[] stageGroundArrivalSpinnerLabel = new JLabel[10];
        JLabel[] stageOtherArrivalSpinnerLabel = new JLabel[10];
        JLabel[] stageOtherExitSpinnerLabel = new JLabel[10];

        for(int i = 0; i < 3; ++i) {
            this.scenarioStagePane[i] = new JPanel(new SpringLayout());
            this.stageTimeSpinnerModel[i] = new SpinnerNumberModel(this.model.getStageTime(i), 1, 10000, 1);
            this.stageGroundArrivalSpinnerModel[i] = new SpinnerNumberModel(this.model.getGroundArrival(i), 0, 10000, 1);
            this.stageOtherArrivalSpinnerModel[i] = new SpinnerNumberModel(this.model.getOtherArrival(i), 0, 1000, 1);
            this.stageOtherExitSpinnerModel[i] = new SpinnerNumberModel(this.model.getOtherExit(i), 0, 100, 1);
            this.stageTimeSpinner[i] = new JSpinner(this.stageTimeSpinnerModel[i]);
            this.stageTimeSpinner[i].addChangeListener(this);
            stageTimeSpinnerLabel[i] = new JLabel("Stage Length (min): ", 11);
            stageTimeSpinnerLabel[i].setLabelFor(this.stageTimeSpinner[i]);
            this.setSize(this.stageTimeSpinner[i], this.x2Size, this.y2Size);
            this.stageGroundArrivalSpinner[i] = new JSpinner(this.stageGroundArrivalSpinnerModel[i]);
            this.stageGroundArrivalSpinner[i].addChangeListener(this);
            stageGroundArrivalSpinnerLabel[i] = new JLabel("Ground Arrival Rate (secs/pers): ", 11);
            stageGroundArrivalSpinnerLabel[i].setLabelFor(this.stageGroundArrivalSpinner[i]);
            this.setSize(this.stageGroundArrivalSpinner[i], this.x2Size, this.y2Size);
            this.stageOtherArrivalSpinner[i] = new JSpinner(this.stageOtherArrivalSpinnerModel[i]);
            this.stageOtherArrivalSpinner[i].addChangeListener(this);
            stageOtherArrivalSpinnerLabel[i] = new JLabel("Other Arrival Rate (secs/pers): ", 11);
            stageOtherArrivalSpinnerLabel[i].setLabelFor(this.stageOtherArrivalSpinner[i]);
            this.setSize(this.stageOtherArrivalSpinner[i], this.x2Size, this.y2Size);
            this.stageOtherExitSpinner[i] = new JSpinner(this.stageOtherExitSpinnerModel[i]);
            this.stageOtherExitSpinner[i].addChangeListener(this);
            stageOtherExitSpinnerLabel[i] = new JLabel("Other Exit Rate (%): ", 11);
            stageOtherExitSpinnerLabel[i].setLabelFor(this.stageOtherExitSpinner[i]);
            this.setSize(this.stageOtherExitSpinner[i], this.x2Size, this.y2Size);
            this.scenarioStagePane[i].add(stageTimeSpinnerLabel[i]);
            this.scenarioStagePane[i].add(this.stageTimeSpinner[i]);
            this.scenarioStagePane[i].add(stageGroundArrivalSpinnerLabel[i]);
            this.scenarioStagePane[i].add(this.stageGroundArrivalSpinner[i]);
            this.scenarioStagePane[i].add(stageOtherArrivalSpinnerLabel[i]);
            this.scenarioStagePane[i].add(this.stageOtherArrivalSpinner[i]);
            this.scenarioStagePane[i].add(stageOtherExitSpinnerLabel[i]);
            this.scenarioStagePane[i].add(this.stageOtherExitSpinner[i]);
            SpringUtilities.makeCompactGrid(this.scenarioStagePane[i], 4, 2, 6, 6, 6, 6);
            this.scenarioStagePane[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Stage " + (i + 1)), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            simulationPanel.add(this.scenarioStagePane[i]);
            this.scenarioStagePane[i].setAlignmentX(0.0F);
            if(i < this.model.getStageNum()) {
                this.scenarioStagePane[i].setVisible(true);
            } else {
                this.scenarioStagePane[i].setVisible(false);
            }
        }

    }

    private void setupBuildingPanel(JPanel buildingPanel) {
        this.floorNumSpinnerModel = new SpinnerNumberModel(this.model.getBuildingFloors(), 1, 99, 1);
        this.floorNumSpinner = new JSpinner(this.floorNumSpinnerModel);
        JLabel floorNumSpinnerLabel = new JLabel("Number of Floors: ", 11);
        floorNumSpinnerLabel.setLabelFor(this.floorNumSpinner);
        this.floorNumSpinner.addChangeListener(this);
        this.setSize(this.floorNumSpinner, this.x2Size, this.y2Size);
        this.floorHeightSpinnerModel = new SpinnerNumberModel(this.model.getBuildingFloorHeight(), 5, 20, 1);
        this.floorHeightSpinner = new JSpinner(this.floorHeightSpinnerModel);
        JLabel floorHeightSpinnerLabel = new JLabel("Floor Height (ft): ", 11);
        floorHeightSpinnerLabel.setLabelFor(this.floorHeightSpinner);
        this.floorHeightSpinner.addChangeListener(this);
        this.setSize(this.floorHeightSpinner, this.x2Size, this.y2Size);
        this.buildingPane = new JPanel(new SpringLayout());
        this.buildingPane.add(floorNumSpinnerLabel);
        this.buildingPane.add(this.floorNumSpinner);
        this.buildingPane.add(floorHeightSpinnerLabel);
        this.buildingPane.add(this.floorHeightSpinner);
        SpringUtilities.makeCompactGrid(this.buildingPane, 2, 2, 6, 6, 6, 6);
        this.buildingPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Floors"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        buildingPanel.add(this.buildingPane);
        this.buildingPane.setAlignmentX(0.0F);
        this.buildingPane.setVisible(true);
    }

    private void setupElevatorPanel(JPanel elevatorPanel) {
        this.elevatorNumSpinnerModel = new SpinnerNumberModel(this.model.getElevators(), 1, 20, 1);
        this.elevatorNumSpinner = new JSpinner(this.elevatorNumSpinnerModel);
        JLabel elevatorNumSpinnerLabel = new JLabel("Number of Elevators: ", 11);
        elevatorNumSpinnerLabel.setLabelFor(this.elevatorNumSpinner);
        this.elevatorNumSpinner.addChangeListener(this);
        this.setSize(this.elevatorNumSpinner, this.x2Size, this.y2Size);
        this.elevatorCapacitySpinnerModel = new SpinnerNumberModel(this.model.getElevatorCapacity(), 1, 30, 1);
        this.elevatorCapacitySpinner = new JSpinner(this.elevatorCapacitySpinnerModel);
        JLabel elevatorCapacitySpinnerLabel = new JLabel("Elevator Capacity: ", 11);
        elevatorCapacitySpinnerLabel.setLabelFor(this.elevatorCapacitySpinner);
        this.elevatorCapacitySpinner.addChangeListener(this);
        this.setSize(this.elevatorCapacitySpinner, this.x2Size, this.y2Size);
        this.elevatorSpeedSpinnerModel = new SpinnerNumberModel(this.model.getElevatorSpeed(), 1, 20, 1);
        this.elevatorSpeedSpinner = new JSpinner(this.elevatorSpeedSpinnerModel);
        JLabel elevatorSpeedSpinnerLabel = new JLabel("Elevator Speed (ft/s): ", 11);
        elevatorSpeedSpinnerLabel.setLabelFor(this.elevatorSpeedSpinner);
        this.elevatorSpeedSpinner.addChangeListener(this);
        this.setSize(this.elevatorSpeedSpinner, this.x2Size, this.y2Size);
        this.elevatorAccelSpinnerModel = new SpinnerNumberModel(this.model.getElevatorAccel(), 1, 3, 1);
        this.elevatorAccelSpinner = new JSpinner(this.elevatorAccelSpinnerModel);
        JLabel elevatorAccelSpinnerLabel = new JLabel("Elevator Accel (ft/sec^2): ", 11);
        elevatorAccelSpinnerLabel.setLabelFor(this.elevatorAccelSpinner);
        this.elevatorAccelSpinner.addChangeListener(this);
        this.setSize(this.elevatorAccelSpinner, this.x2Size, this.y2Size);
        this.elevatorPane = new JPanel(new SpringLayout());
        this.elevatorPane.add(elevatorNumSpinnerLabel);
        this.elevatorPane.add(this.elevatorNumSpinner);
        this.elevatorPane.add(elevatorCapacitySpinnerLabel);
        this.elevatorPane.add(this.elevatorCapacitySpinner);
        this.elevatorPane.add(elevatorSpeedSpinnerLabel);
        this.elevatorPane.add(this.elevatorSpeedSpinner);
        this.elevatorPane.add(elevatorAccelSpinnerLabel);
        this.elevatorPane.add(this.elevatorAccelSpinner);
        SpringUtilities.makeCompactGrid(this.elevatorPane, 4, 2, 6, 6, 6, 6);
        this.elevatorPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Elevators"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        elevatorPanel.add(this.elevatorPane);
        this.elevatorPane.setAlignmentX(0.0F);
        this.elevatorPane.setVisible(true);
    }

    private void setSize(JComponent obj, int x, int y) {
        Dimension size = new Dimension(x, y);
        obj.setPreferredSize(size);
        obj.setMaximumSize(size);
        obj.setMinimumSize(size);
    }

    private void setDirty(boolean dirty) {
        this.isDirty = dirty;
        if(this.isDirty) {
            this.isSaved = false;
        }

        this.editorApply.setEnabled(dirty);
        this.editorRestore.setEnabled(dirty);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.editorApply) {
            this.applyChanges();
        } else if(e.getSource() == this.editorRestore) {
            this.restore();
        } else if(e.getSource() == this.editorRun) {
            this.console.newSimRun();
        } else {
            this.setDirty(true);
        }

    }

    public void stateChanged(ChangeEvent e) {
        if(e.getSource().equals(this.stageSpinner)) {
            SpinnerNumberModel model = (SpinnerNumberModel)this.stageSpinner.getModel();

            for(int i = 0; i < 3; ++i) {
                if(i < model.getNumber().intValue()) {
                    this.scenarioStagePane[i].setVisible(true);
                } else {
                    this.scenarioStagePane[i].setVisible(false);
                }
            }
        }

        this.setDirty(true);
    }

    private void applyChanges() {
        this.model.setTitle(this.simNameText.getText());
        this.model.setDescription(this.simDescriptionText.getText());
        this.model.setBuildingFloorHeight(((SpinnerNumberModel)this.floorHeightSpinnerModel).getNumber().intValue());
        this.model.setElevatorSpeed(((SpinnerNumberModel)this.elevatorSpeedSpinnerModel).getNumber().intValue());
        this.model.setElevatorCapacity(((SpinnerNumberModel)this.elevatorCapacitySpinnerModel).getNumber().intValue());
        this.model.setBuildingFloors(((SpinnerNumberModel)this.floorNumSpinnerModel).getNumber().intValue());
        this.model.setElevators(((SpinnerNumberModel)this.elevatorNumSpinnerModel).getNumber().intValue());
        this.model.setElevatorAccel(((SpinnerNumberModel)this.elevatorAccelSpinnerModel).getNumber().intValue());
        this.model.setElevatorCapacity(((SpinnerNumberModel)this.elevatorCapacitySpinnerModel).getNumber().intValue());
        this.model.setStageNum(((SpinnerNumberModel)this.stageSpinnerModel).getNumber().intValue());
        this.model.setRepeat(((SpinnerNumberModel)this.repeatSpinnerModel).getNumber().intValue());

        for(int i = 0; i < 3; ++i) {
            this.model.setStageTime(i, ((SpinnerNumberModel)this.stageTimeSpinnerModel[i]).getNumber().intValue());
            this.model.setGroundArrival(i, ((SpinnerNumberModel)this.stageGroundArrivalSpinnerModel[i]).getNumber().intValue());
            this.model.setOtherArrival(i, ((SpinnerNumberModel)this.stageOtherArrivalSpinnerModel[i]).getNumber().intValue());
            this.model.setOtherExit(i, ((SpinnerNumberModel)this.stageOtherExitSpinnerModel[i]).getNumber().intValue());
        }

        this.setDirty(false);
    }

    private void restore() {
        this.simNameText.setText(this.model.getTitle());
        this.simDescriptionText.setText(this.model.getDescription());
        this.floorHeightSpinnerModel.setValue(Integer.valueOf(this.model.getBuildingFloorHeight()));
        this.elevatorSpeedSpinnerModel.setValue(Integer.valueOf(this.model.getElevatorSpeed()));
        this.elevatorCapacitySpinnerModel.setValue(Integer.valueOf(this.model.getElevatorCapacity()));
        this.floorNumSpinnerModel.setValue(Integer.valueOf(this.model.getBuildingFloors()));
        this.elevatorNumSpinnerModel.setValue(Integer.valueOf(this.model.getElevators()));
        this.elevatorAccelSpinnerModel.setValue(Integer.valueOf(this.model.getElevatorAccel()));
        this.elevatorCapacitySpinnerModel.setValue(Integer.valueOf(this.model.getElevatorCapacity()));
        this.stageSpinnerModel.setValue(Integer.valueOf(this.model.getStageNum()));
        this.repeatSpinnerModel.setValue(Integer.valueOf(this.model.getRepeat()));

        for(int i = 0; i < 3; ++i) {
            this.stageTimeSpinnerModel[i].setValue(Integer.valueOf(this.model.getStageTime(i)));
            this.stageGroundArrivalSpinnerModel[i].setValue(Integer.valueOf(this.model.getGroundArrival(i)));
            this.stageOtherArrivalSpinnerModel[i].setValue(Integer.valueOf(this.model.getOtherArrival(i)));
            this.stageOtherExitSpinnerModel[i].setValue(Integer.valueOf(this.model.getOtherExit(i)));
        }

        this.setDirty(false);
    }

    public void caretPositionChanged(InputMethodEvent arg0) {
    }

    public void changedUpdate(DocumentEvent arg0) {
        this.setDirty(true);
    }

    public void insertUpdate(DocumentEvent arg0) {
        this.setDirty(true);
    }

    public void removeUpdate(DocumentEvent arg0) {
        this.setDirty(true);
    }

    private boolean serialAndSave(String fileName) {
        this.file = new File(fileName);
        boolean success = false;

        try {
            DocumentBuilderFactory transformer = DocumentBuilderFactory.newInstance();
            transformer.setIgnoringComments(true);
            transformer.setIgnoringElementContentWhitespace(true);
            DocumentBuilder result = transformer.newDocumentBuilder();
            this.document = result.newDocument();
            Element source = this.document.createElement("Scenario");
            this.document.appendChild(source);
            Element e = this.document.createElement("Title");
            e.appendChild(this.document.createTextNode(this.model.getTitle()));
            source.appendChild(e);
            e = this.document.createElement("Description");
            e.appendChild(this.document.createTextNode(this.model.getDescription()));
            source.appendChild(e);
            e = this.document.createElement("Floors");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getBuildingFloors())));
            source.appendChild(e);
            e = this.document.createElement("FloorHeight");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getBuildingFloorHeight())));
            source.appendChild(e);
            e = this.document.createElement("Elevators");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getElevators())));
            source.appendChild(e);
            e = this.document.createElement("ElevatorCapacity");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getElevatorCapacity())));
            source.appendChild(e);
            e = this.document.createElement("ElevatorSpeed");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getElevatorSpeed())));
            source.appendChild(e);
            e = this.document.createElement("ElevatorAccel");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getElevatorAccel())));
            source.appendChild(e);
            e = this.document.createElement("Stages");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getStageNum())));
            source.appendChild(e);
            e = this.document.createElement("Repeats");
            e.appendChild(this.document.createTextNode(Integer.toString(this.model.getRepeat())));
            source.appendChild(e);

            for(int xmlString = 0; xmlString < this.model.getStageNum(); ++xmlString) {
                e = this.document.createElement("StageTime" + xmlString);
                e.appendChild(this.document.createTextNode(Integer.toString(this.model.getStageTime(xmlString))));
                source.appendChild(e);
                e = this.document.createElement("GroundArrival" + xmlString);
                e.appendChild(this.document.createTextNode(Integer.toString(this.model.getGroundArrival(xmlString))));
                source.appendChild(e);
                e = this.document.createElement("OtherArrival" + xmlString);
                e.appendChild(this.document.createTextNode(Integer.toString(this.model.getOtherArrival(xmlString))));
                source.appendChild(e);
                e = this.document.createElement("OtherExit" + xmlString);
                e.appendChild(this.document.createTextNode(Integer.toString(this.model.getOtherExit(xmlString))));
                source.appendChild(e);
            }

            this.document.getDocumentElement().normalize();
        } catch (Exception var12) {
            System.out.println(var12.toString());
        }

        Transformer var13 = null;

        try {
            var13 = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException var10) {
            var10.printStackTrace();
        } catch (TransformerFactoryConfigurationError var11) {
            var11.printStackTrace();
        }

        var13.setOutputProperty("indent", "yes");
        StreamResult var14 = new StreamResult(new StringWriter());
        DOMSource var15 = new DOMSource(this.document);

        try {
            PrintWriter var17 = new PrintWriter(new BufferedWriter(new FileWriter(this.file)), true);
            var13.transform(var15, var14);
            String var16 = var14.getWriter().toString();
            var17.println(var16);
            if(Challenge.logging > 1) {
                System.out.println(var16);
            }

            success = true;
            this.isDirty = false;
        } catch (IOException var8) {
            var8.printStackTrace();
        } catch (TransformerException var9) {
            var9.printStackTrace();
        }

        return success;
    }

    private boolean openAndSerialize(String fromFile) {
        boolean loadError = false;
        Document document = null;
        this.file = new File(fromFile);

        try {
            DocumentBuilderFactory scenario = DocumentBuilderFactory.newInstance();
            scenario.setIgnoringElementContentWhitespace(true);
            scenario.setIgnoringComments(true);
            DocumentBuilder childNodes = scenario.newDocumentBuilder();
            document = childNodes.parse(this.file);
        } catch (Exception var9) {
            System.out.println(var9.getMessage() + ". I/O Error on file " + fromFile);
            loadError = true;
        }

        if(!loadError) {
            Element var10 = document.getDocumentElement();
            if(Challenge.logging > 2) {
                System.out.println("Loading element: " + var10.getNodeName());
            }

            if(Challenge.logging > 2) {
                System.out.println("Child elements: " + var10.getChildNodes().getLength());
            }

            NodeList var11 = var10.getChildNodes();

            for(int tagName = var10.getChildNodes().getLength() - 1; tagName >= 0; --tagName) {
                if(var11.item(tagName).getNodeName() == "#text") {
                    var10.removeChild(var11.item(tagName));
                    if(Challenge.logging > 2) {
                        System.out.println("Child element removed");
                    }
                }
            }

            String var12 = null;
            String tagValue = null;

            for(int i = 0; i < var10.getChildNodes().getLength(); ++i) {
                var12 = var11.item(i).getNodeName();
                tagValue = var11.item(i).getTextContent();
                if(Challenge.logging > 2) {
                    System.out.println("Processing: " + var12 + " and " + tagValue);
                }

                if(var12 == "Title") {
                    this.model.setTitle(tagValue);
                    if(Challenge.logging > 2) {
                        System.out.println("Got Title");
                    }
                }

                if(var12 == "Description") {
                    this.model.setDescription(tagValue);
                    if(Challenge.logging > 2) {
                        System.out.println("Got Description");
                    }
                }

                if(var12 == "Floors") {
                    this.model.setBuildingFloors(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Floors");
                    }
                }

                if(var12 == "FloorHeight") {
                    this.model.setBuildingFloorHeight(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Floor Height");
                    }
                }

                if(var12 == "Elevators") {
                    this.model.setElevators(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Elevators");
                    }
                }

                if(var12 == "ElevatorCapacity") {
                    this.model.setElevatorCapacity(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Elevator Capacity");
                    }
                }

                if(var12 == "ElevatorSpeed") {
                    this.model.setElevatorSpeed(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Elevator Speed");
                    }
                }

                if(var12 == "ElevatorAccel") {
                    this.model.setElevatorAccel(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Elevator Accel");
                    }
                }

                if(var12 == "Stages") {
                    this.model.setStageNum(Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got Stages");
                    }
                }

                if(var12 == "Repeats") {
                    this.model.setRepeat(Integer.parseInt(tagValue));
                    if(this.model.getRepeat() == 0) {
                        this.model.setRepeat(1);
                    }

                    if(Challenge.logging > 2) {
                        System.out.println("Got Repeats");
                    }
                }

                if(var12.startsWith("StageTime")) {
                    this.model.setStageTime(Integer.parseInt(var12.substring("StageTime".length())), Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got stage# " + Integer.parseInt(var12.substring("StageTime".length())) + " = " + Integer.parseInt(tagValue));
                    }
                }

                if(var12.startsWith("GroundArrival")) {
                    this.model.setGroundArrival(Integer.parseInt(var12.substring("GroundArrival".length())), Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got stage# " + Integer.parseInt(var12.substring("GroundArrival".length())) + " ground arrival = " + Integer.parseInt(tagValue));
                    }
                }

                if(var12.startsWith("OtherArrival")) {
                    this.model.setOtherArrival(Integer.parseInt(var12.substring("OtherArrival".length())), Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got stage# " + Integer.parseInt(var12.substring("OtherArrival".length())) + " other arrival= " + Integer.parseInt(tagValue));
                    }
                }

                if(var12.startsWith("OtherExit")) {
                    this.model.setOtherExit(Integer.parseInt(var12.substring("OtherExit".length())), Integer.parseInt(tagValue));
                    if(Challenge.logging > 2) {
                        System.out.println("Got stage# " + Integer.parseInt(var12.substring("OtherExit".length())) + " other exit = " + Integer.parseInt(tagValue));
                    }
                }
            }

            loadError = !this.model.checkModel();
        }

        return !loadError;
    }

    boolean saveModel(boolean saveAs) {
        boolean n = false;
        boolean bail = false;
        String name = null;
        int n1;
        if(this.isDirty) {
            n1 = JOptionPane.showConfirmDialog(this, "Would you like to apply changes to scenario \'" + this.getTitle() + "\' before saving?", "Apply Changes?", 1);
            if(Challenge.logging > 2) {
                System.out.println("Dialog option selected: " + n1);
            }

            if(n1 == 0) {
                this.applyChanges();
            }

            if(n1 == 1) {
                this.restore();
            }

            if(n1 == 2) {
                bail = true;
            }
        }

        if(!bail) {
            if(this.file == null | saveAs) {
                JFileChooser fc = new JFileChooser(this.file);
                fc.addChoosableFileFilter(this.fileFilter);
                fc.setFileFilter(this.fileFilter);
                fc.setDialogTitle("Save the Scenario \'" + this.getTitle() + "\'?");
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(0);
                if(fc.showSaveDialog(this) == 0) {
                    if(fc.getSelectedFile().exists()) {
                        n1 = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the existing file?", "Overwrite File?", 0);
                        if(n1 == 1) {
                            bail = true;
                        }
                    }

                    if(!bail) {
                        this.file = fc.getSelectedFile();
                    }
                } else {
                    bail = true;
                }
            }

            if(!bail) {
                name = this.file.getPath();
                if(!this.file.getName().contains(".")) {
                    name = name.concat(".xml");
                }

                bail = !this.serialAndSave(name);
                if(bail) {
                    JOptionPane.showMessageDialog(this, "Could not save the file.", "File System Error", 0);
                } else {
                    this.setTitle(this.file.getName());
                    this.isSaved = true;
                }
            }
        }

        return !bail;
    }

    private boolean openModel() {
        boolean bail = false;
        String name = null;
        File currentDirectory = this.file;
        if (currentDirectory == null) {
            currentDirectory = new File(System.getProperty("user.dir"));
        }
        JFileChooser fc = new JFileChooser(currentDirectory);
        fc.addChoosableFileFilter(this.fileFilter);
        fc.setFileFilter(this.fileFilter);
        fc.setDialogTitle("Open the Scenario File");
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(0);
        fc.setAcceptAllFileFilterUsed(false);
        if(fc.showOpenDialog(this) == 0) {
            this.file = fc.getSelectedFile();
        } else {
            bail = true;
        }

        if(!bail) {
            name = this.file.getPath();
            if(!this.openAndSerialize(name)) {
                JOptionPane.showMessageDialog(this, "Could not open the file.", "File System Error", 0);
                bail = true;
            } else {
                this.isDirty = false;
                this.isSaved = true;
            }
        }

        return !bail;
    }

    boolean applyChangesSim() {
        boolean bail = false;
        if(this.isDirty) {
            int n = JOptionPane.showConfirmDialog(this, "Would you like to apply changes to scenario \'" + this.getTitle() + "\' before running the scenario?", "Apply Changes?", 1);
            if(Challenge.logging > 2) {
                System.out.println("Dialog option selected: " + n);
            }

            if(n == 0) {
                this.applyChanges();
            }

            if(n == 1) {
                this.restore();
            }

            if(n == 2) {
                bail = true;
            }
        }

        return bail;
    }

    boolean closeModel() {
        boolean n = false;
        boolean bail = false;
        boolean closeNoSave = false;
        String name = null;
        int n1;
        if(this.isDirty | !this.isSaved) {
            n1 = JOptionPane.showConfirmDialog(this, "Would you like to save changes to scenario \'" + this.getTitle() + "\' before closing?", "Save Scenario?", 1);
            if(n1 == 0) {
                bail = false;
            } else if(n1 == 1) {
                closeNoSave = true;
            } else if(n1 == 2) {
                bail = true;
            } else {
                bail = true;
            }
        }

        if(this.isDirty & !closeNoSave & !bail) {
            n1 = JOptionPane.showConfirmDialog(this, "Would you like to apply changes to scenario \'" + this.getTitle() + "\' before saving?", "Apply Changes?", 1);
            if(Challenge.logging > 2) {
                System.out.println("Dialog option selected: " + n1);
            }

            if(n1 == 0) {
                this.applyChanges();
            } else if(n1 == 1) {
                this.restore();
            } else if(n1 == 2) {
                bail = true;
            } else {
                bail = true;
            }
        }

        if(!bail & !closeNoSave) {
            if(this.file == null) {
                JFileChooser fc = new JFileChooser(this.file);
                fc.addChoosableFileFilter(this.fileFilter);
                fc.setFileFilter(this.fileFilter);
                fc.setDialogTitle("Save the Scenario \'" + this.getTitle() + "\'?");
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(0);
                if(fc.showSaveDialog(this) == 0) {
                    if(fc.getSelectedFile().exists()) {
                        n1 = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the existing file?", "Overwrite File?", 0);
                        if(n1 == 0) {
                            bail = false;
                        } else if(n1 == 1) {
                            bail = true;
                        } else {
                            bail = true;
                        }
                    }

                    if(!bail) {
                        this.file = fc.getSelectedFile();
                    }
                } else {
                    bail = true;
                }
            }

            if(!bail & !closeNoSave) {
                name = this.file.getPath();
                if(!this.file.getName().contains(".")) {
                    name = name.concat(".xml");
                }

                bail = !this.serialAndSave(name);
                if(bail) {
                    JOptionPane.showMessageDialog(this, "Could not save the file.", "File System Error", 0);
                } else {
                    this.setTitle(this.file.getName());
                    this.isSaved = true;
                }
            }
        }

        return !bail;
    }

    Model getModel() {
        return this.model;
    }

    public void internalFrameActivated(InternalFrameEvent arg0) {
        this.console.setMenuEditorActive(true);
    }

    public void internalFrameClosed(InternalFrameEvent arg0) {
        if(--editorActive == 0) {
            this.console.setMenuEditorActive(false);
        }

        this.console.updateFocus();
    }

    public void internalFrameClosing(InternalFrameEvent arg0) {
        boolean bail = !this.closeModel();
        if(!bail) {
            this.dispose();
        }

    }

    public void internalFrameDeactivated(InternalFrameEvent arg0) {
    }

    public void internalFrameDeiconified(InternalFrameEvent arg0) {
    }

    public void internalFrameIconified(InternalFrameEvent arg0) {
    }

    public void internalFrameOpened(InternalFrameEvent arg0) {
        if(++editorActive == 1) {
            this.console.setMenuEditorActive(true);
        }

    }
}
