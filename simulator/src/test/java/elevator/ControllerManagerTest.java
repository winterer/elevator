package elevator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by winterer on 13.01.2017.
 */
public class ControllerManagerTest {
    private ControllerManager controllerManager;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        ClassLoader classLoader = MyController.class.getClassLoader();
        controllerManager = new ControllerManager(classLoader);
    }

    @org.junit.jupiter.api.Test
    void scan() {
        controllerManager.scan();
        Set<Class<? extends IController>> controllers = controllerManager.getControllers();
        assertEquals(1, controllers.size());
        assertEquals("elevator.elevator.MyController", controllers.iterator().next().getName());
    }

    @org.junit.jupiter.api.Test
    void getControllers() {
        assertNotNull(controllerManager.getControllers());
        assertTrue(controllerManager.getControllers().isEmpty());
    }
}

class MyController implements IController {
    public IElevators api;
    public long tick;
    public int disposeCounter = 0;

    public void setup(IElevators api) {
        this.api = api;
    }

    public void tick(long tick) {
        this.tick = tick;
    }

    public String getText() {
        return "My Controller";
    }

    @Override
    public void dispose() {
        this.disposeCounter++;
    }
}