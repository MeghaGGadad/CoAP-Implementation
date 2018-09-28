package org.hvl.Temp;

import java.util.Scanner;


public class TempSimulator {

        public static void main(String[] args) {

        TempSimulatorImpl temp = new TempSimulatorImpl();

        while(true){
            System.out.println("Default temperatures: "+temp.getCurrentTemp()+"C OR "+temp.getTempInFarenheit(temp.getCurrentTemp())+"F");
            System.out.println("1.Convert Celcius to Fareiheit");
            System.out.println("2.Convert Fareiheit to Celcius");
            System.out.println("3.Update default temperature");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            switch (input) {
            case 1: System.out.println("Enter temperature in Celcius to convert into Farenheit");
                double celcius = sc.nextDouble();
                System.out.println(celcius+"C = "+temp.getTempInFarenheit(celcius)+"F");
                break;
            case 2: System.out.println("Enter temperature in Farenheit to convert into Celcius");
            double fareinheit = sc.nextDouble();
            System.out.println(fareinheit+"F = "+temp.getTempInCelcius(fareinheit)+"C");
            break;
            case 3: System.out.println("Enter temperature in celcius");
            temp.setDefaultTemp(sc.nextDouble());
                break;
            default:
                System.out.println("Invalid input.");;
            }

        }
    }
}