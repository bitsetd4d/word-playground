package d3bug.speech;


public class MacSpeaker extends TextSpeaker {

	@Override
	String getKey() {
		return "osx";
	}

	@Override
	void sayImpl(String result,boolean boyVoice) {
		String voice = boyVoice ? "Bruce" : "Victoria";
		String exe = "say -v"+voice+" \""+result+"\"";
		addToExecuteQueue(exe);
	}

}
