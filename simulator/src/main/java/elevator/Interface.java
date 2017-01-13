//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package elevator;

public class Interface implements IElevators {
    private Model model;

    Interface(Model model) {
        this.model = model;
    }

    @Override
    public boolean getFloorButtonUp(int floor) {
        if(floor < 0 | floor >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds floor in getFloorButtonUp()");
            }

            return false;
        } else {
            return this.model.building.floorButtons[floor][0];
        }
    }

    @Override
    public boolean getFloorButtonDown(int floor) {
        if(floor < 0 | floor >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds floor in getFloorButtonDown()");
            }

            return false;
        } else {
            return this.model.building.floorButtons[floor][1];
        }
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) {
        if(floor < 0 | floor >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds floor in getElevatorButton()");
            }

            return false;
        } else if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorButton()");
            }

            return false;
        } else {
            return this.model.building.elevator[elevatorNumber].buttons[floor];
        }
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorFloor()");
            }

            return 0;
        } else {
            return (int)Math.round(this.model.building.elevator[elevatorNumber].currentPosition / (double)this.model.building.floorHeight);
        }
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) {
        boolean position = false;
        double dPosition = 0.0D;
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorPosition()");
            }

            return 0;
        } else {
            dPosition = this.model.building.elevator[elevatorNumber].currentPosition;
            int position1 = (int)Math.round(dPosition);
            return position1;
        }
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorSpeed()");
            }

            return 0;
        } else {
            return Math.round((float)this.model.building.elevator[elevatorNumber].capacity);
        }
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorSpeed()");
            }

            return 0;
        } else {
            return (int)Math.round(10.0D * this.model.building.elevator[elevatorNumber].currentSpeed);
        }
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorAccel()");
            }

            return 0;
        } else {
            return (int)Math.round(10.0D * this.model.building.elevator[elevatorNumber].accel);
        }
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorDoorStatus()");
            }

            return 0;
        } else {
            return this.model.building.elevator[elevatorNumber].doors;
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target) {
        if(target < 0 | target >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds target in setTarget()");
            }
        } else if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in setTarget()");
            }
        } else if(!this.getServicesFloors(elevatorNumber, target)) {
            if(Challenge.logging >= 1) {
                System.out.println("Target requested is not serviced by elevator.elevator");
            }
        } else {
            this.model.building.elevator[elevatorNumber].target = target;
        }

    }

    @Override
    public int getTarget(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getTarget()");
            }

            return 0;
        } else {
            return this.model.building.elevator[elevatorNumber].target;
        }
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) {
        if(direction < 0 | direction > 2) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds target in setCommittedDirection()");
            }
        } else if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in setCommittedDirection()");
            }
        } else {
            this.model.building.elevator[elevatorNumber].committedDirection = direction;
        }

    }

    @Override
    public int getCommittedDirection(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getCommittedDirection()");
            }

            return 0;
        } else {
            return this.model.building.elevator[elevatorNumber].committedDirection;
        }
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        if(!this.model.splitsAllowed) {
            if(Challenge.logging >= 1) {
                System.out.println("Splits not allowed in setServicesFloors()");
            }
        } else if(floor < 0 | floor >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds target in setServicesFloors()");
            }
        } else if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in setServicesFloors()");
            }
        } else if(floor == 0 & !service) {
            if(Challenge.logging >= 1) {
                System.out.println("Can\'t turn off service to ground floor in setServicesFloors()");
            }
        } else {
            this.model.building.elevator[elevatorNumber].servicesFloors[floor] = service;
            if(Challenge.logging >= 1) {
                System.out.println("Setting elevator.elevator " + elevatorNumber + " setting floor " + floor + " to " + service);
            }
        }

    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) {
        if(floor < 0 | floor >= this.model.building.floors) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds target in setServicesFloors()");
            }

            return false;
        } else if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in setServicesFloors()");
            }

            return false;
        } else {
            return this.model.building.elevator[elevatorNumber].servicesFloors[floor];
        }
    }

    @Override
    public int getElevatorNum() {
        return this.model.getElevators();
    }

    @Override
    public int getFloorNum() {
        return this.model.getBuildingFloors();
    }

    @Override
    public int getFloorHeight() {
        return this.model.getBuildingFloorHeight();
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) {
        if(elevatorNumber < 0 | elevatorNumber >= this.model.building.elevators) {
            if(Challenge.logging >= 1) {
                System.out.println("Out of bounds elevator.elevator in getElevatorWeight()");
            }

            return 0;
        } else {
            return this.model.building.elevator[elevatorNumber].weight;
        }
    }

    @Override
    public void setText(String controllerText) {
    }

    @Override
    public int getLogging() {
        return Challenge.logging;
    }
}
