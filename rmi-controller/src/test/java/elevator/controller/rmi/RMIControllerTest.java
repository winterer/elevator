package elevator.controller.rmi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by winterer on 13.01.2017.
 */
class RMIControllerTest {
    private RMIController rmiController;

    @BeforeEach
    void setUp() {
        rmiController = new RMIController();
    }

    @AfterEach
    void tearDown() {
        rmiController.dispose();
    }

    @Test
    void testSetup() throws Exception {
        rmiController.setup(null);

        IElevator elevator1 = (IElevator) Naming.lookup("rmi://localhost:1099/ElevatorSim");
        assertNotNull(elevator1);

        Registry registry = LocateRegistry.getRegistry(1099);
        IElevator elevator = (IElevator) registry.lookup("ElevatorSim");

        assertNotNull(elevator);
    }
}