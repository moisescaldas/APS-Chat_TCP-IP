import org.jboss.weld.environment.se.Weld;

import br.unip.si.aps.moises.ServerApp;

public class Main {
	public static void main(String[] args) {
		Weld weld = new Weld();
		ServerApp app = weld.initialize().select(ServerApp.class).get();
		app.run();
		
		weld.shutdown();
		
	}
}
