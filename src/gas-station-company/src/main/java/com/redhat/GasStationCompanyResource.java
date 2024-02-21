package com.redhat;

import java.util.List;
import java.util.Random;

import org.jboss.logging.Logger;

import io.quarkus.panache.common.Page;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/gasstations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GasStationCompanyResource {
	
    private static final Logger LOGGER = Logger.getLogger("GasStationCompanyResource");


    @GET
    public List<GasStation> list(@QueryParam("index") @DefaultValue("0") int index, @QueryParam("size") @DefaultValue("25") int size) {
        return GasStation.findAll().page(Page.of(index, size)).list();
    }

    @GET
    @Path("/{id}")
    public GasStation get(Long id) {
        return GasStation.findById(id);
    }

    @GET
    @Path("/count")
    public Long count() {
        return GasStation.count();
    }
    
    @GET
    @Path("/random")
    public GasStation random() {
    	
    	LOGGER.info("Get random gas station information");
    	//You can retrieve this value by running count.py
    	int minId = 1000001;
    	int maxId = 99999001;
    	GasStation pdv = null;
    	while(pdv == null) 
    		pdv = GasStation.findById(new Random().nextLong(minId, maxId));
    	
    	return pdv;
    }
}