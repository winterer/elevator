package elevator;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by winterer on 13.01.2017.
 */
public class ControllerUtils {
    public static Collection<URL> getLibraryURLs(Path libraryPath) throws IOException {
        List<URL> jarURLs = new ArrayList<>();
        try (DirectoryStream<Path> jarPaths = Files.newDirectoryStream(libraryPath, "*.jar")) {
            for (Path jarPath : jarPaths) {
                jarURLs.add(jarPath.toUri().toURL());
            }
        }

        return jarURLs;
    }

    public static Collection<URL> getControllerURLs(Path base) throws IOException {
        List<URL> urls = new ArrayList<>();
        Path controllerLibs = base;
        if (Files.exists(controllerLibs) && Files.isDirectory(controllerLibs)) {
            urls.addAll(ControllerUtils.getLibraryURLs(controllerLibs));
        }

        Path controllerClasses = base;
        if (Files.exists(controllerClasses) && Files.isDirectory(controllerClasses)) {
            urls.add(controllerClasses.toUri().toURL());
        }

        return urls;
    }
}
