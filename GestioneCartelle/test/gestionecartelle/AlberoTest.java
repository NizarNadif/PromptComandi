/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionecartelle;

import gestionecartelle.Albero;
import gestionecartelle.NodoAlbero;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nizar
 */
public class AlberoTest {
    
    public AlberoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of inserisci method, of class Albero.
     */
    @Test
    public void testInserisciRicerca() {
        System.out.println("inserisci");
        Albero instance = new Albero();
        instance.inserisci("a", "a");
        instance.inserisci("a", "b");
        instance.inserisci("a", "c");
        instance.inserisci("b", "d");
        instance.inserisci("d", "e");
        instance.inserisci("c", "f");
        NodoAlbero node = instance.ricerca("f");
        String expResult = "f";
        assertEquals(expResult, node.getNome());
    }

    /**
     * Test of size method, of class Albero.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Albero instance = new Albero();
        instance.inserisci("a", "a");
        instance.inserisci("a", "b");
        instance.inserisci("a", "c");
        instance.inserisci("b", "d");
        instance.inserisci("d", "e");
        instance.inserisci("c", "f");
        int expResult = 6;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of altezza method, of class Albero.
     */
    @Test
    public void testAltezza() {
        System.out.println("altezza");
        Albero instance = new Albero();
        instance.inserisci("a", "a");
        instance.inserisci("a", "b");
        instance.inserisci("a", "c");
        instance.inserisci("b", "d");
        instance.inserisci("d", "e");
        instance.inserisci("c", "f");
        int expResult = 3;
        int result = instance.altezza();
        assertEquals(expResult, result);
    }
    
}
