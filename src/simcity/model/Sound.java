package simcity.model;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    public Sound(String soundFile, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        play(soundFile, speed);
    }

    public Sound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    }

    public void play(String soundFile, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread() {

            @Override
            public void run() {
                try {
                    if (speed == 0) {
                        return;
                    } else if (speed == 1) {
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                        sleep(20000);
                        clip.stop();
                        clip.close();
                    } else if (speed == 2) { //*1,5
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                        sleep(13333);
                        clip.stop();
                        clip.close();
                    } else { //*2
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                        sleep(10000);
                        clip.stop();
                        clip.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }

    public void playShort(String soundFile, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Thread() {

            @Override
            public void run() {
                try {
                    if (speed == 0) {
                        return;
                    } else if (speed == 1) {
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        sleep(5000);
                        clip.start();
                        sleep(10000);
                        clip.stop();
                        clip.close();
                    } else if (speed == 2) { //*1,5
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        sleep(3333);
                        clip.start();
                        sleep(6666);
                        clip.stop();
                        clip.close();
                    } else { //*2
                        Scanner scanner = new Scanner(System.in);
                        File file = new File(soundFile);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        sleep(2500);
                        clip.start();
                        sleep(5000);
                        clip.stop();
                        clip.close();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
}
