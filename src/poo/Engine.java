/*

 */
package poo;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Waldemar Stegierski
 */
public class Engine {

    // Constantes
    private final int MAX_COLORES_FACIL = 3;
    private final int MAX_COLORES_SEQ = 4;
    private final int MAX_COLORES_DIFICIL = 3;
    private final int MAX_COLORES_SEQ2 = 15;
    private final int AYUDAS = 0;

    // Resto de atributos de la clase
    Record myRecord = new Record();
    Scanner sc = new Scanner(System.in);
    tColores[] secuenciaColores = new tColores[MAX_COLORES_SEQ];
    int numAyudas = AYUDAS;
    boolean fallo = false;
    boolean ayuda = false;

    /**
     * Controla la ejecucion de las distintas funcionalidades del programa
     *
     * @throws java.io.IOException
     */
    public void start() throws IOException {

        String nombre;
        int op;
        int puntos = 0;


        myRecord.cargarRanking();
        System.out.println("Welcome to Simon dice!\nWhat's your name?");
        nombre = this.sc.nextLine();
        System.out.println("Hello " + nombre + ", press ENTER to start playing");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error - Stop line in start(): " + e);
        }
        Jugador newPlayer = new Jugador(nombre, 0);
        this.myRecord.setNuevoJugador(newPlayer);

        op = menu();
        while (op != 0) {
            switch (op) {
                case 1:
                    puntos = play(Tmodo.FACIL);

                    myRecord.escribirRanking(nombre, puntos);
                    break;

                case 2:
                    puntos = play(Tmodo.DIFICIL);

                    myRecord.escribirRanking(nombre, puntos);
                    break;
                case 3:

                    myRecord.ordenarRanking();
                    myRecord.showRanking();
                    break;

                case 4:
                    myRecord.showBestPlayer();
                    break;
                default:


                    break;
            }

            op = menu();
        }
        // Al finalizar el programa ordenamos el ranking y lo actualizamos en 'top.txt'
        // Blah, blah, blah
        myRecord.sobreEscribir();


    }


    /**
     * Permite jugar una partida de Simon dice
     *
     * @return
     */
    public Integer play(Tmodo _modo) {
        this.fallo = false;
        boolean ayuda = false;
        Integer puntos = 0;
        tColores colorElegido;

        if (_modo == Tmodo.FACIL) {

            generarSecuencia(this.MAX_COLORES_FACIL);
        }
        // Comprobamos si esta jugando en modo dificil para multiplicar x2 los puntos
        if (_modo == Tmodo.DIFICIL) {

            generarSecuencia(this.MAX_COLORES_DIFICIL);

            puntos *= 2;
        }

        // 'k' indica la cantidad de colores que vamos a mostrar segun la ronda
        int k = 3;
        while (k <= this.MAX_COLORES_SEQ && this.fallo == false) {
            System.out.print("Secuencia numero " + (k - 2) + ": ");
            for (int i = 0; i < k; i++) {
                System.out.print(mostrarColor(this.secuenciaColores[i]) + "  ");
            }

            System.out.println("\nMemoriza la secuencia y pulsa ENTER para continuar");
            try {
                System.in.read();
            } catch (IOException e) {
                System.out.println("Error - Stop line in play(): " + e);
            }
            // Limpiamos la consola
            for (int i = 0; i < 50; ++i) {
                System.out.println();
            }

            System.out.println("Introduce la secuencia de " + k + " colores ('X' para la ayuda): ");
//ayuda sistema -8
            int i = 0;
            // Termina cuando se ha acertado la secuencia o cuando se ha fallado
            while (i < k && this.fallo == false) {
                String letra = this.sc.next();
                // Bucle que asegura que la letra es del abecedario
                while (!letra.matches("[A-Za-z]{1}")) {
                    System.out.println("Introduce un caracter valido.");
                    letra = this.sc.next();
                }
                // Si pedimos la ayuda
                if (letra.toUpperCase().charAt(0) == 'X') {
                    usarAyuda(i);
                    if (puntos >= 0) {
                        puntos -= 8;
                    }

                } else {
                    colorElegido = charToColor(letra.toUpperCase().charAt(0));
                    this.fallo = comprobarColor(colorElegido, i);
                    if (this.fallo == false) {
                        puntos += 5;
                        i++;
                    }
                }
            }
            // Comprobamos si ha acertado la secuencia
            if (comprobarSecuencia(k) == true) {
                puntos += 10;
            }
            k++;
        }

        return puntos;
    }

    /**
     * Retorna la opcion elegida
     *
     * @return
     */
    public int menu() {
        int opcion;
        do {
            System.out.print("Elige una opcion para continuar: \n"
                    + "    0 - Salir. \n"
                    + "    1 - Jugar en modo facil. \n"
                    + "    2 - Jugar en modo dificil. \n"
                    + "    3 - Ver 10 mejores jugadores. \n"
                    + "    4 - Ver jugador/es con la puntuación más alta \n"
                    + "Opcion: ");
            opcion = sc.nextInt();
            if (opcion < 0 || opcion > 4) {
                System.out.println("Opcion no valida!");
            }
        } while (opcion < 0 || opcion > 4);
        return opcion;
    }

    /**
     * Genera una secuencia de colores
     *
     * @param _numColores
     */
    public void generarSecuencia(int _numColores) {
        tColores colorGenerado;
        int random;
        for (int i = 0; i < this.MAX_COLORES_SEQ; i++) {
            // Generamos un color dentro del intervalo [0, _numColores]
            random = (int) (Math.random() * _numColores);
            colorGenerado = intToColor(random);
            this.secuenciaColores[i] = colorGenerado;
        }
    }

    /**
     * Comprueba el resultado de cada secuencia
     *
     * @param _index
     * @return
     */
    public boolean comprobarSecuencia(int _index) {
        boolean puntosSecuencia = false;
        if (this.fallo == true) {
            System.out.println("Lo siento, has perdido.");
            puntosSecuencia = false;
        } else if (this.fallo == false && _index == this.MAX_COLORES_SEQ) {
            System.out.println("Has ganado el juego!!!");
            puntosSecuencia = true;
        } else if (this.fallo == false) {
            System.out.println("Enhorabuena, has acertado la secuencia numero " + (_index - 2));
            puntosSecuencia = true;
        }
        return puntosSecuencia;
    }

    /**
     * Permite que escojamos una ayuda
     *
     * @param _index
     * @return
     */
    public boolean usarAyuda(int _index) {
        if (this.numAyudas == 0) {
            System.out.println("Lo siento, no quedan ayudas. Debes acertar los colores restantes sin ayuda.");
            return false;
        } else {
            numAyudas--;

            System.out.println("El siguiente color es el: "
                    + mostrarColor(this.secuenciaColores[_index]) + ". Te quedan " + numAyudas + " ayudas.");
            return true;
        }
    }

    /**
     * Comprueba si el color es correcto retornando si ha habido (o no) fallo
     *
     * @param _color
     * @param _index
     * @return
     */
    public boolean comprobarColor(tColores _color, int _index) {
        return this.secuenciaColores[_index] != _color;
    }

    /**
     * Muestra por pantalla el color
     *
     * @param _color
     * @return
     */
    public String mostrarColor(tColores _color) {
        String color = "";
        if (_color == tColores.ROJO) {
            color = "Rojo";
        } else if (_color == tColores.VERDE) {
            color = "Verde";
        } else if (_color == tColores.AZUL) {
            color = "Azul";
        } else if (_color == tColores.DORADO) {
            color = "Dorado";
        } else if (_color == tColores.BLANCO) {
            color = "Blanco";
        } else if (_color == tColores.MARRON) {
            color = "Marron";
        } else if (_color == tColores.NARANJA) {
            color = "Naranja";
        }

        return color;
    }

    /**
     * Retorna el color correspondiente a la letra
     *
     * @param _color
     * @return
     */
    public tColores charToColor(char _color) {
        if (_color == 'R') {
            return tColores.ROJO;
        } else if (_color == 'V') {
            return tColores.VERDE;
        } else if (_color == 'A') {
            return tColores.AZUL;
        } else if (_color == 'D') {
            return tColores.DORADO;
        } else if (_color == 'B') {
            return tColores.BLANCO;
        } else if (_color == 'M') {
            return tColores.MARRON;
        } else if (_color == 'N') {
            return tColores.NARANJA;
        }
        return null;
    }

    /**
     * Retorna el color correspondiente a la posicion que ocupa
     *
     * @param _numero
     * @return
     */
    public tColores intToColor(int _numero) {
        switch (_numero) {
            case 0:
                return tColores.ROJO;
            case 1:
                return tColores.VERDE;
            case 2:
                return tColores.AZUL;
            case 3:
                return tColores.DORADO;

            case 4:
                return tColores.BLANCO;
            case 5:
                return tColores.MARRON;
            case 6:
                return tColores.NARANJA;

            default:
                return null;
        }
    }


    //1ºarchivo sacar el string, pasar a datos con los pair, array de pares,
    /*
        Este método crea un objeto de tipo CustomReadFile
y llama al método que se encarga de leer de fichero, que como no podía
ser de otra forma se encuentra en la propia clase CustomReadFile (de ahí
que debamos instanciar un objeto de esa clase).
     */
 /*
    void escribirRanking (): Este método crea un objeto de tipo CustomWriteFile
y llama al método que se encarga de escribir en fichero, que como no podía

ser de otra forma se encuentra en la propia clase CustomWriteFile. Especí-
ficamente, este método creará una cadena de tipo String con los jugadores

y sus puntuaciones, respetando las dos columnas. Esta cadena será pasada
por parámetro al método correspondiente en la clase CustomWriteFile
para que así pueda escribir la información en el fichero.
El profesor proporcionará parte del código de las clases CustomReadFile,
CustomWriteFile, ICustomReadFile, ICustomWriteFile y Pair.
     */
}
