package com.parking.space;

import com.parking.space.model.CarDetails;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ParkingSpaceApplication {

    static Map<Integer, CarDetails> parkingDetails;

    public static void main(final String[] args) throws Exception {
        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));

        String command[];
        CarDetails carDetails;
        while(true) {
            command = br.readLine().split(" ");
            String operation = command[0];

            if(operation.equalsIgnoreCase("create_parking_lot")) {
                parkingDetails  = new HashMap<>();
                int slots = Integer.parseInt(command[1]);
                for(int i = 0;i < slots;i++)
                    parkingDetails.put(i + 1, null);

                System.out.println("Created a parking lot with " + slots + " slots");
            }

            else if(operation.equalsIgnoreCase("park")) {
                try {
                    carDetails = new CarDetails(command[1], command[2]);
                    int parkingSlot = parkNearEntry(carDetails);
                    if (parkingSlot != -1)
                        System.out.println("Allocated Slot number: " + parkingSlot);
                    else
                        System.out.println("Sorry, parking lot is full");
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(operation.equalsIgnoreCase("leave")) {
                try {
                    int parkingSlot = Integer.parseInt(command[1]);
                    CarDetails car = parkingDetails.get(parkingSlot);
                    if (car != null) {
                        parkingDetails.put(parkingSlot, null);
                        System.out.println("Slot number " + parkingSlot + " is free");
                    }
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(operation.equalsIgnoreCase("status")) {
                displayStatus();
            }

            else if(operation.equalsIgnoreCase("registration_numbers_for_cars_with_colour")) {
                try {
                    String colour = command[1];
                    displayColourCar(colour);
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(operation.equalsIgnoreCase("slot_numbers_for_cars_with_colour")) {
                try {
                    String colour = command[1];
                    displaySlotNumberColour(colour);
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(operation.equalsIgnoreCase("slot_number_for_registration_number")) {
                try {
                    String registration_number = command[1];
                    displaySlotNumber(registration_number);
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            else if(operation.equalsIgnoreCase("exit"))
                break;

            System.out.println();
        }

    }

    public static void displaySlotNumberColour(String colour) {
        for(Map.Entry<Integer, CarDetails> entry : parkingDetails.entrySet()) {
            CarDetails car = entry.getValue();
            if(car.getCarColor().equalsIgnoreCase(colour))
                System.out.print(entry.getKey() + ", ");
        }
    }

    public static void displaySlotNumber(String number) {
        for(Map.Entry<Integer, CarDetails> entry : parkingDetails.entrySet()) {
            CarDetails car = entry.getValue();
            if(car != null && car.getCarNumber().equalsIgnoreCase(number)) {
                System.out.print(entry.getKey());
                return;
            }
        }
        System.out.println("Not found");
    }

    public static void displayColourCar(String colour) {
        for(Map.Entry<Integer, CarDetails> entry : parkingDetails.entrySet()) {
            CarDetails car = entry.getValue();
            if(car != null && car.getCarColor().equalsIgnoreCase(colour))
                System.out.print(car.getCarNumber() + ", ");
        }
    }

    public static void displayStatus() {
        System.out.println("Slot No.\tRegistration No\tColour");
        for(Map.Entry<Integer, CarDetails> entry : parkingDetails.entrySet()) {
            CarDetails car = entry.getValue();
            if(car != null)
                System.out.println(entry.getKey() + "\t" + car.getCarNumber() + "\t" + car.getCarColor());
        }
    }

    public static int parkNearEntry(CarDetails carDetails) {
        for(Map.Entry<Integer, CarDetails> entry : parkingDetails.entrySet()) {
            if(entry.getValue() == null) {
                parkingDetails.put(entry.getKey(), carDetails);
                return entry.getKey();
            }
        }

        return -1;


    }

}