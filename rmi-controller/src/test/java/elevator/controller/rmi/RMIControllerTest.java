package elevator.controller.rmi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.assertNotNull;

/**
 * Created by winterer on 13.01.2017.
 */
public class RMIControllerTest {
    private RMIController rmiController;

    @Before
    public void setUp() {
        rmiController = new RMIController();
    }

    @After
    public void tearDown() {
        rmiController.dispose();
    }

    @Test
    public void testSetup() throws Exception {
        rmiController.setup(null);

        IElevator elevator1 = (IElevator) Naming.lookup("rmi://localhost:1099/ElevatorSim");
        assertNotNull(elevator1);

        Registry registry = LocateRegistry.getRegistry(1099);
        IElevator elevator = (IElevator) registry.lookup("ElevatorSim");

        assertNotNull(elevator);
    }
}