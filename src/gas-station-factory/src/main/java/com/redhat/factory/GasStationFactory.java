package com.redhat.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import com.redhat.factory.restclient.GasStationCompanyRestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GasStationFactory {
	
	@Inject 
	@Channel("sensors") 
	Emitter<String> emitter;

	@RestClient
	GasStationCompanyRestClient gasStationCompanyRestClient;

	
    private static final Logger LOGGER = Logger.getLogger("com.redhat.factory.GasStationFactory");
	
	private List<GasStation> gasStations = new ArrayList<GasStation>();
	
	public List<GasStation> buildGasStation(int number) {
		LOGGER.infof("Building %d gas station(s)", number);
		
		  List<GasStation> gs = new ArrayList<GasStation>();
		  
		  for (int i = 0; i < number; i++) { 
			//Retrieve information from the gas company
			LOGGER.debugf("Retrieving informations from the gas company");
			GasStation gasStation = gasStationCompanyRestClient.random();
			gasStation.buildTanksWithSensors(emitter);
			gs.add(gasStation);
		  }
		  
		  gasStations.addAll(gs);
		  return gs;
	}
	
	public List<GasStation> getGasStations() {
		return gasStations;
	}
}
