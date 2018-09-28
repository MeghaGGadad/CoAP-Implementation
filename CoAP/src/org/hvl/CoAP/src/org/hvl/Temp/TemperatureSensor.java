package org.hvl.Temp;

public interface TemperatureSensor {
	
	public double getTempInFarenheit(double celcius);
    public double getTempInCelcius(double farenheit);
    public double getCurrentTemp();
    public double setDefaultTemp(double defaultCelcius);

}
