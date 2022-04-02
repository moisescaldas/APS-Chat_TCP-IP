package br.unip.si.aps.moises;

import org.junit.Test;

import br.unip.si.aps.moises.network.ProxyManager;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue(){
    	ProxyManager manager = new ProxyManager();
    	manager.run();	
    }
}
