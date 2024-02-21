package com.redhat.factory.sensors;

/**
 * This class simulate a sensor data in the form of sawthooth sensor.
 * Each time the nextValue() method is called, it will return the next sample of the sawtoosh wave
 * https://en.wikipedia.org/wiki/Sawtooth_wave
 * https://en.wikipedia.org/wiki/Sampling_(signal_processing)
 * @author anissetiouajni
 *
 */
public class SawtoothSensor implements ISensor<Float> {
	
	private Float ymin;
	private Float ymax;
	private Float duration;
	private int counter = 0; //represent x
	private Float currValue;

	@Override
	public Float nextValue() {
		if(counter > duration)
			counter=0;
		
		//droite lineraire qui passe par l'origine 
		currValue = (ymax/duration) * counter + ymin; //y = a*x + b
		counter++;
		return currValue;
	}

	@Override
	public void setParameters(Float min, Float max, Float duration) {
		this.ymin = min;
		this.ymax = max;
		this.duration = duration;
	}

}
