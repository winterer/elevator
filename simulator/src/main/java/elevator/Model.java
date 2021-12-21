//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import java.util.ArrayList;

public class Model {
    static final int DOORSOPEN = 1;
    static final int DOORSCLOSED = 2;
    static final int UP = 0;
    static final int DOWN = 1;
    static final int STOPPED = 2;
    static final long TICK_DELAY = 50L;
    static final int MAXSTAGES = 10;
    static final int OPEN = 0;
    static final int PLAY = 1;
    static final int PAUSE = 2;
    static final int INIT_FLOORS = 25;
    static final int INIT_ELEVATORS = 3;
    static final int INIT_FLOOR_HEIGHT = 10;
    static final int INIT_ELEVATOR_CAPACITY = 10;
    static final int INIT_ELEVATOR_SPEED = 5;
    static final int INIT_ELEVATOR_ACCEL = 1;
    static final String INIT_TITLE = "title";
    static final String INIT_DESCRIPTION = "description";
    static final int INIT_STAGES = 1;
    static final int INIT_STAGE_TIME = 60;
    static final int INIT_GROUND_ARRIVAL = 10;
    static final int INIT_OTHER_ARRIVAL = 100;
    static final int INIT_OTHER_EXIT = 50;
    static final int INIT_REPEAT = 1;
    static final int FLOORS_MAX = 99;
    static final int FLOORS_MIN = 1;
    static final int FLOORHEIGHT_MAX = 20;
    static final int FLOORHEIGHT_MIN = 5;
    static final int ELEVATORS_MAX = 20;
    static final int ELEVATORS_MIN = 1;
    static final int TITLE_MIN = 1;
    static final int TITLE_MAX = 25;
    static final int DESCRIPTION_MIN = 0;
    static final int DESCRIPTION_MAX = 1024;
    static final int STAGES_MIN = 1;
    static final int STAGES_MAX = 3;
    static final int REPEAT_MIN = 1;
    static final int REPEAT_MAX = 1000;
    static final int STAGE_TIME_MIN = 1;
    static final int STAGE_TIME_MAX = 10000;
    static final int GROUND_ARRIVAL_MIN = 0;
    static final int GROUND_ARRIVAL_MAX = 10000;
    static final int OTHER_ARRIVAL_MIN = 0;
    static final int OTHER_ARRIVAL_MAX = 1000;
    static final int OTHER_EXIT_MIN = 0;
    static final int OTHER_EXIT_MAX = 100;
    static final int SPEED_MIN = 1;
    static final int SPEED_MAX = 20;
    static final int ACCEL_MIN = 1;
    static final int ACCEL_MAX = 3;
    static final int CAPACITY_MIN = 1;
    static final int CAPACITY_MAX = 30;
    Building building = new Building(this);
    Stage[] stages = new Stage[3];
    int stageNum;
    int repeat;
    String title = null;
    String description = null;
    Interface controllerInterface;
    long clock = 0L;
    ArrayList<Passenger> passengers = new ArrayList<>(10);
    boolean splitsAllowed = true;

    public Model() {
        for(int i = 0; i < 3; ++i) {
            this.stages[i] = new Stage();
        }

        this.controllerInterface = new Interface(this);
        this.initModel();
    }

    Interface getInterface() {
        return this.controllerInterface;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setBuildingFloors(int floors) {
        this.building.floors = floors;
    }

    void setBuildingFloorHeight(int height) {
        this.building.floorHeight = height;
    }

    void setElevators(int elevators) {
        this.building.elevators = elevators;
    }

    void setElevatorCapacity(int capacity) {
        this.building.elevatorCapacity = capacity;

        for(int i = 0; i < 20; ++i) {
            this.building.elevator[i].capacity = capacity;
        }

    }

    void setElevatorSpeed(int speed) {
        this.building.elevatorSpeed = speed;

        for(int i = 0; i < 20; ++i) {
            this.building.elevator[i].speed = (double)((float)speed);
        }

    }

    void setElevatorAccel(int accel) {
        this.building.elevatorAccel = accel;

        for(int i = 0; i < 20; ++i) {
            this.building.elevator[i].accel = (double)((float)accel);
        }

    }

    void setStageNum(int number) {
        this.stageNum = number;
    }

    void setStageTime(int num, int time) {
        this.stages[num].stageTime = time;
    }

    void setGroundArrival(int stage, int rate) {
        this.stages[stage].groundArrival = rate;
    }

    void setOtherArrival(int stage, int rate) {
        this.stages[stage].otherArrival = rate;
    }

    void setOtherExit(int stage, int rate) {
        this.stages[stage].otherExit = rate;
    }

    void setRepeat(int number) {
        this.repeat = number;
    }

    String getTitle() {
        return this.title;
    }

    String getDescription() {
        return this.description;
    }

    int getBuildingFloors() {
        return this.building.floors;
    }

    int getBuildingFloorHeight() {
        return this.building.floorHeight;
    }

    int getElevators() {
        return this.building.elevators;
    }

    int getElevatorCapacity() {
        return this.building.elevatorCapacity;
    }

    int getElevatorSpeed() {
        return this.building.elevatorSpeed;
    }

    int getElevatorAccel() {
        return this.building.elevatorAccel;
    }

    int getStageNum() {
        return this.stageNum;
    }

    int getStageTime(int stage) {
        return this.stages[stage].stageTime;
    }

    int getGroundArrival(int stage) {
        return this.stages[stage].groundArrival;
    }

    int getOtherArrival(int stage) {
        return this.stages[stage].otherArrival;
    }

    int getOtherExit(int stage) {
        return this.stages[stage].otherExit;
    }

    int getRepeat() {
        return this.repeat;
    }

    void setClock(long clock) {
        this.clock = clock;
    }

    int getElevatorTarget(int elevator) {
        return this.building.elevator[elevator].target;
    }

    void setElevatorTarget(int elevator, int targetFloor) {
        this.building.elevator[elevator].target = targetFloor;
    }

    int getElevatorWeight(int elevator) {
        return this.building.elevator[elevator].weight;
    }

    int getElevatorPassengerCount(int elevator) {
        return this.building.elevator[elevator].passengerCount;
    }

    boolean getServicesFloor(int elevator, int floor) {
        return this.building.elevator[elevator].servicesFloors[floor];
    }

    void setButton(int floor, int button, boolean setting) {
        this.building.floorButtons[floor][button] = setting;
    }

    boolean getButton(int floor, int button) {
        return this.building.floorButtons[floor][button];
    }

    int getElevatorPosition(int elevator) {
        return (int)Math.round(this.building.elevator[elevator].currentPosition);
    }

    int getElevatorDirection(int elevator) {
        return this.building.elevator[elevator].committedDirection;
    }

    int getElevatorDoors(int elevator) {
        return this.building.elevator[elevator].doors;
    }

    int[][] getWaitingPassengers() {
        int[][] waiting = new int[99][2];

        int var5;
        for(var5 = 0; var5 < this.building.floors; ++var5) {
            waiting[var5][0] = 0;
            waiting[var5][1] = 0;
        }

        for(var5 = 0; var5 < this.passengers.size(); ++var5) {
            Passenger p = (Passenger)this.passengers.get(var5);
            if(p.status == 1) {
                if(p.targetFloor > p.departFloor) {
                    ++waiting[p.departFloor][0];
                } else {
                    ++waiting[p.departFloor][1];
                }
            }
        }

        return waiting;
    }

    Model cloneModel(Model sourceModel) {
        this.setTitle(sourceModel.getTitle());
        this.setDescription(sourceModel.getDescription());
        this.setBuildingFloors(sourceModel.getBuildingFloors());
        this.setElevators(sourceModel.getElevators());
        this.setBuildingFloorHeight(sourceModel.getBuildingFloorHeight());
        this.setElevatorSpeed(sourceModel.getElevatorSpeed());
        this.setElevatorAccel(sourceModel.getElevatorAccel());
        this.setElevatorCapacity(sourceModel.getElevatorCapacity());
        this.setStageNum(sourceModel.getStageNum());
        this.setRepeat(sourceModel.getRepeat());

        for(int i = 0; i < sourceModel.getStageNum(); ++i) {
            this.setStageTime(i, sourceModel.getStageTime(i));
            this.setGroundArrival(i, sourceModel.getGroundArrival(i));
            this.setOtherArrival(i, sourceModel.getOtherArrival(i));
            this.setOtherExit(i, sourceModel.getOtherExit(i));
        }

        return this;
    }

    void initModel() {
        this.setBuildingFloors(25);
        this.setElevators(3);
        this.setBuildingFloorHeight(10);
        this.setElevatorSpeed(5);
        this.setElevatorAccel(1);
        this.setElevatorCapacity(10);
        this.setTitle("title");
        this.setDescription("description");
        this.setStageNum(1);
        this.setRepeat(1);

        for(int i = 0; i < 3; ++i) {
            this.setStageTime(i, 60);
            this.setGroundArrival(i, 10);
            this.setOtherArrival(i, 100);
            this.setOtherExit(i, 50);
        }

    }

    boolean checkModel() {
        boolean ok = !(this.getBuildingFloors() < 1 | this.getBuildingFloors() > 99 | this.getElevators() < 1 | this.getElevators() > 20 | this.getBuildingFloorHeight() < 5 | this.getBuildingFloorHeight() > 20 | this.getElevatorSpeed() < 1 | this.getElevators() > 20 | this.getElevatorCapacity() < 1 | this.getElevatorCapacity() > 30 | this.getTitle().length() < 1 | this.getTitle().length() > 25 | this.getDescription().length() < 0 | this.getDescription().length() > 1024) | this.getStageNum() < 1 | this.getStageNum() > 3 | this.getRepeat() < 1 | this.getRepeat() > 1000;
        if(ok) {
            for(int i = 0; i < this.getStageNum(); ++i) {
                if(ok) {
                    ok = !(this.getStageTime(i) < 1 | this.getStageTime(i) > 10000 | this.getGroundArrival(i) < 0 | this.getGroundArrival(i) > 10000 | this.getOtherArrival(i) < 0 | this.getOtherArrival(i) > 1000 | this.getOtherExit(i) < 0 | this.getOtherExit(i) > 100);
                }
            }
        }

        return ok;
    }

    void clearPassengers() {
        Passenger.count = 0;
    }

    int getPassengerNum() {
        return this.passengers.size();
    }

    Object returnTableValue(int row, int col) {
        if(row >= this.passengers.size()) {
            return Integer.valueOf(0);
        } else {
            try {
                if(col == 0) {
                    return String.valueOf(((Passenger)this.passengers.get(row)).id);
                }

                if(col == 1) {
                    return Integer.valueOf(((Passenger)this.passengers.get(row)).originalFloor + 1);
                }

                if(col == 2) {
                    if(((Passenger)this.passengers.get(row)).split) {
                        return ((Passenger)this.passengers.get(row)).finalFloor + 1 + " (" + (((Passenger)this.passengers.get(row)).splitFloor + 1) + ")";
                    }

                    return Integer.valueOf(((Passenger)this.passengers.get(row)).targetFloor + 1);
                }

                if(col == 3) {
                    if(((Passenger)this.passengers.get(row)).boardTime == 0L) {
                        return Long.valueOf((this.clock - ((Passenger)this.passengers.get(row)).createTime) / 10L);
                    }

                    return Long.valueOf((((Passenger)this.passengers.get(row)).boardTime - ((Passenger)this.passengers.get(row)).createTime) / 10L);
                }

                if(col != 4) {
                    if(col != 5) {
                        return Integer.valueOf(0);
                    }

                    if(((Passenger)this.passengers.get(row)).boardTime > 0L & ((Passenger)this.passengers.get(row)).status != 1) {
                        return Integer.valueOf(((Passenger)this.passengers.get(row)).onElevator + 1);
                    }

                    return "";
                }

                if(((Passenger)this.passengers.get(row)).boardTime > 0L) {
                    return Long.valueOf((this.clock - ((Passenger)this.passengers.get(row)).boardTime) / 10L);
                }
            } catch (Exception var4) {
                if(Challenge.logging >= 2) {
                    System.out.println("Exception Caught in Console");
                }

                return Integer.valueOf(0);
            }

            return Integer.valueOf(0);
        }
    }
}
