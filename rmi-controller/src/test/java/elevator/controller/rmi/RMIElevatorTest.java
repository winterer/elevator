package elevator.controller.rmi;

import elevator.IElevators;
import org.junit.Before;
import org.junit.Test;
import sqelevator.IElevator;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by winterer on 18.01.2017.
 */
public class RMIElevatorTest {

    private IElevators mock;
    private RMIElevator elevator;

    @Before
    public void setUp() throws Exception {
        mock = mock(IElevators.class);
        elevator = new RMIElevator(mock);
    }

    @Test
    public void testGetCommittedDirection() throws Exception {
        when(mock.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
        assertEquals(IElevator.ELEVATOR_DIRECTION_UP, elevator.getCommittedDirection(0));
    }

    @Test
    public void testGetSetClockTick() throws Exception {
        elevator.setClockTick(1L);
        assertEquals(1L, elevator.getClockTick());
    }
}