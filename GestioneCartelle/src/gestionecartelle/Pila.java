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
public class Pila implements Serializable{
    private final Sequenza seq;
    //Sequenza è una inner class di Pila e Nodo è una inner class di Sequenza
    //Pila -> Sequenza -> Nodo
    public Pila() {
        seq= new Sequenza ();
    }
    
    public void push(String s){
        seq.inserimentoInTesta(s);
    }
    
    public String pop(){
        String s= seq.getItem(1);
        seq.cancellaInTesta();
        return s;
    }
    
    public int size(){
        return seq.sizeNodi();
    }
    
    public boolean isEmpty (){
        return seq.sizeNodi() == 0;
    }
    
    @Override
    public String toString() {
        return seq.toString();
    }
        
    static class Sequenza implements Serializable{
        private Nodo head;
        private int n_nodi;

        public Sequenza() {
            head= null;
            n_nodi= 0;
        }

        public void inserimentoInTesta (String s){
            Nodo nuovo= new Nodo(s);
            nuovo.setNext(head);
            head= nuovo;
            n_nodi++;
        }

        public void cancellaInTesta () {
            if (head == null)
                return;
            head= head.getNext();
            n_nodi--;
        }

        public void inserimentoInCoda (String s){
            if (n_nodi == 0)
                head= new Nodo (s);
            Nodo temp= head;
            while (temp.getNext() != null) 
                temp = temp.getNext();
            temp.setNext(new Nodo (s));
            n_nodi++;
        }

        public void cancellaInCoda () {
            Nodo temp= head;
            while (temp.getNext().getNext() != null)
                temp= temp.getNext();
            temp.setNext(null);
            n_nodi--;
        }

        public void inserimentoInPosizione (String s, int pos) {
            if (pos < 0 || pos > n_nodi)
                return;
            if (pos == 0)
                inserimentoInTesta(s);
            else{
                Nodo temp1 = head;
                Nodo temp2 = new Nodo(s);
                for (int i=0; i<pos-1; i++)
                    temp1 = temp1.getNext();
                temp2.setNext(temp1.getNext());
                temp1.setNext(temp2);
            }
            n_nodi++;
        }

        public void cancellaInPosizione (int pos) {
            if (pos < 0 || pos > n_nodi)
                return;
            if (pos == 0)
                cancellaInTesta();
            else{
                Nodo temp= head;
                for (int i=0; i<pos-1; i++)
                   temp = temp.getNext();
                temp.setNext(temp.getNext().getNext());
            }
            n_nodi--;
        }

        public String getItem (int pos) {
            if (pos < 0 || pos > n_nodi)
                return null;
            Nodo temp= head;
            for (int i=0; i<pos-1; i++)
                temp= temp.getNext();
            return temp.getInfo();
        }

        public int sizeNodi () {
            return n_nodi;
        }

        @Override
        public String toString() {
            if (n_nodi == 0)
                return "La lista è vuota";
            String s= "" + head.getInfo();
            Nodo temp= head.getNext();
            while (temp != null) {
                s= temp.getInfo() + s;
                temp = temp.getNext();
            } 

            return s;
        }
        
        public class Nodo implements Serializable{
            private String info;
            private Nodo next;

            public Nodo(String info) {
                this.info = info;
                this.next= null;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public Nodo getNext() {
                return next;
            }

            public void setNext(Nodo next) {
                this.next = next;
            }

        }

    }
    
}
