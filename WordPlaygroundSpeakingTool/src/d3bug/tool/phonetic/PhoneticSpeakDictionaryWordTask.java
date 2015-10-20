package d3bug.tool.phonetic;

import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.words.dictionary.DictionaryWord;
import d3bug.words.dictionary.Phoneme;

public class PhoneticSpeakDictionaryWordTask extends UniverseRunnable {

	private DictionaryWord word;
	
	public PhoneticSpeakDictionaryWordTask(DictionaryWord word) {
		this.word = word;
	}
	
	@Override
	public String getName() {
		return "Speak dictionary word";
	}

	@Override
	public void run() {
		Phoneme[] phonemes = word.getPhonemes();
 		for (Phoneme p : phonemes) {
			SoundFx.getInstance().playPhoneme(p.toString());
 			sleep(p.getDelay());
  		}
 	}
}