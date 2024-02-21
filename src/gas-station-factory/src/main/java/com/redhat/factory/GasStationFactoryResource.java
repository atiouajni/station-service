package com.redhat.factory;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/factory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GasStationFactoryResource {
	
	@Inject 
	GasStationFactory gasStationFactory;
	
	 @GET
	 @Path("/generate")
	 public Response generate(@DefaultValue("1") @QueryParam("number") int number) {
		 
		try {
			 return Response.accepted(gasStationFactory.buildGasStation(number)).build();

		}catch (Exception exc) {
			 return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exc.getMessage()).build();
		} 
	 }
	 
	 @GET
	 @Path("/list")
	 public List<GasStation> getGasStation(@QueryParam("index") @DefaultValue("0") int index, @QueryParam("size") @DefaultValue("25") int size){
		 
		 return gasStationFactory.getGasStations();	 
	 }
}
