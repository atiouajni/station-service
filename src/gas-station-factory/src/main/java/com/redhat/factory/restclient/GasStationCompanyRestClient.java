package com.redhat.factory.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.redhat.factory.GasStation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/gasstations")
@RegisterRestClient(configKey = "gas-station-company")
public interface GasStationCompanyRestClient {

	@GET
	@Path("/random")
	GasStation random();
}
