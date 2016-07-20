package net.amarantha.timemachine;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;

public class Zapper {

    private final String name;

    public Zapper(String name, Pin triggerPin) {
        this.name = name;
        System.out.println("Connecting to GPIO...");
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalInput trigger = gpio.provisionDigitalInputPin(triggerPin, PinPullResistance.PULL_UP);
        trigger.addListener((GpioPinListenerDigital) gpioEvent -> zap(trigger.isLow()));
        System.out.println("Zapper " + name + " Ready");
    }

    private boolean zapped = false;

    private ZapCallback callback;

    public void setCallback(ZapCallback callback) {
        this.callback = callback;
    }

    public void zap(boolean zapped) {
        System.out.println("Zapper " + name + ": " + (zapped?"ZAPPED!":"---"));
        this.zapped = zapped;
        if ( callback!=null ) {
            callback.zap(zapped);
        }
    }

    public boolean isZapped() {
        return zapped;
    }

    public interface ZapCallback {
        void zap(boolean zapped);
    }

}
