package d3bug.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioProcessing {

	public static void main(String[] args) {
		String testFile = "/Users/paul/Documents/workspace/WordPlayGround/sounds/letter-a.wav";
		int totalFramesRead = 0;
		File fileIn = new File(testFile);
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
			int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
			float something = audioInputStream.getFormat().getFrameRate();
			System.out.println("Bytes per frame: "+bytesPerFrame);
			// Set an arbitrary buffer size of 1024 frames.
			int numBytes = 10000 * bytesPerFrame; 
			byte[] audioBytes = new byte[numBytes];
			try {
				int numBytesRead = 0;
				int numFramesRead = 0;
				// Try to read numBytes bytes from the file.
				while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
					// Calculate the number of frames actually read.
					numFramesRead = numBytesRead / bytesPerFrame;
					totalFramesRead += numFramesRead;

					for (int i=0; i<numBytesRead; i+=2) {
						int v = (int)audioBytes[i] * (1<<8)+ (int)audioBytes[i+1];
						if (v < 0) {
							System.out.println("grrr");
						}
					}

				}
				System.out.println("Frames read: "+totalFramesRead);
			} catch (Exception ex) { 
				ex.printStackTrace();
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}		
		
	}

}
