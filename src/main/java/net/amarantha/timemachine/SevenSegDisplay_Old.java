package net.amarantha.timemachine;

import com.google.inject.Singleton;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import static com.pi4j.io.gpio.RaspiPin.GPIO_00;
import static com.pi4j.io.gpio.RaspiPin.GPIO_01;

@Singleton
public class SevenSegDisplay_Old {

    private GpioPinDigitalOutput clockPin;
    private GpioPinDigitalOutput dataPin;
//    private GpioPinDigitalOutput loadPin;
//    private GpioPinDigitalOutput resetPin;

    private int number = 0;

    public SevenSegDisplay_Old() {

//        GpioController gpio = GpioFactory.getInstance();
//        clockPin = gpio.provisionDigitalOutputPin(GPIO_00, PinState.LOW);
//        dataPin = gpio.provisionDigitalOutputPin(GPIO_01, PinState.LOW);
//        loadPin = gpio.provisionDigitalOutputPin(GPIO_02, PinState.LOW);
//        resetPin = gpio.provisionDigitalOutputPin(GPIO_03, PinState.LOW);

    }

    private void sleep(long nanoSeconds) {
        long start = System.nanoTime();
        while ( System.nanoTime() - start < nanoSeconds ) {}
    }

    private long delay = 600;

    public void putNumber(int number) {

        if ( number < 0 || number > 9999 ) {
            throw new IllegalArgumentException("Fuck that!");
        }

        // start with Most Significant
        int[] digits = new int[4];
        digits[0] = number / 1000;
        digits[1] = (number - (digits[0]*1000)) / 100;
        digits[2] = (number - (digits[0]*1000) - (digits[1]*100)) / 10;
        digits[3] = (number - (digits[0]*1000) - (digits[1]*100) - (digits[2]*10));

//        System.out.println(digits[0] + "," + digits[1] + "," + digits[2] + "," + digits[3]);

        boolean[] pattern = new boolean[35];
        pattern[0] = T;
        int i=1;
        for ( int d=0; d<4; d++ ) {
            boolean[] digitPattern = NUMBERS[digits[d]];
            for ( int b=0; b<7; b++ ) {
                pattern[i++] = digitPattern[b];
            }
        }
        for ( int j=0; j<6; j++ ) {
            pattern[i++] = F;
        }


        this.number = number;

        boolean last = false;

        // initial
        pulseClock();

//        sleep(delay/2);
//        dataPin.high();
//        sleep(delay/2);

//        pulseClock();

        for ( int d=0; d<35; d++ ) {
            sleep(delay/2);
            dataPin.setState(pattern[d]);
            sleep(delay/2);
            pulseClock();
        }

//        sleep(delay/2);
//        dataPin.low();
//        sleep(delay/2);
//        pulseClock();
    }

    private void pulseClock() {
        clockPin.high();
        sleep(delay);
        clockPin.low();
    }

    private static final boolean T = true;
    private static final boolean F = false;

    private static final boolean[] ZERO =   { T,T,T,T,T,T,F };
    private static final boolean[] ONE =    { F,T,T,F,F,F,F };
    private static final boolean[] TWO =    { T,T,F,T,T,F,T };
    private static final boolean[] THREE =  { T,T,T,T,F,F,T };
    private static final boolean[] FOUR =   { F,T,T,F,F,T,T };
    private static final boolean[] FIVE =   { T,F,T,T,F,T,T };
    private static final boolean[] SIX =    { T,F,T,T,T,T,T };
    private static final boolean[] SEVEN =  { T,T,T,F,F,F,F };
    private static final boolean[] EIGHT =  { T,T,T,T,T,T,T };
    private static final boolean[] NINE =   { T,T,T,F,F,T,T };

    private static final boolean[][] NUMBERS = { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };

}
