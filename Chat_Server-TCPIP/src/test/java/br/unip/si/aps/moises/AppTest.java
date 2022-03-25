package br.unip.si.aps.moises;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.unip.si.aps.moises.network.ConnectionManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue(){
    	ConnectionManager manager = new ConnectionManager();
    	manager.run();
    	
    }
}
