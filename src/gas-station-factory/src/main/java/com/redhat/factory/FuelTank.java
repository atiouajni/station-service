package com.redhat.factory;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.factory.sensors.ISensor;
import com.redhat.factory.sensors.SawtoothSensorReversed;
import com.redhat.factory.sensors.SensorDataRaw;
import com.redhat.factory.sensors.SensorTypeEnum;
import com.redhat.factory.sensors.SineWaveSensor;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;

public class FuelTank {
	
    private static final Logger LOGGER = Logger.getLogger(FuelTank.class);
	
	private String fuel;
	@JsonIgnore
	private Long gasStationId;
	private int capacity;
	private HashMap<SensorTypeEnum, Object> sensors = new HashMap<SensorTypeEnum, Object>();
	@JsonIgnore
	private Emitter<String> mqttBroker;

	public FuelTank(Emitter<String> mqttBroker, Long gasStationId, String fuel) {
		
		this.fuel = fuel;
		this.gasStationId = gasStationId;
		this.mqttBroker = mqttBroker;
		
		LOGGER.infof("Creating new '%s' tank with 3 sensors for '%d' gas station", fuel, gasStationId);
		
		simulateLevelSensor();
		simulateHumiditySensor();
		simulateTemperatureSensor();
	}

	private void simulateLevelSensor() {
				
		//generate a random capacity from 1000 to 3000
		capacity = (new Random().nextInt(21)*100)+1000; 
		
		LOGGER.debugf("creating '%s' tank with '%d' L capacity", fuel, capacity);
		
		SawtoothSensorReversed levelSensorSimulator = new SawtoothSensorReversed();
		levelSensorSimulator.setParameters(0f, 100f, 600f); //variation from 100 to 0 in 1 min (reversed sawTooth !)
        
		// Build an infinite stream, It emits a tank level every second
		generateAndSendAsync(levelSensorSimulator, SensorTypeEnum.level);

	}
	
	private void simulateHumiditySensor() {
		
		SineWaveSensor humiditySensorSimulator = new SineWaveSensor();
		humiditySensorSimulator.setParameters(40f, 60f, 60f); //variation from 40 to 60 in 1min
		
		generateAndSendAsync(humiditySensorSimulator, SensorTypeEnum.humidity);	
	}
	
	private void simulateTemperatureSensor() {
		
		SineWaveSensor tempSensorSimulator = new SineWaveSensor();
		tempSensorSimulator.setParameters(60f, 90f, 180f);//variation from 60 to 90 in 1min
		
		generateAndSendAsync(tempSensorSimulator, SensorTypeEnum.temperature);	
	}

	private void generateAndSendAsync(ISensor<?> sensorSimulator, SensorTypeEnum sensorType) {

		LOGGER.debugf("sending tank %s data to 'sensors' channel", sensorType.toString());

		// Build an infinite stream, It emits a tank level every second
        Multi.createFrom().ticks().every(Duration.ofSeconds(1)).subscribe().with(
        		item -> {
        			//TODO
        			Object value = sensorSimulator.nextValue();
        			
        			//attach a sensor value to the tank.
        			//This will help to retrieve the data from the rest api
        			sensors.put(sensorType, value);
        			
        			//Encapsulate sensor information into SensorDataRaw then serialize
        			SensorDataRaw sensordata = new SensorDataRaw(gasStationId, fuel, sensorType.toString() , value , new Date());
        			ObjectMapper mapper = new ObjectMapper();
        			String sensordataJson = null;
        			try {
        				sensordataJson = mapper.writeValueAsString(sensordata);
					} catch (JsonProcessingException e) {
						LOGGER.errorf("An error occured during Jackson serialization of sensor data : %s ",e.getMessage());
						e.printStackTrace();
					}
        			LOGGER.debugf("Sending data to the channel '%s' -- %s",ConfigProvider.getConfig()
							.getConfigValue("factory.sensors.topic").getValue(), sensordataJson);
        			
        			//send serialized sensor data to MQTT broker
        			mqttBroker.send(
        					MqttMessage.of(
        							ConfigProvider.getConfig()
        							.getConfigValue("factory.sensors.topic").getValue()
        							.concat("/")
        							.concat(Long.toString(gasStationId))
        							, sensordataJson));
        		}
		);
	}
	
	//GETTERS & SETTERS

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public Long getGasStationId() {
		return gasStationId;
	}

	public void setGasStationId(Long gasStationId) {
		this.gasStationId = gasStationId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Emitter<String> getMqttBroker() {
		return mqttBroker;
	}

	public void setMqttBroker(Emitter<String> mqttBroker) {
		this.mqttBroker = mqttBroker;
	}


	public HashMap<SensorTypeEnum, Object> getSensors() {
		return sensors;
	}


	public void setSensors(HashMap<SensorTypeEnum, Object> sensorValue) {
		this.sensors = sensorValue;
	}
}
