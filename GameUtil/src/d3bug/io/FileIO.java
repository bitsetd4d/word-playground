package d3bug.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileIO {
	
	private static final int BLKSIZ = 8192; 
	
	/** Read the entire content of an Reader into a String */ 
	public static String readerToString(Reader is) throws IOException { 
	        StringBuffer sb = new StringBuffer(  ); 
	        char[] b = new char[BLKSIZ]; 
	        int n; 
	        // Read a block. If it gets any chars, append them. 
	        while ((n = is.read(b)) > 0) { 
	                sb.append(b, 0, n); 
	        } 
	  
	        // Only construct the String object once, here. 
	        return sb.toString(); 
	} 
	  
	/** Read the content of a Stream into a String */ 
	public static String inputStreamToString(InputStream is) throws IOException { 
	        return readerToString(new InputStreamReader(is)); 
	} 

}
