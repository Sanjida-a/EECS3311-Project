package presentation;

public enum USER {
	PATIENT,
	PHARMACIST,
	OWNER,
	DEVELOPER,
	GUEST;
	
	
	public static USER getValue(String s) {	//convert string into USER enum
		USER result;
		if(s.compareTo("Owner") == 0) {
			result = USER.OWNER;
		}
		else if(s.compareTo("Pharmacist") == 0) {
			result = USER.PHARMACIST;
		}
		else if(s.compareTo("Developer") == 0) {
			result = USER.DEVELOPER;
		}
		else {
			result = USER.PATIENT;
		}
		return result;
	}
}
