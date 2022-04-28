package poo;

import filespackage.CustomReadFile;
import filespackage.CustomWriteFile;
import filespackage.Pair;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Vector;

/**
 * @author USUARIO
 */
public class Record {

    private final int MAX_JUGADORES = 10;
    private Jugador jugadores[] = new Jugador[MAX_JUGADORES];
    private int contador = 0;

    /**
     * Ordena el ranking de jugadores con el algoritmo de la burbuja
     */
    public void ordenarRanking() {
        Integer auxPuntuacion;
        String auxNombre;
        int i,j;
        for (i = 0; i < contador ; i++) {
            for(j = 0; j < contador-1; j++) {
                if (jugadores[j+1].getPuntuacion() > jugadores[j].getPuntuacion()) {
                    auxNombre = jugadores[j+1].getNombre();
                    jugadores[j+1].setNombre(jugadores[j].getNombre());
                    jugadores[j].setNombre(auxNombre);
                    auxPuntuacion = jugadores[j+1].getPuntuacion();
                    jugadores[j+1].setPuntuacion(jugadores[j].getPuntuacion());
                    jugadores[j].setPuntuacion(auxPuntuacion);;
                }
            }
        }
    }


    public void setNuevoJugador(Jugador _nuevoJugador) {
        // Da igual los jugadores que haya, siempre hay que insertar el nuevo jugador

        // Si hay menos de 10, incrementamos el contador
        if (this.contador < MAX_JUGADORES) {
            this.jugadores[this.contador] = _nuevoJugador;
            this.contador++;
        }
        // Si ya hay 10 no hay que hacer nada porque arriba ya lo hemos reemplazado
    }

    public void cargarRanking() throws IOException {
        CustomReadFile customReadFile = new CustomReadFile("top.txt");
        Vector<Pair> pares = customReadFile.readJugadores();

        for (int i = 0; i < pares.size(); i++) {
            int puntuacion = (int) pares.get(i).getP();

            String nombre = (String) pares.get(i).getN();
            Jugador jugadorNuevo = new Jugador(nombre, puntuacion);

            setNuevoJugador(jugadorNuevo);
        }
        customReadFile.closeReadFile();
    }

    void escribirRanking(String nombre, int puntuacion) throws IOException {
        CustomWriteFile customWriteFile = new CustomWriteFile("top.txt");
        String variable = puntuacion + " " + nombre + "\n";
        customWriteFile.writeJugadores(variable);
        customWriteFile.closeWriteFile();

    }

    void showRanking() throws IOException {
        int i = 0;
        do{
            System.out.println(this.jugadores[i].getPuntuacion() + "" +this.jugadores[i].getNombre());
            i++;
        } while(i<10&& i< this.contador);
    }



    void showBestPlayer() throws IOException {
        ordenarRanking();

        if (jugadores[0].getPuntuacion() == jugadores[1].getPuntuacion()) {
            System.out.println(" ");
            System.out.println(jugadores[0].getNombre() + " ");
            System.out.print(jugadores[0].getPuntuacion());
            System.out.println(jugadores[1].getNombre());
            System.out.println(jugadores[1].getPuntuacion());
        } else {
            System.out.println(" ");
            System.out.println(jugadores[0].getNombre());
            System.out.println(jugadores[0].getPuntuacion());
        }
    }

    void sobreEscribir() throws IOException {

        ordenarRanking();
        // BufferedWriter bw = new BufferedWriter(new FileWriter("top.txt"));
        //bw.write("");
        //
        // bw.close();
        CustomWriteFile customWriteFilee = new CustomWriteFile("top.txt", false);
        for (int i = 0; i < contador; i++) {


            String variable = jugadores[i].getNombre() + " " + jugadores[i].getPuntuacion() +"\n" ;
            customWriteFilee.writeJugadores(variable);
            customWriteFilee.closeWriteFile();
        }




    }

}


