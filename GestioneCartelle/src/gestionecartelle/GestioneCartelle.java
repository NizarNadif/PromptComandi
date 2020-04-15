/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionecartelle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 *La classe GestoreUnità gestisce tutti i tipi di interazione che può
 * effettuare l'utente mentre utilizza la console, la classe Volume contiene tutti i
 * metodi eseguibili (questi vengono chiamati nella classe GestoreUnità).
 * E' possibili salvare/caricare tutti i progressi.
 * @author Nizar
 */
public class GestioneCartelle {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GestoreUnità g;
        Scanner scan = new Scanner (System.in);
        //Deserializzazione dell'oggetto che serializzo in fondo al main
        ObjectInputStream in;
        //se il file gestori.bin non esiste, viene lanciata l'eccezione
        //e viene creato un nuovo GestoreUnità
        try {
            in = new ObjectInputStream(new FileInputStream("gestori.bin"));
            GestoreUnità temp = (GestoreUnità)in.readObject();
            in.close();
            System.out.print("Desideri caricare i salvataggi effettuati(S/N)? ");
            if (scan.nextLine().toUpperCase().startsWith("S"))
                g = temp;
            else g = new GestoreUnità();
        } catch (FileNotFoundException | InvalidClassException ex) {
            //InvalidClassException viene lanciata quando per esempio lanciamo 2 volte lo stesso programma
            //FileNotFoundException viene lanviata quando non esiste il file gestori.bin
            g = new GestoreUnità();
        }
        
        System.out.println("Digitare help per visualizzare i comandi implementati.\n"
                + "Digitare help nomeComando per visualizzare la sintassi del comando.");
        g.run();
        
        if (g.modificato()){
            System.out.print("Desideri salvare ciò che è stato effettuato(S/N)? ");
            if (scan.nextLine().toUpperCase().startsWith("S")){
                //Serializzazione
                ObjectOutputStream out;
                out = new ObjectOutputStream(new FileOutputStream("gestori.bin"));
                out.writeObject(g);
                out.close();
            } else {
                //creo un oggetto File che si riferisce al file "gestori.bin" e lo elimino
                File f = new File("gestori.bin");
                f.delete();
            }
        }
    }
}
