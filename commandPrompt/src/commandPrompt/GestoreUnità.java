/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package commandPrompt;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Nizar
 */
public class GestoreUnità implements Serializable{
    //array di volumi (es. chiavette, dischi, SSD, HDD)
    private final Volume[] unità;
    //ora e data registrata
    private LocalDateTime tempo;
    //numero di comandi svolti chehanno modificato delle cartelle o dei file
    private int operazioniSvolte;
    
    public GestoreUnità() {
        unità = new Volume[10];
        this.unità[0] = new Volume("C:");
        this.unità[1] = new Volume("E:");
        this.unità[2] = new Volume("F:");
        this.unità[3] = new Volume("K:");
        operazioniSvolte = 0;
    }
    
    public void run(){
        Scanner scan = new Scanner (System.in);
        int unitàAttuale = 0;
        while (true){
            System.out.print(unità[unitàAttuale].getPercorso() + ' ');
            String input[]= scan.nextLine().trim().replaceAll("\\s+", " ").split(" ");
            //leggo la stringa di input, rimuovo gli eventuali spazi che si trovano prima e dopo la stringa con trim,
            //rimpiazzo tutti gli eventuali gruppi di spazi, rappresentati da \\s+, con un unico spazio ed infine divido l'input.
            boolean flag = true; //questo flag serve per sapere se è stata svolta un'operazione
            if (input[0].equalsIgnoreCase("exit")){
                break;
            } else if (input[0].toUpperCase().startsWith("CD") || input[0].toUpperCase().startsWith("CHDIR")){
                if (input[0].toUpperCase().startsWith("CD")){
                    try{
                        if (input[0].toUpperCase().startsWith("CD..")){
                            unità[unitàAttuale].changeDirectory(input[0].substring(2));
                        } else if (input[0].length() == 2)
                            unità[unitàAttuale].changeDirectory(input[1]);
                    }catch(ArrayIndexOutOfBoundsException exception){
                        System.out.println(unità[unitàAttuale].getPercorso().substring(0, unità[unitàAttuale].getPercorso().length() - 1) + "\n");
                    }
                } else{
                    try{
                        if (input[0].toUpperCase().startsWith("CHDIR..")){
                            unità[unitàAttuale].changeDirectory(input[0].substring(5));
                        } else if (input[0].length() == 5)
                            unità[unitàAttuale].changeDirectory(input[1]);
                    }catch(ArrayIndexOutOfBoundsException exception){
                        System.out.println(unità[unitàAttuale].getPercorso().replaceFirst(">", "") + "\n");
                    }
                }
            } else{
                switch(input[0].toUpperCase()) {
                    case "C:":
                        unitàAttuale = 0;
                        break;
                    case "E:":
                        unitàAttuale = 1;
                        break;
                    case "F:":
                        unitàAttuale = 2;
                        break;
                    case "K:":
                        unitàAttuale = 3;
                        break;
                        
                    case "HELP":
                        flag = false;
                        try{
                            switch (input[1].toUpperCase()){
                                case "DIR": System.out.println("Visualizza un elenco di file e sottodirectory in una directory.");
                                    break;
                                case "MD": System.out.println("Crea una directory.\n"
                                        + "Sintassi: MD percorso/nome");
                                    break;
                                case "MKDIR": System.out.println("Crea una directory.\n"
                                        + "Sintassi: MKDIR percorso/nome");
                                    break;
                                case "CD": System.out.println("Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n"
                                        + "Sintassi: CD percorso/nome");
                                    break;
                                case "CHDIR": System.out.println("Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n"
                                        + "Sintassi: CHDIR percorso/nome");
                                    break;
                                case "DATE": System.out.println("Visualizza la data.\n"
                                        + "Sintassi: DATE");
                                    break;
                                case "TIME": System.out.println("Visualizza l'ora.\n"
                                        + "Sintassi: TIME");
                                    break;
                                case "EXIT": System.out.println("Termina il programma.\n"
                                        + "Sintassi: EXIT");
                                    break;
                                case "RD": System.out.println("Rimuove una directory.\n"
                                        + "Sintassi: RD percorso/nome");
                                    break;
                                case "RMDIR": System.out.println("Rimuove una directory.\n"
                                        + "Sintassi: RMDIR percorso/nome");
                                    break;
                                case "REN": System.out.println("Rinomina un file.\n"
                                        + "Sintassi: REN percorso/nome nuovoNome");
                                    break;
                                case "RENAME": System.out.println("Rinomina un file.\n"
                                        + "Sintassi: RENAME percorso/nome nuovoNome");
                                    break;
                                case "TOUCH": System.out.println("Crea uno o più file.\n"
                                        + "Sintassi: TOUCH percorso/nome1 percorso/nome2 percorso/nomeN ...");
                                    break;
                                case "TREE": System.out.println("Visualizza graficamente la struttura di directory di un'unità o percorso.\n"
                                        + "Sintassi: TREE");
                                    break;
                                case "PRINT": System.out.println("Visualizza il contenuto di un file.\n"
                                        + "Sintassi: PRINT percorso/nome");
                                    break;
                                case "WRITE": System.out.println("Scrivi il testo di un file.\n"
                                        + "Sintassi: WRITE percorso/nome testo");
                                    break;
                                default: System.out.println("\"" + input[1] + "\"" + " non è riconosciuto come comando.");
                                    break;
                            }
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println(
                                    "<NomeUnità>    Cambia unità (C:, E:, F: e K:)\n" +
                                    "CD             Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n" + 
                                    "CHDIR          Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n" +
                                    "DATE           Visualizza la data.\n" +
                                    "DIR            Visualizza un elenco di file e sottodirectory in una directory.\n" +
                                    "EXIT           Termina il programma.\n" +
                                    "HELP           Fornisce informazioni della Guida per i comandi.\n" +
                                    "MKDIR          Crea una directory.\n" +
                                    "MD             Crea una directory.\n" +
                                    "PRINT          Visualizza il contenuto di un file.\n" +
                                    "RD             Rimuove una directory.\n" +
                                    "REN            Rinomina un file.\n" +
                                    "RENAME         Rinomina un file.\n" +
                                    "RMDIR          Rimuove una directory.\n" +
                                    "TIME           Visualizza l'ora.\n" +
                                    "TOUCH          Crea uno o più file.\n" +
                                    "TREE           Visualizza graficamente la struttura di directory di un'unità o percorso.\n" +
                                    "WRITE          Scrivi il testo di un file.");
                        }
                        break;
                        
                    case "DIR":
                        System.out.println(unità[unitàAttuale].showDirectory());
                        break;
                        
                    case "MD": case "MKDIR":
                        try{
                            unità[unitàAttuale].makeDirectory(input[1]);
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println("Sintassi del comando errata.");
                        }
                        break;
                        
                    case "TOUCH":
                        try{
                            unità[unitàAttuale].touch(input);
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println("Sintassi del comando errata.");
                        }
                        break;
                        
                    case "RD": case "RMDIR": 
                        try{
                            unità[unitàAttuale].removeDirectory(input[1]);
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println("Sintassi del comando errata.");
                        }
                        break;
                        
                    case "REN": case "RENAME":
                        try{
                            unità[unitàAttuale].rename(input[1], input[2]);
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println("Sintassi del comando errata.");
                        }
                        break;
                    
                    case "DATE":
                        tempo = LocalDateTime.now();
                        //utilizzo la classe wrapper String in modo da poter stampare numeri ad almeno due cifre (es. 4 -> 04 )
                        System.out.println("Data corrente: " + String.format("%02d", tempo.getDayOfMonth()) + "/" +
                                String.format("%02d", tempo.getMonthValue())+ "/" + tempo.getYear());
                        break;
                        
                    case "WRITE":
                        try{
                            unità[unitàAttuale].write(input[1], input);
                        }catch(ArrayIndexOutOfBoundsException exception){
                            System.out.println("Sintassi del comando errata.");
                        }
                        break;
                    
                    case "PRINT":
                        System.out.println(unità[unitàAttuale].print(input[1]));
                        break;
                        
                    case "TIME":
                        tempo = LocalDateTime.now();
                        //utilizzo la classe wrapper String in modo da poter stampare numeri ad almeno due cifre (es. 4 -> 04 )
                        System.out.println("Ora corrente: " + String.format("%02d", tempo.getHour()) + ":" +
                                String.format("%02d", tempo.getMinute())+ ":" +
                                String.format("%02d", tempo.getSecond()));
                        break;
                    
                    case "TREE":
                        System.out.println(unità[unitàAttuale].tree());
                        break;
				
                    default:
                        System.out.println("\"" + input[0] + "\"" + " non è riconosciuto come comando.");
                        flag = false;
                        break;
                }
            }
            if (flag) operazioniSvolte++;
        }
    }
    
    public boolean modificato(){
        return operazioniSvolte != 0;
    }
    
}

