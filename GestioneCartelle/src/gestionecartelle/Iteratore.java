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
public class Iteratore implements Serializable{
    private NodoAlbero nodo;

    public Iteratore(NodoAlbero nodo) {
        this.nodo = nodo;
    }
    
    public NodoAlbero nextFratello () {
        if (nodo == null)
            return null;
        NodoAlbero n= nodo;
        nodo= nodo.getFratello();
        return n;
    }
    
    
    public NodoAlbero nextFiglio () {
        if (nodo == null)
            return null;
        NodoAlbero n= nodo;
        nodo= nodo.getFiglio();
        return n;
    }
    
    public NodoAlbero nextPadre () {
        if (nodo == null)
            return null;
        NodoAlbero n= nodo;
        nodo= nodo.getPadre();
        return n;
    }
    
    public NodoAlbero getAttuale(){
        return nodo;
    }
    
    
    public boolean hasNext() {
        if (nodo == null)
            return false;
        return true;
    }

}
