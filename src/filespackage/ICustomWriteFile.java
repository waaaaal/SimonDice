/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package filespackage;

import java.io.IOException;

/**
 * @author USUARIO
 */
public interface ICustomWriteFile {


    void closeWriteFile() throws IOException;

    void writeJugadores(String _chain) throws IOException;


}
