package net.amarantha.timemachine;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pi4j.io.gpio.*;
import net.amarantha.timemachine.sound.AudioFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.currentTimeMillis;

@Singleton
public class TimeMachineApplication {

    private static final int PRESENT_YEAR = 2016;
    private static final String PRESENT_AUDIO_FILE = "audio/background.mp3";
    private static final String TIME_TRAVEL_AUDIO_FILE = "audio/timetravel.mp3";
    private static final long TIME_TRAVEL_DURATION = 10000;

    private boolean ignoreZapper = false;

    public void startApplication() {

        System.out.println("Starting Time Machine...");

        addYear(1969, 60000);
        addYear(1987, 40000);
        addYear(2009, 60000);
        addYear(2015, 52000);
        addYear(2031, 47000);
        addYear(2054, 56000);

        Zapper zapper = new Zapper("Zapper", RaspiPin.GPIO_00);
        zapper.setCallback((zapped) -> {
            if (zapped && !ignoreZapper) {
                ignoreZapper = true;
                engageTimeTravel();
                startScene();
                engageTimeTravel();
                inPresent();
                ignoreZapper = false;
            }
        });

        inPresent();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextLine()) {}
            zapper.zap(true);
        }

    }

    @Inject private PixelTape pixelTape;
    @Inject private SevenSeg sevenSeg;

    private void inPresent() {
        System.out.println("We are back in "+PRESENT_YEAR);
//        playAudio(PRESENT_AUDIO_FILE);
        pixelTape.backgroundPattern();
        sevenSeg.displayNumber(PRESENT_YEAR);
    }

    private void engageTimeTravel() {
        System.out.println("Engaging Time Travel....");
        try {
            playAudio(TIME_TRAVEL_AUDIO_FILE);
            pixelTape.timeTravelPattern();
            long start = currentTimeMillis();
            while ( currentTimeMillis()-start < TIME_TRAVEL_DURATION ) {
                sevenSeg.displayNumber((int)Math.round(Math.random()*9999));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Time Jump Complete!");
    }

    public void startScene() {
        int year = years.get(currentId++);
        try {
            System.out.println("We have arrived in " + year);
            playAudio(audioFiles.get(year));
            pixelTape.backgroundPattern();
            sevenSeg.displayNumber(year);
            Thread.sleep(durations.get(year));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ( currentId >= years.size() ) {
            currentId = 0;
        }
    }

    private static int currentId = 0;

    ///////////
    // Audio //
    ///////////

    private void playAudio(String file) {
        System.out.println("Playing " + file);
        if (audio != null) {
            audio.stop();
        }
        AudioFile newAudio = null;
        if (file != null) {
            newAudio = new AudioFile(file);
            newAudio.play();
        }
        audio = newAudio;
    }

    private AudioFile audio;

    ///////////
    // Years //
    ///////////

    private void addYear(int year, int duration) {
        addYear(year, duration, "audio/"+year+".mp3");
    }

    private void addYear(int year, int duration, String audioFile) {
        years.put(id, year);
        durations.put(year, duration);
        audioFiles.put(year, audioFile);
        id++;
    }

    private Map<Integer, Integer> years = new HashMap<>();
    private Map<Integer, String> audioFiles = new HashMap<>();
    private Map<Integer, Integer> durations = new HashMap<>();

    private int id = 0;

}
