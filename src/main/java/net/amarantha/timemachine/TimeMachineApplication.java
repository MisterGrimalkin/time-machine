package net.amarantha.timemachine;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Scanner;

@Singleton
public class TimeMachineApplication {

    @Inject SevenSegDisplay display;

    public void startApplication(Integer constantNumber) {

        System.out.println("Starting Time Machine...");

        int number = 0;

        while( true ) {
            if ( constantNumber!=null ) {
                display.putNumber(constantNumber);
            } else {
                if (number > 9999) {
                    number = 0;
                }
                System.out.println("Travelling to: " + number);
                display.putNumber(number++);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
