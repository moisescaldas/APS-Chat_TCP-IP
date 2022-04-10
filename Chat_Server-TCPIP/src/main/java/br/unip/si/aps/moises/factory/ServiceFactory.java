package br.unip.si.aps.moises.factory;

import br.unip.si.aps.moises.service.Service;

public class ServiceFactory {
	private ServiceFactory() {}

	public static Service getService(String serviceName) {
		try {
			return (Service) Class.forName(
					"br.unip.si.aps.moises.service." + serviceName).newInstance();		
		} catch (Exception e) {
			return null;
		}
	}
}
