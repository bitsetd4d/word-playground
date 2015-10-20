package d3bug.poc.words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordUtil {

	private static List<String> syllables = new ArrayList<String>();
	private static Set<String> strongSyllables = new HashSet<String>();
	static {
		initialiseStrongSyllables();
		initialiseSyllables();
	}
	private static void initialiseStrongSyllables() {
		strongSyllables.add("aa");
		strongSyllables.add("bb");
		strongSyllables.add("cc");
		strongSyllables.add("dd");
		strongSyllables.add("ee");
		strongSyllables.add("ff");
		strongSyllables.add("ll");
		strongSyllables.add("mm");
		strongSyllables.add("nn");
		strongSyllables.add("oo");
		strongSyllables.add("pp");
		strongSyllables.add("rr");
		strongSyllables.add("ss");
		strongSyllables.add("tt");
		strongSyllables.add("zz");
		strongSyllables.add("ing");
	}
	
	// Need to think about barrier etc
	// so it would be b-a-rr-ier not b-ar-r-ier
	private static void initialiseSyllables() {
		syllables.add("ight");
		syllables.add("tion");
		syllables.add("ong");
		syllables.add("ate");
		//syllables.add("ing");
		syllables.add("ike");
		syllables.add("ise");
		syllables.add("ile");
		syllables.add("ice");
		syllables.add("ia");
		syllables.add("in");
		syllables.add("au");
		syllables.add("sh");
		syllables.add("st");
		syllables.add("ng");
		syllables.add("ch");
		syllables.add("kn");
		syllables.add("ei");
		syllables.add("ea");
		syllables.add("gh");
		syllables.add("ph");
		syllables.add("or");
		syllables.add("ow");
		syllables.add("th");
		syllables.add("ss");
		syllables.add("ai");
		syllables.add("oa");
		syllables.add("ie");
		syllables.add("qu");
		syllables.add("ou");
		syllables.add("oi");
		syllables.add("ue");
		syllables.add("er");
		syllables.add("ar");
		syllables.add("ck");
		syllables.add("wh");
	}

	public static boolean isSyllable(String x) {
		return syllables.contains(x) || strongSyllables.contains(x);
	}


	public static String[] splitIntoElements(String x) {
		for (String j : syllables) {
			if (j.equalsIgnoreCase(x)) {
				return splitIntoLetters(x);
			}
		}
		
		for (String j : strongSyllables) {
			if (j.equalsIgnoreCase(x)) {
				return splitIntoLetters(x);
			}
		}
		
		List<String> decomposed = decomposeByStrongSyllables(x);

		List<String> bits = new ArrayList<String>();
		OUTER:
		for (String xx : decomposed) {
			LOOP:
			for (int i=0; i<xx.length(); i++) {
				if (strongSyllables.contains(xx.toLowerCase())) {
					bits.add(xx);
					continue OUTER;
				} 
				String z = xx.substring(i);
				for (String j : syllables) {
					if (z.toLowerCase().startsWith(j)) {
						bits.add(z.substring(0,j.length()));
						i += j.length()-1;
						continue LOOP;
					}
				}
				bits.add(z.substring(0,1));
			}
		}
		return bits.toArray(new String[0]);
	}

	private static List<String> decomposeByStrongSyllables(String x) {
		for (String j : strongSyllables) {
			if (x.equals(j)) return Collections.singletonList(x);
			int indx = x.toLowerCase().indexOf(j);
			if (indx >= 0) {
				String before = x.substring(0,indx);
				String after = x.substring(indx + j.length());
				List<String> beforeList = decomposeByStrongSyllables(before);
				List<String> afterList = decomposeByStrongSyllables(after);
				List<String> answer = new ArrayList<String>();
				answer.addAll(beforeList);
				answer.add(j);
				answer.addAll(afterList);
				return answer;
			}
		}
		return Collections.singletonList(x);
	}

	public static String[] splitIntoLetters(String x) {
		String[] y = new String[x.length()];
		for (int i=0; i<y.length; i++) {
			y[i] = String.valueOf(x.charAt(i));
		}
		return y;
	}	
	
}
