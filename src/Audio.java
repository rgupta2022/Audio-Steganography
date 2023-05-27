import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	 private String fileName;
	    private Clip clips;

	    public Audio(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	        this.fileName = fileName;
	        loadAudio();
	    }

	    private void loadAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	        File file = new File(fileName);
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
	        clips = AudioSystem.getClip();
	        clips.open(audioInputStream);
	    }

	    public void play() {
	        if (clips != null) {
	            clips.start();
	        }
	    }

	    public void stop() {
	        if (clips != null) {
	            clips.stop();
	            clips.setFramePosition(0);
	        }
	    }
}
