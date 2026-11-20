import java.nio.file.*;
import java.util.stream.*;

// Prints the .class files of each module's bundleLibRuntimeToDirDebug input directory in
// Files.walk order, the same unsorted walk AGP's DirectoryBasedClassFileInput uses to feed D8.
public class WalkOrder {
    public static void main(String[] args) throws Exception {
        for (String module : args) {
            Path intermediates = Paths.get(module, "build", "intermediates");
            try (Stream<Path> dirs = Files.walk(intermediates)) {
                for (Path root : dirs.filter(p -> Files.isDirectory(p) && p.getFileName().toString().equals("bundleLibRuntimeToDirDebug")).collect(Collectors.toList())) {
                    System.out.println("== " + module + " (" + root + ")");
                    try (Stream<Path> files = Files.walk(root)) {
                        files.filter(p -> p.toString().endsWith(".class"))
                                .forEach(p -> System.out.println("  " + root.relativize(p).toString().replace('\\', '/')));
                    }
                }
            }
        }
    }
}
