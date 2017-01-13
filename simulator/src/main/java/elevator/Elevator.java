//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

public class Elevator {
    static final int DOORSOPEN = 1;
    static final int DOORSCLOSED = 2;
    static final int DOORSOPENING = 3;
    static final int DOORSCLOSING = 4;
    static final int DOORTIMING = 10;
    static final int UP = 0;
    static final int DOWN = 1;
    static final int STOPPED = 2;
    double currentSpeed;
    int currentDirection;
    int passengerCount = 0;
    private boolean slowing;
    double currentPosition;
    double targetPosition;
    int doors;
    int doorMoving = 0;
    int target;
    int committedDirection;
    int weight;
    long lastBoarded;
    private int wait;
    boolean beamBroken;
    boolean[] buttons = new boolean[99];
    boolean[] servicesFloors = new boolean[99];
    double speed = 0.0D;
    double tickSpeed = 0.0D;
    int ID = 0;
    int capacity = 0;
    double accel = 0.0D;
    double tickAccel = 0.0D;
    Model model;

    Elevator(Model model) {
        this.model = model;
        this.resetElevators();
    }

    int getTarget() {
        return this.target;
    }

    int getTargetPosition() {
        return (int)Math.round(this.targetPosition);
    }

    void setTarget(int target) {
        this.target = target;
    }

    int getPosition() {
        return (int)Math.round(this.currentPosition);
    }

    boolean elevatorReadyToExit(int floor) {
        return this.doors == 1 && Math.round(10.0D * this.currentPosition) == (long)(10 * floor * this.model.building.floorHeight);
    }

    boolean elevatorReadyToBoard(int departFloor, int targetFloor) {
        return this.doors == 1 & Math.round(10.0D * this.currentPosition) == (long)(10 * departFloor * this.model.building.floorHeight) & this.passengerCount < this.model.building.elevatorCapacity & this.servicesFloors[targetFloor] & (targetFloor > departFloor && this.committedDirection != 1 || targetFloor < departFloor && this.committedDirection != 0);
    }

    void setButton(int floor, boolean value) {
        this.buttons[floor] = value;
    }

    boolean getButton(int floor) {
        return this.buttons[floor];
    }

    int tick() {
        double stoppingDistance = 0.0D;
        double testDistance = 0.0D;
        double tickSpeed = this.speed / 10.0D;
        double tickAccel = this.accel / 10.0D;
        this.targetPosition = (double)(this.target * this.model.building.floorHeight);
        this.slowing = false;
        if(Math.round(10.0D * this.targetPosition) != Math.round(10.0D * this.currentPosition)) {
            if(this.doors == 1) {
                this.slowing = false;
                if(this.model.clock >= 5L + this.lastBoarded & (!this.beamBroken | this.passengerCount >= this.model.building.elevatorCapacity)) {
                    this.doors = 4;
                    this.doorMoving = 10;
                    if(Challenge.logging >= 3) {
                        System.out.println("Elevators doors on " + Math.round(this.currentPosition) / (long)this.model.building.floorHeight + " closing" + " at t=" + this.model.clock);
                    }

                    if(Math.round(10.0D * this.targetPosition) > Math.round(10.0D * this.currentPosition)) {
                        this.currentDirection = 0;
                    } else {
                        this.currentDirection = 1;
                    }
                }
            } else if(this.doors == 4) {
                if(--this.doorMoving == 0) {
                    this.doors = 2;
                }
            } else if(this.doors == 2) {
                if(Challenge.logging >= 3) {
                    System.out.println("Targetting " + this.targetPosition + ". " + "Position: " + this.currentPosition + " with speed: " + this.currentSpeed + " at: " + this.model.clock);
                }

                stoppingDistance = Math.abs(this.currentSpeed * this.currentSpeed / (2.0D * tickAccel));
                testDistance = Math.abs(this.targetPosition - (this.currentPosition + this.currentSpeed));
                if(Math.round(10.0D * this.currentSpeed) != 0L & (Math.round(10.0D * this.currentPosition) > Math.round(10.0D * this.targetPosition) & this.currentDirection == 0 | Math.round(10.0D * this.currentPosition) < Math.round(10.0D * this.targetPosition) & this.currentDirection == 1)) {
                    this.slowing = true;
                    if(this.currentDirection == 0) {
                        this.currentSpeed -= tickAccel;
                    } else {
                        this.currentSpeed += tickAccel;
                    }

                    if(Math.round(10.0D * this.currentSpeed) == 0L) {
                        if(this.currentDirection == 0) {
                            this.currentDirection = 1;
                        } else if(this.currentDirection == 1) {
                            this.currentDirection = 0;
                        }

                        this.slowing = false;
                    }
                } else if(Math.round(10.0D * stoppingDistance) >= Math.round(10.0D * testDistance)) {
                    this.slowing = true;
                    if(this.currentDirection == 0) {
                        this.currentSpeed -= tickAccel;
                    } else {
                        this.currentSpeed += tickAccel;
                    }
                }

                if(!this.slowing) {
                    if(this.currentDirection == 0) {
                        this.currentSpeed = Math.min(this.currentSpeed + tickAccel, tickSpeed);
                    } else {
                        this.currentSpeed = Math.max(this.currentSpeed - tickAccel, -tickSpeed);
                    }
                }

                if(this.slowing & Math.abs(this.targetPosition - this.currentPosition) <= tickAccel) {
                    this.currentPosition = this.targetPosition;
                    this.currentSpeed = 0.0D;
                } else {
                    this.currentPosition += this.currentSpeed;
                }

                if(this.currentPosition < -1.0D) {
                    if(Challenge.logging >= 1) {
                        System.out.println("Elevator going out of bounds at t=" + this.model.clock);
                    }

                    this.currentPosition = 0.0D;
                }

                if(Math.round(this.currentPosition * 10.0D) > (long)(10 * ((this.model.building.floors - 1) * this.model.building.floorHeight + 1))) {
                    if(Challenge.logging >= 1) {
                        System.out.println("Elevator going out of bounds at t=" + this.model.clock);
                    }

                    this.currentPosition = (double)(this.model.building.floors - 1) * (double)this.model.building.floorHeight;
                    this.currentSpeed = 0.0D;
                }

                if(this.currentDirection == 2 && Challenge.logging >= 1) {
                    System.out.println("EEEEEK - Elevator stopped when it should be up or down at t=" + this.model.clock);
                }
            }
        } else {
            if(this.doors == 2) {
                this.doors = 3;
                this.doorMoving = 10;
                this.currentDirection = 2;
                this.currentSpeed = 0.0D;
                if(Challenge.logging >= 3) {
                    System.out.println("Elevators doors on " + Math.round(this.currentPosition) / (long)this.model.building.floorHeight + " openning" + " at t=" + this.model.clock);
                }

                this.buttons[this.target] = false;
                this.slowing = false;
            }

            if(this.doors == 3 && --this.doorMoving == 0) {
                this.doors = 1;
            }
        }

        if(Challenge.logging >= 3) {
            System.out.println("Speed=" + this.currentSpeed + ". Position: " + this.currentPosition + ". Speed/Accel: " + tickSpeed + "/" + tickAccel);
        }

        return (int)Math.round(this.currentPosition);
    }

    void resetElevators() {
        this.currentPosition = 0.0D;
        this.doors = 1;
        this.currentDirection = 2;
        this.target = 0;
        this.passengerCount = 0;
        this.beamBroken = false;
        this.lastBoarded = 0L;
        this.weight = 0;
        this.wait = 1;
        this.committedDirection = 2;

        for(int i = 0; i < 99; ++i) {
            this.buttons[i] = false;
            this.servicesFloors[i] = true;
        }

    }
}
