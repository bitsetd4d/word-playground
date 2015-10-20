package d3bug.words.dictionary.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import d3bug.words.dictionary.DictionaryWord;
import d3bug.words.dictionary.Phoneme;

public class DictionaryReader {
	
	public static void main(String[] args) {
//		File f = new File("resources/cmudict.0.6");
//		DictionaryReader dr = new DictionaryReader(f);
//		dr.load();
//		int yes = 0;
//		int no = 0;
//		for (DictionaryWord w : dr.getDictionary().values()) {
//			StringBuilder sb = new StringBuilder();
//			for (Phoneme p : w.getPhonemes()) {
//				sb.append(p.getEquivalent());
//			}
//			if (sb.toString().toUpperCase().equals(w.getWord())) {
//				System.out.println(w.getWord());
//				yes++;
//			} else {
//				no++;
//			}
//		}
//		System.out.println("Yes: "+yes+", No: "+no);
	}
	
	private InputStream is;
	private Map<String,DictionaryWord> map = new HashMap<String,DictionaryWord>();
	
	private int ok;
	private int errors;
	
	public DictionaryReader(InputStream is) {
		this.is = is;
	}

	public Map<String,DictionaryWord> getDictionary() {
		return map;
	}

	public void load() {
		long t1 = System.currentTimeMillis();
		int count = 0;
		try {
			LineNumberReader rdr = new LineNumberReader(new InputStreamReader(is));
			String line = null;
			while ((line = rdr.readLine()) != null) {
				parseLine(line);
				count++;
			}
			rdr.close();
			long t2 = System.currentTimeMillis();
			System.out.println("Dictionary read "+count+" entries in "+(t2 - t1)+"ms - "+ok+" words loaded, word map: "+map.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private void parseLine(String line) {
		if (line.startsWith("#")) return;
		StringTokenizer tok = new StringTokenizer(line);
		if (!tok.hasMoreTokens()) return;
		String first = tok.nextToken();
		String word = first.trim().toUpperCase();
		List<Phoneme> list = new ArrayList<Phoneme>();
		while (tok.hasMoreTokens()) {
			String t = tok.nextToken();
			Phoneme p = parsePhoneme(t);
			if (p == null) {
				System.err.println("Error parsing "+line+" ["+t+"]");
				errors++;
				return;
			}
			list.add(p);
		}
		Phoneme[] phonemes = list.toArray(new Phoneme[0]);
		DictionaryWord dword = new DictionaryWordImpl(word,phonemes);
		map.put(word,dword);
		ok++;
	}

	private Phoneme parsePhoneme(String p) {
		String in = p.trim();
		if (in.equals("")) return null;
		char c = p.charAt(p.length() - 1);
		if (Character.isDigit(c)) {
			in = p.substring(0,p.length() - 1);
		}
		try {
			return Phoneme.valueOf(in);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static class DictionaryWordImpl implements DictionaryWord {

		private String word;
		private Phoneme[] phonemes;

		DictionaryWordImpl(String word,Phoneme[] phonemes) {
			this.word = word;
			this.phonemes = phonemes;
		}
		
		public String getWord() {
			return word;
		}
		
		public Phoneme[] getPhonemes() {
			return phonemes;
		}
		
	}
	

}
