package net.amarantha.timemachine;

import com.google.inject.Guice;
import net.amarantha.timemachine.sound.AudioFile;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Guice.createInjector(new ApplicationModule())
            .getInstance(TimeMachineApplication.class)
                .startApplication();
    }

}
