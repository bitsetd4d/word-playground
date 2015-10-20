package d3bug.words.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import d3bug.os.ThisComputer;
import d3bug.words.dictionary.internal.DictionaryReader;

public class DictionaryLookup {
	
	private static Map<String,DictionaryWord> dictionary;
	static {
		initialiseDictionary();
	}
	
	private static DictionaryWord lastWord;
	
	public static DictionaryWord lookup(String word) {
		String key = word.toUpperCase();
		if (lastWord != null && lastWord.getWord().equals(word)) {
			return lastWord;
		}
		return (lastWord = dictionary.get(key));
	}

	private static void initialiseDictionary() {
		InputStream is = DictionaryLookup.class.getResourceAsStream("/resources/cmudict.0.6");
		if (is == null) {
			try {
				is = new FileInputStream(new File("WordPlayWords/resources//cmudict.0.6"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		DictionaryReader rdr = new DictionaryReader(is);
		rdr.load();
		dictionary = rdr.getDictionary();		
	}

}
