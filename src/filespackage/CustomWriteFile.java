package filespackage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author USUARIO
 */
public class CustomWriteFile extends FileWriter implements ICustomWriteFile {

    /**
     * Constructora
     *
     * @param _file
     * @throws IOException
     */
    public CustomWriteFile(String _file) throws IOException {
        super(new java.io.File(" . ").getCanonicalPath() + "/src/datapackage/" + _file, true);
        //C:\Users\USUARIO\OneDrive\Documentos\NetBeansProjects\Simondice\src\main\java\datapackage\top.txt
    }

    public CustomWriteFile(String _file, boolean holi) throws IOException {
        super(new java.io.File(" . ").getCanonicalPath() + "/src/datapackage/" + _file, holi);
        //C:\Users\USUARIO\OneDrive\Documentos\NetBeansProjects\Simondice\src\main\java\datapackage\top.txt
    }

    public void closeWriteFile() throws IOException {
        try {
            this.close();
        } catch (IOException e) {

        }

    }

    public void writeJugadores(String _chain) throws IOException {

        this.write(_chain);

    }

}
