package d3bug.words.dictionary;

public enum Phoneme {
	
	AA ("O"), 	//	odd	AA D
	AE ("A"), 	//	at	AE T
	AH ("U"),	//hut	HH AH T
	AO ("OR"),  //	ought	AO T
	AW ("OW"),	// cow	K AW
	AY ("I"),	//hide	HH AY D
	B  ("B",150),	//be	B IY
	CH ("CH"),	//cheese	CH IY Z
	D  ("D"),	//dee	D IY
	DH ("TH",350),	//thee	DH IY
	EH ("E"),	//Ed	EH D
	ER ("ER"),	//hurt	HH ER T 
	EY ("AY"),	//ate	EY T
	F  ("F"),	//fee	F IY
	G  ("G",150),	//green	G R IY N
	HH ("H"),	//he	HH IY
	IH ("I"),	//it	IH T
	IY ("EE"),	//eat	IY T
	JH ("J",150),	//gee	JH IY
	K  ("K",150),	//key	K IY
	L  ("L"),	//lee	L IY
	M  ("M"),	//me	M IY
	N  ("N"),	//knee	N IY
	NG ("NG"),	//ping	P IH NG
	OW ("OW"),	//oat	OW T
	OY ("OY"),	//toy	T OY
	P  ("P",150),	//pee	P IY
	R  ("R",300),	//read	R IY D
	S  ("S",350),	//sea	S IY
	SH ("SH"),	//she	SH IY
	T  ("T",200),	//tea	T IY
	TH ("TH"),	//theta	TH EY T AH
	UH ("OO",300),	//hood	HH UH D
	UW ("OO",300),	//two	T UW
	V  ("V",200),	//vee	V IY
	W  ("W"),	//we	W IY
	Y  ("Y"),	//yield	Y IY L D
	Z  ("Z"),	//zee	Z IY
	ZH ("ZH");	//seizure	S IY ZH ER
	
	private final String equivalent;
	private int delay;
	Phoneme(String equivalent) {
		this.equivalent = equivalent;
		this.delay = 300;
	}
	Phoneme(String equivalent,int delay) {
		this.equivalent = equivalent;
		this.delay = delay;
	}
	public String getEquivalent() {
		return equivalent;
	}
	
	public int getDelay() {
		return delay;
	}
	
}
