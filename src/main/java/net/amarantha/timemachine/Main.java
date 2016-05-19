package net.amarantha.timemachine;

import com.google.inject.Guice;

public class Main {

    public static void main(String[] args) {
        Integer number = null;
        if ( args.length>0 ) {
            try {
                number = Integer.parseInt(args[0]);
            } catch ( NumberFormatException e ) {
                System.out.println("That's not a number Dan!");
            }
        }
        Guice.createInjector(new ApplicationModule())
            .getInstance(TimeMachineApplication.class)
                .startApplication(number);
    }

}
