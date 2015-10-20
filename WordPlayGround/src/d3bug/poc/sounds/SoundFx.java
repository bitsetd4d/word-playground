package d3bug.poc.sounds;

import java.applet.AudioClip;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import d3bug.audio.AudioClipAccess;

public class SoundFx {
	
	private static Random random = new Random();
	private static SoundFx INSTANCE = new SoundFx();
	ExecutorService soundExecutor = Executors.newSingleThreadExecutor();
	
	public static SoundFx getInstance() {
		return INSTANCE;
	}
	
	private static final String[] MERGE_SOUNDS = {
		"rollover20.wav",
		"rollover19.wav",
		"rollover14.wav",
		"rollover09.wav",
		"rollover13.wav",
		"rollover03.wav"};

	public void soundStartup() {
		playSoundFile("newsalert.wav");
	}
	
	public void soundWordPickedUp() {
		playSoundFile("magicwand.wav");
	}
	
	public void soundCorrect() {
		playSoundFile("nicechime.wav");
	}	
	
	public void soundVacuumAttack() {
		playSoundFile("boing.wav");
	}
	
	public void soundClick() {
		playSoundFile("generalclick15.wav");
	}

	public void soundDoubleClick() {
		playSoundFile("generalclick26.wav");
	}
	
	public void soundNewLetterButtonCreated() {
		playSoundFile("generalclick03.wav");
	}
	
	public void soundEatButton() {
		playSoundFile("scifirollover.wav");
	}
	
	public void soundMerge() {
		int idx = random.nextInt(MERGE_SOUNDS.length);
		String sound = MERGE_SOUNDS[idx];
		playSoundFile(sound);
	}
	
	public void soundUndo() {
		playSoundFile("whoosh08.wav");
	}

	public void soundRedo() {
		playSoundFile("whoosh05.wav");
	}
	
	public void soundDontKnown() {
		playSoundFile("bingbong.wav");
	}

	
	public void playPhonic(String t) {
		String fileName = ("phonetics/"+t+".wav").toLowerCase();
		playSoundFile(fileName);
	}
	
	public void playPhoneme(String t) {
		String fileName = ("phonemes/p-"+t+".wav").toLowerCase();
		playSoundFile(fileName);		
	}
	
	private void playSoundFile(final String fileName) {
		soundExecutor.execute(new Runnable() { public void run() {
			AudioClip clip = AudioClipAccess.getInstance().getAudioClip(fileName);
			clip.play();
		}});
	}


}
