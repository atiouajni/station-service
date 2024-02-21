package com.redhat.factory.sensors;

public interface ISensor<Type> {
				
	void setParameters(Float min, Float max, Float duration);
	
	Type nextValue();
}
