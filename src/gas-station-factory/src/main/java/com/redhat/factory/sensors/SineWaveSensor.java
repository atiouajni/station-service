package com.redhat.factory.sensors;

/**
 * https://mathbitsnotebook.com/Algebra2/TrigGraphs/TGTrigVocab.html
 * @author anissetiouajni
 *
 */
public class SineWaveSensor implements ISensor<Double> {
	
	private Float ymin;
	private Float ymax;
	private Float duration;
	private int counter = 0; //represent x
	private double currValue;

	@Override
	public void setParameters(Float min, Float max, Float duration) {
		this.ymin = min;
		this.ymax = max;
		this.duration = duration;
	}

	@Override
	public Double nextValue() {
		if(counter > duration)
			counter=0;
		
		currValue = ((ymax-ymin)/2)  *  Math.sin(2*Math.PI*(1/duration)*counter)  +  (ymin+(ymax-ymin)/2); // y = A*sin(B*x)+D A:Amplitude, B:Frequency D:Vertical drift
		counter++;

		return currValue;
	}

}