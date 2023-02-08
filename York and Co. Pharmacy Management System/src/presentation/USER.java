package presentation;

public enum USER {
	PATIENT,
	PHARMACIST,
	OWNER;
	
	
	public static USER getValue(String s) {
		USER result;
		if(s.compareTo("Owner") == 0) {
			result = USER.OWNER;
		}
		else if(s.compareTo("Pharmacist") == 0) {
			result = USER.PHARMACIST;
		}
		else {
			result = USER.PATIENT;
		}
		return result;
	}
}
