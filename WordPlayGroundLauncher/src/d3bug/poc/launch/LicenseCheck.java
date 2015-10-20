package d3bug.poc.launch;

import java.sql.Date;

public class LicenseCheck {

	public static void hackyQuickCheck() {
		if (true) return;
		Date before = new Date(107,6,7);
		Date after = new Date(108,9,30);
		long now = System.currentTimeMillis();
		if (now < before.getTime()) {
			System.exit(-666);
		}
		if (now > after.getTime()) {
			System.exit(-999);
		}

	}
}
