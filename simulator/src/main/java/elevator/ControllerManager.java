package elevator;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by winterer on 13.01.2017.
 */
public class ControllerManager {
    private final Set<Class<? extends IController>> controllers = new HashSet<Class<? extends IController>>();
    private final ClassLoader[] classLoaders;

    public ControllerManager(ClassLoader... classLoaders) {
        this.classLoaders = classLoaders;
    }

    public void scan() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addClassLoaders(this.classLoaders)
                .addScanners(new SubTypesScanner())
                .build());

        Set<Class<? extends IController>> classes = reflections.getSubTypesOf(IController.class);
        controllers.clear();
        controllers.addAll(classes);
    }

    public Set<Class<? extends IController>> getControllers() {
        return Collections.unmodifiableSet(controllers);
    }
}
