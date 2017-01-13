//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

import java.text.NumberFormat;
import java.util.Iterator;

class Engine {
    private long clock;
    private long stageStartTime;
    private long repeatStartTime;
    private long repeatTime;
    private long nextRepeatTime;
    private long nextStageTime;
    private long stageTime;
    private long totalTime;
    private int currentStage;
    private int currentRepeat;
    private int repeats;
    private int stages;
    private int passengerArrivalRateOther;
    private int passengerArrivalRateGround;
    private int passengerExitRateOther;
    private int floors;
    private int elevators;
    String[] simClocks = new String[3];
    private NumberFormat nf1;
    private NumberFormat nf2;
    private Model model;
    private IController controller;
    double averageWait;
    double averageTravel;
    int passengersProcessed;
    long maxWait;
    long maxTravel;

    Engine(Model model, IController controller) {
        this.model = model;
        this.controller = controller;
    }

    void setupSim() {
        this.controller.setup(this.model.controllerInterface);
        this.stages = this.model.stageNum;
        this.currentStage = 0;
        this.currentRepeat = 0;
        this.stageStartTime = 0L;
        this.repeatStartTime = 0L;
        this.repeats = this.model.repeat;
        this.repeatTime = 0L;
        this.stageTime = 0L;
        this.totalTime = 0L;
        this.clock = 0L;
        this.model.setClock(this.clock);
        Passenger.count = 0;
        this.stageTime = (long)(this.model.getStageTime(0) * 600);
        this.nextStageTime = (long)(this.model.getStageTime(0) * 600);

        for(int i = 0; i < this.model.getStageNum(); ++i) {
            this.repeatTime += (long)(this.model.getStageTime(i) * 600);
        }

        this.nextRepeatTime = this.repeatTime;
        this.totalTime = this.repeatTime * (long)this.repeats;
        this.passengerArrivalRateOther = this.model.stages[0].otherArrival;
        this.passengerArrivalRateGround = this.model.stages[0].groundArrival;
        this.passengerExitRateOther = this.model.stages[0].otherExit;
        this.nf1 = NumberFormat.getNumberInstance();
        this.nf1.setMaximumFractionDigits(0);
        this.nf1.setMinimumIntegerDigits(1);
        this.nf1.setMaximumIntegerDigits(1);
        this.nf2 = NumberFormat.getNumberInstance();
        this.nf2.setMaximumFractionDigits(0);
        this.nf2.setMinimumIntegerDigits(2);
        this.updateClocks();
        this.floors = this.model.building.floors;
        this.elevators = this.model.building.elevators;
    }

    boolean tickSim() {
        ++this.clock;
        this.updateClocks();
        this.updatePassengers();

        for(int i = 0; i < this.elevators; ++i) {
            this.model.building.elevator[i].tick();
        }

        this.controller.tick(this.clock);
        return this.clock >= this.totalTime;
    }

    void clearPassengers() {
        if(!this.model.passengers.isEmpty()) {
            for(int i = this.model.passengers.size() - 1; i >= 0; --i) {
                Passenger p = (Passenger)this.model.passengers.get(i);
                if(p.boardTime == 0L) {
                    p.boardTime = (long)Math.round(Math.max((float)(this.clock - p.createTime), (float)this.averageWait)) + p.createTime;
                }

                p.departTime = (long)((int)Math.round(Math.max((double)((float)(this.clock - p.boardTime)), this.averageTravel - this.averageWait))) + p.boardTime;
                if(Challenge.logging >= 2) {
                    System.out.println("Remaining passenger " + p.id + " removal : " + p.createTime + ", " + p.boardTime + ", " + p.departTime);
                }

                this.trackResults(p);
                this.model.passengers.remove(p);
            }
        }

        this.model.clearPassengers();
    }

    void resetSim() {
        boolean i = false;
        this.clock = 0L;
        this.model.setClock(this.clock);
        this.currentStage = 0;
        this.currentRepeat = 0;
        this.stageStartTime = 0L;
        this.repeatStartTime = 0L;
        this.repeatTime = 0L;
        this.nextRepeatTime = 0L;
        this.nextStageTime = (long)(this.model.getStageTime(0) * 600);
        this.stageTime = (long)(this.model.getStageTime(0) * 600);

        int var2;
        for(var2 = 0; var2 < this.model.getStageNum(); ++var2) {
            this.repeatTime += (long)(this.model.getStageTime(var2) * 600);
        }

        this.nextRepeatTime = this.repeatTime;
        this.totalTime = this.repeatTime * (long)this.repeats;
        this.passengerArrivalRateOther = this.model.stages[0].otherArrival;
        this.passengerArrivalRateGround = this.model.stages[0].groundArrival;
        this.passengerExitRateOther = this.model.stages[0].otherExit;
        this.averageWait = 0.0D;
        this.averageTravel = 0.0D;
        this.passengersProcessed = 0;
        this.maxWait = 0L;
        this.maxTravel = 0L;

        for(var2 = 0; var2 < this.elevators; ++var2) {
            this.model.building.elevator[var2].resetElevators();
        }

    }

    private void updateClocks() {
        if(this.clock != this.totalTime) {
            if(this.clock == this.nextRepeatTime) {
                ++this.currentRepeat;
                this.currentStage = 0;
                this.repeatStartTime = this.clock;
                this.stageStartTime = this.clock;
                this.stageTime = (long)this.model.getStageTime(this.currentStage) * 600L;
                this.nextStageTime = this.clock + this.stageTime;
                this.nextRepeatTime = this.clock + this.repeatTime;
                this.passengerArrivalRateOther = this.model.stages[this.currentStage].otherArrival;
                this.passengerArrivalRateGround = this.model.stages[this.currentStage].groundArrival;
                this.passengerExitRateOther = this.model.stages[this.currentStage].otherExit;
            } else if(this.clock == this.nextStageTime) {
                ++this.currentStage;
                this.stageTime = 0L;
                this.stageStartTime = this.clock;
                this.nextStageTime = this.clock + (long)(this.model.getStageTime(this.currentStage) * 600);
                this.passengerArrivalRateOther = this.model.stages[this.currentStage].otherArrival;
                this.passengerArrivalRateGround = this.model.stages[this.currentStage].groundArrival;
                this.passengerExitRateOther = this.model.stages[this.currentStage].otherExit;
            }
        }

        this.model.setClock(this.clock);
    }

    void updateClockText() {
        this.simClocks[0] = " Clock: " + this.nf2.format(this.clock / 36000L) + ":" + this.nf2.format(this.clock / 600L % 60L) + ":" + this.nf2.format(this.clock / 10L % 60L) + "." + this.nf1.format(this.clock % 10L) + " ";
        this.simClocks[1] = " Repeat: " + (this.currentRepeat + 1) + " of " + this.model.getRepeat() + " | " + this.nf2.format((this.clock - this.repeatStartTime) / 36000L) + ":" + this.nf2.format((this.clock - this.repeatStartTime) / 600L % 60L) + ":" + this.nf2.format((this.clock - this.repeatStartTime) / 10L % 60L) + "." + this.nf1.format((this.clock - this.repeatStartTime) % 10L) + " ";
        this.simClocks[2] = " Stage: " + (this.currentStage + 1) + " of " + this.model.getStageNum() + " | " + this.nf2.format((this.clock - this.stageStartTime) / 36000L) + ":" + this.nf2.format((this.clock - this.stageStartTime) / 600L % 60L) + ":" + this.nf2.format((this.clock - this.stageStartTime) / 10L % 60L) + "." + this.nf1.format((this.clock - this.stageStartTime) % 10L) + " ";
    }

    private void updatePassengers() {
        boolean targetFloor = false;
        double arrivalRate = 0.0D;
        boolean exitRate = false;
        boolean splitDistance = false;
        int splitFloor = 0;
        int p;
        if(!this.model.passengers.isEmpty()) {
            for(p = this.model.passengers.size() - 1; p >= 0; --p) {
                if(((Passenger)this.model.passengers.get(p)).status == 3) {
                    if(Challenge.logging >= 2) {
                        System.out.println("Removing passenger at " + this.clock);
                    }

                    this.trackResults((Passenger)this.model.passengers.get(p));
                    this.model.passengers.remove(p);
                }
            }
        }

        for(p = 0; p < this.floors; ++p) {
            arrivalRate = 0.0D;
            int var15;
            if(p == 0) {
                if(this.passengerArrivalRateGround > 0) {
                    arrivalRate = 1.0D / (double)(this.passengerArrivalRateGround * 10);
                }

                var15 = 0;
            } else {
                if(this.passengerArrivalRateOther > 0) {
                    arrivalRate = 1.0D / (double)(this.passengerArrivalRateOther * 10);
                }

                var15 = this.passengerExitRateOther;
            }

            if(arrivalRate > 0.0D & Math.random() <= arrivalRate) {
                int var14;
                if(p == 0) {
                    var14 = (int)(Math.random() * (double)(this.floors - 1)) + 1;
                } else if(Math.random() < (double)var15 / 100.0D) {
                    var14 = 0;
                } else if(Math.random() < (double)(this.floors - p - 1) / (double)(this.floors - 2)) {
                    var14 = (int)(Math.random() * (double)(this.floors - p - 1)) + p + 1;
                } else {
                    var14 = (int)(Math.random() * (double)(p - 1)) + 1;
                }

                boolean foundElevator = false;
                boolean foundSplit = false;
                boolean foundTarget = false;
                boolean foundDepart = false;
                int elevatorTest = 0;

                for(boolean floorTest = false; !foundElevator & elevatorTest < this.elevators; ++elevatorTest) {
                    if(this.model.building.elevator[elevatorTest].servicesFloors[p] & this.model.building.elevator[elevatorTest].servicesFloors[var14]) {
                        foundElevator = true;
                    }
                }

                if(!foundElevator) {
                    int var16 = this.floors * 2;
                    foundSplit = false;

                    for(int var19 = 0; var19 < this.floors; ++var19) {
                        foundTarget = false;
                        foundDepart = false;

                        for(elevatorTest = 0; elevatorTest < this.elevators; ++elevatorTest) {
                            if(this.model.building.elevator[elevatorTest].servicesFloors[p] & this.model.building.elevator[elevatorTest].servicesFloors[var19]) {
                                foundDepart = true;
                            }

                            if(this.model.building.elevator[elevatorTest].servicesFloors[var14] & this.model.building.elevator[elevatorTest].servicesFloors[var19]) {
                                foundTarget = true;
                            }
                        }

                        if(foundTarget & foundDepart & Math.abs(p - var19) + Math.abs(var19 - var14) < var16) {
                            splitFloor = var19;
                            var16 = Math.abs(p - var19) + Math.abs(var19 - var14);
                            foundSplit = true;
                        }
                    }
                }

                if(foundSplit) {
                    this.model.passengers.add(new Passenger(this.model, p, splitFloor, true, var14));
                } else {
                    this.model.passengers.add(new Passenger(this.model, p, var14, false, 0));
                }

                if(Challenge.logging >= 2) {
                    System.out.println("Adding passenger leaving from " + p + " going to " + var14 + " at t=" + this.clock);
                }
            }
        }

        for(p = 0; p < this.elevators; ++p) {
            this.model.building.elevator[p].beamBroken = false;
        }

        if(!this.model.passengers.isEmpty()) {
            Iterator var17 = this.model.passengers.iterator();

            while(var17.hasNext()) {
                Passenger var18 = (Passenger)var17.next();
                var18.tick();
            }
        }

    }

    private void trackResults(Passenger p) {
        ++this.passengersProcessed;
        this.maxWait = Math.max(this.maxWait, p.boardTime - p.createTime);
        this.maxTravel = Math.max(this.maxTravel, p.departTime - p.createTime);
        this.averageWait = this.averageWait * (((double)this.passengersProcessed - 1.0D) / (double)this.passengersProcessed) + (double)(p.boardTime - p.createTime) / (double)this.passengersProcessed;
        this.averageTravel = this.averageTravel * (((double)this.passengersProcessed - 1.0D) / (double)this.passengersProcessed) + (double)(p.departTime - p.createTime) / (double)this.passengersProcessed;
    }
}
