package org.hvl.Temp;

public class RoomSimulation1 {

	public static void main(String[] args)
	 {
	  double outTemp=18.0,inTemp=20.0;
	  boolean heaterOn=false;
	  double heaterOutput=30.0;
	  double heaterOnTemp=22.0,overheat=2.0;
	  double roomFactor=0.05;
	  double roomArea=50.0;
	  int simulationtime=3;
	  
	  for(int i=0;i<simulationtime;i++)
	     {
	      inTemp-=(inTemp-outTemp)*roomFactor;
	      if(heaterOn)
	         inTemp+=heaterOutput/roomArea;
	      if(inTemp>heaterOnTemp+overheat)
	         heaterOn=false;
	      else if(inTemp<heaterOnTemp)
	         heaterOn=true;
	      System.out.printf("%3d %7.2f %7.2f",i,inTemp,outTemp);
	      if(heaterOn)
	         System.out.println("  ON");
	      else
	         System.out.println("  OFF");
	     }
	  
	 }
}
