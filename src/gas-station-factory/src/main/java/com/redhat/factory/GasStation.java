package com.redhat.factory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

public class GasStation {
	
	private Long id;
	private String latlng;
	private String typeroute;
	private String adresse;
	private String commune;
	private String codepostal;
	private String hdebut;
	private String hfin;
	private String saufjour;
	private String services;
	private String carburants;
	private String activite;
	private List<FuelTank> tanks = new ArrayList<>();
	
    private static final Logger LOGGER = Logger.getLogger(GasStation.class);

	public void buildTanksWithSensors(Emitter<String> mqttBroker) {
		if(carburants == null || carburants.isBlank())
			LOGGER.infof("No fuel tanks found for the gas station '%d'",id);
		else {
			for (String fuel : carburants.split("\\|")) {
				tanks.add(new FuelTank(mqttBroker, id, fuel));
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public String getTyperoute() {
		return typeroute;
	}

	public void setTyperoute(String typeroute) {
		this.typeroute = typeroute;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}

	public String getHdebut() {
		return hdebut;
	}

	public void setHdebut(String hdebut) {
		this.hdebut = hdebut;
	}

	public String getHfin() {
		return hfin;
	}

	public void setHfin(String hfin) {
		this.hfin = hfin;
	}

	public String getSaufjour() {
		return saufjour;
	}

	public void setSaufjour(String saufjour) {
		this.saufjour = saufjour;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getCarburants() {
		return carburants;
	}

	public void setCarburants(String carburants) {
		this.carburants = carburants;
	}

	public String getActivite() {
		return activite;
	}

	public void setActivite(String activite) {
		this.activite = activite;
	}

	public List<FuelTank> getTanks() {
		return tanks;
	}

	public void setTanks(List<FuelTank> tanks) {
		this.tanks = tanks;
	}
}
