package d3bug.speech;

public class WindowsSpeaker extends TextSpeaker {

	@Override
	String getKey() {
		return "windows";
	}

	@Override
	void sayImpl(String result,boolean boyVoice) {
		if (result.trim().length() == 0) return;
		String exe = boyVoice ? "c:\\sayboy.bat" : "c:\\saygirl.bat";
		addToExecuteQueue(exe + " " + result);
		System.out.println(result);
	}

}
