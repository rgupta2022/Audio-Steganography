import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * A custom file filter for audio files with the ".au" extension.
 */
public class AudioFilter extends FileFilter {

    /**
     * Determines whether the given file is accepted by the filter.
     *
     * @param file The file to be checked.
     * @return `true` if the file is a directory or has the ".au" extension, `false` otherwise.
     */
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".au");
    }

    /**
     * Returns the description of the file filter.
     *
     * @return The description of the file filter, in this case, ".au File (*.au)".
     */
    public String getDescription() {
        return ".au File (*.au)";
    }
}
