//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

public class Building {
    int floors = 0;
    int elevators = 0;
    int floorHeight = 0;
    int elevatorSpeed = 0;
    int elevatorCapacity = 0;
    int elevatorAccel = 0;
    Elevator[] elevator = new Elevator[20];
    boolean[][] floorButtons = new boolean[99][2];
    boolean[] doorSensor = new boolean[99];
    static int UP = 0;
    static int DOWN = 1;
    Model model;

    Building(Model model) {
        this.model = model;

        for(int i = 0; i < 20; ++i) {
            this.elevator[i] = new Elevator(model);
        }

    }
}
