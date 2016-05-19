package net.amarantha.timemachine;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

public class ApplicationModule extends AbstractModule {

    @Inject SevenSegDisplay display;

    @Override
    protected void configure() {



    }

}
