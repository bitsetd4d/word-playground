package d3bug.poc.launch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

import d3bug.poc.words.WordUtil;

public class SyllableTest {

	
	public static void main(String[] args) throws Exception {
		new SyllableTest().go(args[0]);
	}

	private void go(String fileName) throws Exception {
		FileReader fr = new FileReader(fileName);
		LineNumberReader rdr = new LineNumberReader(fr);
		while (rdr.ready()) {
			String line = rdr.readLine();
			if (line == null) break;
			processLine(line);
		}		
	}

	private void processLine(String line) {
		String[] words = line.split(" ");
		for (String word : words) {
			outputWord(word);
		}
	}

	private void outputWord(String word) {
		String[] elements = WordUtil.splitIntoElements(word);
		StringBuilder sb = new StringBuilder();
		for (String x : elements) {
			if (sb.length() > 0) sb.append("-");
			sb.append(x);
		}
		System.out.println(sb);
	}
	
	
}
