package net.amarantha.timemachine.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class AudioFile extends PlaybackListener implements Runnable {

    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    private boolean playing = false;

    public AudioFile(String filePath) {
        this.filePath = filePath;
    }

    private Process process;
//    private Timer repeatTimer;

    public void playInProcess() {
        try {
            process = Runtime.getRuntime().exec("mpg321 " + filePath);
//            if ( repeatTimer!=null ) {
//                repeatTimer.cancel();
//            }
//            repeatTimer = new Timer();
//            repeatTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if ( process!=null && !process.isAlive() ) {
//                        playInProcess();
//                    }
//                }
//            }, 0, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
//        playWithJLayer();
        playInProcess();
    }

    public void playWithJLayer() {
        try {
            String urlAsString = "file:///" + new java.io.File(".").getCanonicalPath() + "/" + this.filePath;

            player = new AdvancedPlayer(new URL(urlAsString).openStream(), FactoryRegistry.systemRegistry().createAudioDevice());
            player.setPlayBackListener(this);

            playerThread = new Thread(this);
            playerThread.start();
            System.out.println(urlAsString);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
//        if ( player!=null ) {
//            player.stop();
//        }
        if ( process !=null ) {
            System.out.println("Killing");
            try {
                process.destroyForcibly().waitFor();
//                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        process = null;
    }

    public void playbackStarted(PlaybackEvent playbackEvent) {
        playing = true;
    }

    public void playbackFinished(PlaybackEvent playbackEvent) {
        playing = false;
    }

    public void run() {
        try {
            System.out.println("Playing....");
            this.player.play();
            System.out.println("Played");
        } catch (JavaLayerException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return process.isAlive();
    }
}

