/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commandPrompt;

import java.io.Serializable;

/**
 *
 * @author Nizar
 */
public class Albero implements Serializable{
    private NodoAlbero root;
    private int n_nodi;
    
    public Albero(){
        root = null;
        n_nodi = 0;
    }
    
    /**
    *Aggiunge un nodo all'albero
     * @param dove padre del nuovo nodo
     * @param contenuto nome del nuovo nodo
     * @param tipo tipo del nodo (file o directory)
    */
    public void inserisci(NodoAlbero dove, String contenuto, String tipo){
        if (n_nodi == 0){
            root = new NodoAlbero(contenuto, null, tipo);
            n_nodi++;
            return;
        }
        //se il nodo padre non ha un figlio, lo creiamo
        if (dove.getFiglio() == null)
            dove.setFiglio(new NodoAlbero(contenuto, dove, tipo));
        //se ce ne ha già almeno uno, lo accodiamo agli altri figli
        else {
            NodoAlbero temp = dove.getFiglio();
            while (temp.getFratello() != null)
                temp = temp.getFratello();
            temp.setFratello(new NodoAlbero(contenuto, temp.getPadre(), tipo));
        }
        n_nodi++;
    }
    
    /**
    *Aggiunge un nodo all'albero
     * @param dove nome del padre del nuovo nodo
     * @param contenuto nome del nuovo nodo
    */
    public void inserisci(String dove, String contenuto){
        if (n_nodi == 0){
            root = new NodoAlbero(contenuto, null, "directory");
            n_nodi++;
            return;
        }
        //troviamo il nodo che sarà il padre del nodo che dobbiamo aggiungere
        NodoAlbero cercato = ricerca(dove);
        
        //se il nodo padre non esiste, non aggiungiamo il nuovo nodo
        if (cercato == null)
            return;
        //se il nodo padre non ha un figlio, lo creiamo
        if (cercato.getFiglio() == null)
            cercato.setFiglio(new NodoAlbero(contenuto, cercato, "directory"));
        //se ce ne ha già almeno uno, lo accodiamo agli altri figli
        else {
            NodoAlbero temp = cercato.getFiglio();
            while (temp.getFratello() != null)
                temp = temp.getFratello();
            temp.setFratello(new NodoAlbero(contenuto, temp.getPadre(), "directory"));
        }
        n_nodi++;
    }
    
    /**
    *Questo metodo cerca e ritorna un nodo nell'albero
    * @param dove nodo da cercare
    * @return il nodo da cercare
    */
    public NodoAlbero ricerca(String dove){
        return ricercaRicorsiva(dove, root);
    }
    
    private NodoAlbero ricercaRicorsiva(String dove, NodoAlbero attuale){
        /*se arriviamo ad una foglia, ci fermiamo perché non
        è ovviamente possibile trovare il nodo ricercato
        fra i figli di una foglia, non avendone*/
        if (attuale == null)
            return null;
        //se ho trovato il nodo ricercato, lo ritorno
        if (attuale.getNome().compareToIgnoreCase(dove) == 0)
            return attuale;
        /*altrimenti continuo a cercare fra i figli e i fratelli
        (e i loro rispettivi figli) del nodo nel quale mi trovo al momento*/
        NodoAlbero a = ricercaRicorsiva(dove, attuale.getFiglio());
        NodoAlbero b = ricercaRicorsiva(dove, attuale.getFratello());
        //se abbiamo trovato il nodo ricercato fra i figli, lo ritorniamo
        if (a != null)
            return a;
        //altrimenti ritorniamo il nodo trovato fra i fratelli
        return b;
    }
    
    /**
     * Questo metodo non effettua una ricorsione
     * @return numero di nodi nell'albero
    */
    public int size(){
        return n_nodi;
    }
    
     /**
     * @return altezza dell'albero, ovvero la distanza fra la radice dell'albero e la foglia più lontana a quest'ultima
     */
    public int altezza(){
        return altezzaRicorsiva(root);
    }

    private int altezzaRicorsiva(NodoAlbero attuale){
        if (attuale == null)
            return 0;
        NodoAlbero temp = attuale.getFiglio();
        int max = 0;
        while (temp != null){
            int h= altezzaRicorsiva(temp);
            if (h > max)
                max = h;
            temp = temp.getFratello();
        }
        if (attuale.getFiglio() == null)
            return max;
        return max + 1;
    }

    public NodoAlbero getRoot() {
        return root;
    }
    
    
     /**
     * @param partenza nodo dal quale parte l'iteratore
     * @return un iteratore che ci permette di muoverci
     * nell'albero fra i padri, i fratelli e le foglie
     */
    public Iteratore getIteratore (NodoAlbero partenza) {
        Iteratore iteratore = new Iteratore (partenza);
        return iteratore;
    }
}
