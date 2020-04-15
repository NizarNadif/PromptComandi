/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package gestionecartelle;

import java.io.Serializable;

/**
 *
 * @author Nizar
 */
public class Volume implements Serializable{
    private final Albero albero;
    private NodoAlbero attuale;
    private Pila percorso;
    
    public Volume(String root) {
        albero = new Albero();
        albero.inserisci(root, root);
        percorso = new Pila();
        percorso.push(root);
        percorso.push("\\>");
        attuale = albero.getRoot();
    }
    
    public String help (String arg){
        switch (arg.toUpperCase()){
            case "DIR": return "Visualizza un elenco di file e sottodirectory in una directory.";
            case "MD": return "Crea una directory.\n"
                    + "Sintassi: MD percorso/nome";
            case "MKDIR": return "Crea una directory.\n"
                    + "Sintassi: MKDIR percorso/nome";
            case "CD": return "Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n"
                    + "Sintassi: CD percorso/nome";
            case "CHDIR": return "Visualizza il nome della directory corrente o consente di passare a un'altra directory.\n"
                    + "Sintassi: CHDIR percorso/nome";
            case "DATE": return "Visualizza la data.\n"
                    + "Sintassi: DATE";
            case "TIME": return "Visualizza l'ora.\n"
                    + "Sintassi: TIME";
            case "EXIT": return "Termina il programma.\n"
                    + "Sintassi: EXIT";
            case "RD": return "Rimuove una directory.\n"
                    + "Sintassi: RD percorso/nome";
            case "RMDIR": return "Rimuove una directory.\n"
                    + "Sintassi: RMDIR percorso/nome";
            case "REN": return "Rinomina un file.\n"
                    + "Sintassi: REN percorso/nome nuovoNome";
            case "RENAME": return "Rinomina un file.\n"
                    + "Sintassi: RENAME percorso/nome nuovoNome";
            case "TOUCH": return "Crea uno o più file.\n"
                    + "Sintassi: TOUCH percorso/nome1 percorso/nome2 percorso/nomeN ...";
            case "TREE": return "Visualizza graficamente la struttura di directory di un'unità o percorso.\n"
                    + "Sintassi: TREE";
            case "PRINT": return "Visualizza il contenuto di un file.\n"
                    + "Sintassi: PRINT percorso/nome";
            case "WRITE": return "Scrivi il testo di un file.\n"
                    + "Sintassi: WRITE percorso/nome testo";
            default: break;
        }
        return "\"" + arg + "\"" + " non è riconosciuto come comando.";
    }
    public String showDirectory(){
        Iteratore iteratore = new Iteratore(attuale);
        String s= attuale.toString() + "\n";
        s = s.replaceFirst(attuale.getNome(), ".");
        s += iteratore.nextFiglio().toString() + "\n";
        s = s.replaceFirst(attuale.getNome(), "..");
        Integer d, f;
        for (d=0, f=0; iteratore.hasNext();){
            if (iteratore.getAttuale().getTipo().equalsIgnoreCase("directory")) d++;
            else f++;
            s += iteratore.nextFratello().toString() + "\n";
        }
        for (int k = 0; k < 16 - f.toString().length(); k++)
            s += ' ';
        s += f + " File\n";
        for (int k = 0; k < 16 - d.toString().length(); k++)
            s += ' ';
        s += d + " Directory";
        return s;
    }
    
    public void makeDirectory(String percorso){
        //divido le destinazioni con gli slash (back e front)
        String[] temp = percorso.split("\\\\");
        String[][] cartelle = new String[temp.length][];
        for (int i=0; i<cartelle.length; i++){
            cartelle[i] = temp[i].split("/");
        }
        
        NodoAlbero posizione = attuale;
        for (String[] cart : cartelle){
            for (String c: cart){
                if (c.matches("")){
                    System.out.println("Sintassi del comando errata.");
                    return;
                }
                if (findDirectory(posizione, c) != null){
                    System.out.println("Sottodirectory o file " + c + " già esistente.");
                    return;
                }
                albero.inserisci(posizione, c, "directory");
                posizione = findDirectory(posizione, c);
            }
        }
    }
    
    public void touch(String[] percorsi){
        for (int i = 1; i < percorsi.length; i++){
            //divido le destinazioni con gli slash (back e front)
            String[] temp = percorsi[i].split("\\\\");
            String[][] cartelle = new String[temp.length][];
            for (int j=0; j<cartelle.length; j++){
                cartelle[j] = temp[j].split("/");
            }
            
            String nuovoFile = cartelle[cartelle.length - 1][cartelle[cartelle.length - 1].length - 1];
            cartelle[cartelle.length - 1][cartelle[cartelle.length - 1].length - 1] = null;
            
            NodoAlbero posizione = attuale;
            for (String[] cart : cartelle){
                for (String c: cart){
                    if (c != null){
                        if (c.equals(".."))
                            posizione = posizione.getPadre();
                        else posizione = findDirectory(posizione, c);  //cerco il nodo richiesto
                        if (posizione == null){
                            System.out.println("Impossibile trovare il percorso specificato.");
                            return;
                        }
                    }
                }
            }
            
            if (findDirectory(posizione, nuovoFile) != null){
                System.out.println("Sottodirectory o file " + nuovoFile + " già esistente.");
                return;
            }
            albero.inserisci(posizione, nuovoFile, "file");
            
            
        }
    }
    
    public void write(String percorso, String[] dati){
        String[] temp = percorso.split("\\\\");
        String[][] cartelle = new String[temp.length][];
        for (int i=0; i<cartelle.length; i++){
            cartelle[i] = temp[i].split("/");
        }
        
        NodoAlbero posizione = attuale;
        for (String[] cart : cartelle){
            for (String c: cart){
                if (c.equals(".."))
                    posizione = posizione.getPadre();
                else posizione = findDirectory(posizione, c);  //cerco il nodo richiesto
                if (posizione == null){
                    System.out.println("Impossibile trovare il percorso specificato.");
                    return;
                }
            }
        }
        if (posizione.getTipo().equalsIgnoreCase("directory")){
            System.out.println("Impossibile trovare il percorso specificato.");
            return;
        }
        String s = "";
        for (int i=2; i < dati.length; i++)
            s += dati[i] + " ";
        posizione.setDati(s);
    }
    
    public String print (String percorso){
        String[] temp = percorso.split("\\\\");
        String[][] cartelle = new String[temp.length][];
        for (int i=0; i<cartelle.length; i++){
            cartelle[i] = temp[i].split("/");
        }
        
        NodoAlbero posizione = attuale;
        for (String[] cart : cartelle){
            for (String c: cart){
                if (c.equals(".."))
                    posizione = posizione.getPadre();
                else posizione = findDirectory(posizione, c);  //cerco il nodo richiesto
                if (posizione == null){
                    return "Impossibile trovare il percorso specificato.";
                }
            }
        }
        if (posizione.getTipo().equalsIgnoreCase("directory")){
            return "Impossibile trovare il percorso specificato.";
        }
        return posizione.getDati();
    }
    
    public boolean changeDirectory(String percorso){
        /*  Faccio in modo che se nell'input dobbiamo raggiungere una cartella annidata
        in altre cartelle, divido le destinazioni con gli slash (back e front) e mi sposto una cartella per volta
        per esempio: cd Users\Nizar/Desktop --> cd Users + cd Nizar + cd Desktop   */
        String[] temp = percorso.split("\\\\"); //divido le destinazioni
        String[][] destinazioni = new String[temp.length][];
        for (int i=0; i<destinazioni.length; i++){
            destinazioni[i] = temp[i].split("/");
        }
        
        for (String[] dest : destinazioni){
            for (String d : dest){
                if(d.matches(""));
                else if (d.matches("..")){  //se l'input è .., mi sposto nella cartella padre
                    NodoAlbero padre = attuale.getPadre();
                    if (padre != null){
                        //es: C:\Users\Nizar\Desktop> --> C:\Users\Nizar>
                        attuale = padre;
                        this.percorso.pop(); //tolgo >
                        this.percorso.pop(); //tolgo Desktop
                        this.percorso.pop(); //tolgo \
                        this.percorso.push(">"); //aggiungo >
                    }
                }
                else{
                    NodoAlbero trovato = findDirectory(attuale, d);  //cerco il nodo richiesto fra i suoi figli diretti
                    if (trovato == null){
                        System.out.println("Impossibile trovare il percorso specificato.");
                        return false;
                    } else if (!trovato.getTipo().equalsIgnoreCase("directory")){
                        //se è un file (quindi non è una directory) mi fermo
                        System.out.println("Nome di directory non valido.");
                        return false;
                    } else { //Se ho trovato il figlio mi sposto in quest'ultimo
                        //es: C:\Users\Nizar> --> C:\Users\Nizar\Desktop>
                        attuale = trovato;
                        this.percorso.pop(); //tolgo >
                        this.percorso.push("\\"); //aggiungo \
                        this.percorso.push(attuale.getNome()); //aggiungo Desktop
                        this.percorso.push(">"); //aggiungo >
                    }
                }
            }
        }
        return true;
    }
    
    public void rename (String nomeOriginale, String nomeNuovo){
        String[] temp = nomeOriginale.split("\\\\");
        String[][] cartelle = new String[temp.length][];
        for (int i=0; i<cartelle.length; i++){
            cartelle[i] = temp[i].split("/");
        }
        
        NodoAlbero posizione = attuale;
        for (String[] cart : cartelle){
            for (String c: cart){
                if (c.equals(".."))
                    posizione = posizione.getPadre();
                else posizione = findDirectory(posizione, c);  //cerco il nodo richiesto
                if (posizione == null){
                    System.out.println("Impossibile trovare il percorso specificato.");
                    return;
                }
            }
        }
        
        if (findDirectory(posizione.getPadre(), nomeNuovo) != null){
            System.out.println("Sottodirectory o file " + nomeNuovo + " già esistente.");
            return;
        }
        posizione.setNome(nomeNuovo);
        
    }
    
    /**
     *Cerca una sottocartella
     * @param origine cartella padre della cartella da trovare
     * @param directory file da trovare
     * @return il riferimento del nodo, se viene trovato, altrimenti null
     */
    private NodoAlbero findDirectory(NodoAlbero origine, String nome){
        for (Iteratore i = new Iteratore(origine.getFiglio()); i.hasNext(); i.nextFratello())
            if (i.getAttuale().getNome().equalsIgnoreCase(nome))
                return i.getAttuale();
        return null;
    }
    
    public void removeDirectory (String percorso){
        NodoAlbero posizioneAttuale = attuale;
        Pila percorsoAttuale = new Pila();
        Pila temp = new Pila();
        while (!this.percorso.isEmpty())
            temp.push(this.percorso.pop());
        while (!temp.isEmpty())
            percorsoAttuale.push(temp.pop());
        //trovo l'oggetto da eliminare spostando attuale in quest'ultimo, poi
        //faccio tornare attuale alla sua posizione originale
        if (!changeDirectory(percorso)) return;
        NodoAlbero daEliminare = attuale, nodoSuccessivo = daEliminare.getFratello();
        attuale = posizioneAttuale;
        this.percorso = percorsoAttuale;
        
        NodoAlbero primoFratello = daEliminare.getPadre().getFiglio();
        if (primoFratello.getNome().equalsIgnoreCase(daEliminare.getNome())){
            daEliminare.getPadre().setFiglio(nodoSuccessivo);
        } else{
            for (Iteratore i = new Iteratore (daEliminare.getPadre().getFiglio()); i.hasNext(); i.nextFratello()){
                if (i.getAttuale().getFratello().getNome().equalsIgnoreCase(daEliminare.getNome())){
                    i.getAttuale().setFratello(nodoSuccessivo);
                    return;
                }
            }
        }
    }
    
    public String tree(){
        return attuale.tree();
    }
    
    /**
     * @return il percorso della cartella attuale
     */
    public String getPercorso(){
        return percorso.toString();
    }
}
