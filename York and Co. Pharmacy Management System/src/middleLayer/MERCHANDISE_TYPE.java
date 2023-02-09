package middleLayer;

public enum MERCHANDISE_TYPE {
	COUGH,
	COLD,
	FEVER,
	SINUS;
	
	private static final MERCHANDISE_TYPE[] values = values(); 

	public static MERCHANDISE_TYPE getValue(String s) {
		//MERCHANDISE_TYPE start = MERCHANDISE_TYPE.COUGH;
		for(MERCHANDISE_TYPE t : values) {
			if(t.name().compareTo(s.toUpperCase())==0) {
				return t;
			}
		}
		return null;
		
	}
}


