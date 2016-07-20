package net.amarantha.timemachine;

import com.google.inject.Singleton;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Singleton
public class PixelTape {

//    private GpioPinDigitalOutput trigger;

    public PixelTape() {
//        GpioController gpio = GpioFactory.getInstance();
//        trigger = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
    }

    public void backgroundPattern() {
        File file = new File("timetravel");
        file.delete();
//        trigger.high();
    }

    public void timeTravelPattern() {
        try ( FileWriter w = new FileWriter("timetravel") ) {
            w.write("Yes");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        trigger.low();
    }

}
