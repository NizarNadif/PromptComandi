/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandPrompt;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Nizar
 */
public class NodoAlbero implements Serializable{
    private String nome;
    private final NodoAlbero padre;
    private NodoAlbero fratello;
    private NodoAlbero figlio;
    private final LocalDateTime creazione;
    private final String tipo;
    private String dati;

    public NodoAlbero(String info, NodoAlbero padre, String tipo) {
        this.nome = info;
        this.padre = padre;
        this.tipo = tipo;
        fratello = null;
        figlio = null;
        dati = "";
        creazione = LocalDateTime.now();
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setDati (String dati) {
        this.dati = dati;
    }
    
    public String getDati(){
        return dati;
    }

    public NodoAlbero getPadre() {
        return padre;
    }
    
    public NodoAlbero getFratello() {
        return fratello;
    }

    public String getTipo(){
        return tipo;
    }
    
    public void setFratello(NodoAlbero fratello) {
        this.fratello = fratello;
    }

    public NodoAlbero getFiglio() {
        return figlio;
    }

    public void setFiglio(NodoAlbero figlio) {
        this.figlio = figlio;
    }

    public String tree(){
        StringBuilder str = new StringBuilder(50);
        treeRicorsivo(str, "", "");
        return str.toString();
    }
    
    /**
     * @param s stringa modificabile da thread diversi
     * @param toAdd prefisso del nodo attuale
     * @param childrenAdd prefisso di un eventuale figlio
     */
    private void treeRicorsivo(StringBuilder s, String toAdd, String childrenAdd){
        s.append(toAdd).append(nome).append("\n");
        Iteratore it = new Iteratore(figlio);
        while (it.hasNext()){
            NodoAlbero attuale = it.getAttuale();
            NodoAlbero prossimo = it.nextFratello();
            if (it.hasNext()) {
                prossimo.treeRicorsivo(s, childrenAdd + "├── ", childrenAdd + "│   ");
                
            } else {
                prossimo.treeRicorsivo(s, childrenAdd + "└── ", childrenAdd + "    ");
            }
        }
    }
    
    @Override
    public String toString() {
        String s = String.format("%02d", creazione.getDayOfMonth()) + "/" + String.format("%02d", creazione.getMonthValue()) 
                + "/" + creazione.getYear() + "  " + String.format("%02d", creazione.getHour()) 
                + ":" + String.format("%02d", creazione.getMinute());
        if (tipo.matches("directory"))
           s += "    <DIR>          ";
        else{
            for (int i = 0; i< 18 - tipo.length(); i++)
                s += ' ';
            s += tipo + ' ';
        }
        s+= nome;
        return s;
    }
    
}
