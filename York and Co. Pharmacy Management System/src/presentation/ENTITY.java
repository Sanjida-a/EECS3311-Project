package presentation;

public enum ENTITY {
	MERCHANDISE,
	PATIENT,
	PRESCRIPTION;
	
	private static final ENTITY[] values = values();
	
	
	public ENTITY next() {
		return values[(ordinal() + 1) % values.length];
	}
}
