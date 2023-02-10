package presentation;

public enum ENTITY {
	MERCHANDISE,
	PATIENT,
	PRESCRIPTION;
	
	private static final ENTITY[] values = values();
	
	
	public ENTITY next() { //get next enum value of the object
		return values[(ordinal() + 1) % values.length];
	}
}
