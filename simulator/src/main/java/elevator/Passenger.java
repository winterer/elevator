//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

public class Passenger {
    static int count = 0;
    int id;
    long createTime = 0L;
    long boardTime = 0L;
    long departTime = 0L;
    int departFloor = 0;
    int targetFloor = 0;
    int originalFloor = 0;
    int onElevator = 0;
    boolean split = false;
    int splitFloor = 0;
    int finalFloor = 0;
    int status = 0;
    int weight = 0;
    Model model;
    static final int WAITING = 1;
    static final int RIDING = 2;
    static final int DONE = 3;
    static final int LOADTIME = 5;

    Passenger(Model model, int departFloor, int targetFloor, boolean split, int finalFloor) {
        ++count;
        this.model = model;
        this.id = count;
        this.createTime = model.clock;
        this.departFloor = departFloor;
        this.targetFloor = targetFloor;
        this.split = split;
        this.finalFloor = finalFloor;
        this.splitFloor = targetFloor;
        this.originalFloor = departFloor;
        this.boardTime = 0L;
        this.departTime = 0L;
        this.onElevator = 99;
        this.weight = (int)(Math.random() * 100.0D) + 100;
        this.status = 1;
        if(targetFloor > departFloor) {
            model.setButton(departFloor, 0, true);
        } else {
            model.setButton(departFloor, 1, true);
        }

    }

    void tick() {
        if(this.status == 1) {
            boolean boarded = false;

            for(int elevatorCount = 0; elevatorCount < this.model.building.elevators && !boarded; ++elevatorCount) {
                if(this.model.building.elevator[elevatorCount].elevatorReadyToBoard(this.departFloor, this.targetFloor)) {
                    if(this.model.clock - this.model.building.elevator[elevatorCount].lastBoarded >= 5L) {
                        this.boardPassenger(elevatorCount);
                        boarded = true;
                        this.model.building.elevator[elevatorCount].beamBroken = false;
                    } else {
                        if(this.targetFloor > this.departFloor) {
                            this.model.setButton(this.departFloor, 0, true);
                        } else if(this.targetFloor < this.departFloor) {
                            this.model.setButton(this.departFloor, 1, true);
                        }

                        this.model.building.elevator[elevatorCount].beamBroken = true;
                    }
                } else if(this.targetFloor > this.departFloor) {
                    this.model.setButton(this.departFloor, 0, true);
                } else if(this.targetFloor < this.departFloor) {
                    this.model.setButton(this.departFloor, 1, true);
                }
            }
        } else if(this.status == 2 && this.model.building.elevator[this.onElevator].elevatorReadyToExit(this.targetFloor)) {
            if(this.model.clock - this.model.building.elevator[this.onElevator].lastBoarded >= 5L) {
                this.departPassenger(this.onElevator);
                this.model.building.elevator[this.onElevator].beamBroken = false;
            } else {
                this.model.building.elevator[this.onElevator].beamBroken = true;
            }
        }

    }

    private void boardPassenger(int elevator) {
        if(!this.split | this.split & this.targetFloor != this.finalFloor) {
            this.boardTime = this.model.clock;
            this.onElevator = elevator;
            this.model.building.elevator[elevator].setButton(this.targetFloor, true);
            if(this.targetFloor > this.departFloor) {
                this.model.setButton(this.departFloor, 0, false);
            } else {
                this.model.setButton(this.departFloor, 1, false);
            }

            ++this.model.building.elevator[elevator].passengerCount;
            this.model.building.elevator[elevator].lastBoarded = this.model.clock;
            this.model.building.elevator[elevator].weight += this.weight;
            this.status = 2;
            if(Challenge.logging >= 2) {
                System.out.println("Passenger# " + this.id + " is boarding on: " + this.onElevator + " at t= " + this.boardTime);
            }
        } else {
            this.onElevator = elevator;
            this.model.building.elevator[elevator].setButton(this.targetFloor, true);
            if(this.targetFloor > this.departFloor) {
                this.model.setButton(this.departFloor, 0, false);
            } else {
                this.model.setButton(this.departFloor, 1, false);
            }

            ++this.model.building.elevator[elevator].passengerCount;
            this.model.building.elevator[elevator].lastBoarded = this.model.clock;
            this.model.building.elevator[elevator].weight += this.weight;
            this.status = 2;
            if(Challenge.logging >= 2) {
                System.out.println("Passenger# " + this.id + " is boarding on: " + this.onElevator + " at t= " + this.boardTime);
            }
        }

    }

    private void departPassenger(int elevator) {
        if(!this.split | this.split & this.targetFloor == this.finalFloor) {
            this.departTime = this.model.clock;
            if(Challenge.logging >= 2) {
                System.out.println("Passenger# " + this.id + " is exiting from: " + this.onElevator + " at t=" + this.departTime);
            }

            this.model.building.elevator[this.onElevator].setButton(this.targetFloor, false);
            --this.model.building.elevator[this.onElevator].passengerCount;
            this.model.building.elevator[elevator].lastBoarded = this.model.clock;
            this.model.building.elevator[elevator].weight -= this.weight;
            this.status = 3;
        } else {
            this.model.building.elevator[this.onElevator].setButton(this.targetFloor, false);
            --this.model.building.elevator[this.onElevator].passengerCount;
            this.model.building.elevator[elevator].lastBoarded = this.model.clock;
            this.model.building.elevator[elevator].weight -= this.weight;
            this.status = 1;
            this.departFloor = this.targetFloor;
            this.targetFloor = this.finalFloor;
            if(this.targetFloor > this.departFloor) {
                this.model.setButton(this.departFloor, 0, true);
            } else {
                this.model.setButton(this.departFloor, 1, true);
            }

            if(Challenge.logging >= 2) {
                System.out.println("Passenger# " + this.id + " is boarding on: " + this.onElevator + " at t= " + this.boardTime);
            }
        }

    }
}
