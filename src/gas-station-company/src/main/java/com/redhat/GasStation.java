package com.redhat;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class GasStation extends PanacheEntityBase {
    
	@Id public Long id;
	public String latlng;
	public String typeroute;
	public String adresse;
	public String commune;
	public String codepostal;
	public String hdebut;
	public String hfin;
	public String saufjour;
	public String services;
	public String carburants;
	public String activite;
	
}
