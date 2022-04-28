package filespackage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author USUARIO
 */
public class CustomReadFile extends FileReader implements ICustomReadFile {
    private final Scanner sc;

    /**
     * Constructora
     *
     * @param _file
     * @throws IOException
     */

    public CustomReadFile(String _file) throws IOException {
        super(new File("src" + File.separator + "datapackage" + File.separator + _file));
        //por que cojones me ha dado fallo con la ruta del writer error de 3 horas!!!!!!
        this.sc = new Scanner(this);
    }
//C:\Users\USUARIO\IdeaProjects\Simon\src\datapackage

    public void closeReadFile() throws IOException {
        try {
            this.close();
        } catch (IOException e) {
            System.out.println("error + closeReadFile():" + e);
        }

    }

    public Vector<Pair> readJugadores() {
        Vector<Pair> myPairs = new Vector<Pair>();
        Integer puntuacion;
        String nombre;

        while (this.sc.hasNextLine()) {
            puntuacion = this.sc.nextInt();
            nombre = this.sc.nextLine();

            myPairs.add(new Pair(puntuacion, nombre)); //crea objetos sin edintificador
        }
        return myPairs;
    }

}
