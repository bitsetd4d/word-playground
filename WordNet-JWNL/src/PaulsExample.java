import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.dictionary.Dictionary;


public class PaulsExample {
	
	private static final String PROPS = "file_properties.xml";
	public static void main(String[] args) {
		PaulsExample p = new PaulsExample();
		try {
			p.go();
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}
	
	private void go() throws JWNLException {
		System.out.println("Go...");
		try {
			JWNL.initialize(new FileInputStream(PROPS));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Dictionary wordnet = Dictionary.getInstance();
		IndexWord word = wordnet.getIndexWord(POS.VERB,"jump");
		Synset[] senses = word.getSenses();
		for (int i = 0; i < senses.length; i++) {
		   System.out.println(word + ": " + senses[i].getGloss());
		}
		System.out.println("-------");
//		System.out.println(Arrays.toString(example.getWords()));
		
		for (Synset s : senses) { 
			PointerTargetNodeList relatedList = PointerUtils.getInstance().getAlsoSees(s);
			dumpList(relatedList,"Also Sees");
		}
		for (Synset s : senses) { 
			PointerTargetNodeList relatedList = PointerUtils.getInstance().getAntonyms(s);
			dumpList(relatedList,"Antonyms (Opposite)");
		}
		for (Synset s : senses) { 
			PointerTargetNodeList relatedList = PointerUtils.getInstance().getAttributes(s);
			dumpList(relatedList,"Attributes");
		}
	}

	private void dumpList(PointerTargetNodeList relatedList, String title) {
		System.out.println("*** "+title+" ***");
		Iterator i = relatedList.iterator();
		while (i.hasNext()) {
		  PointerTargetNode related = (PointerTargetNode) i.next();
		  Synset s = related.getSynset();
		  System.out.println(s);
		}
		System.out.println();
	}
}
