package middleLayer.MerchandiseInventory;

public enum MERCHANDISE_TYPE { // hard coded values of medication types that are possible in our inventory
	COUGH,
	COLD,
	FEVER,
	SINUS;
	
	private static final MERCHANDISE_TYPE[] values = values(); 
	
	public static MERCHANDISE_TYPE getValue(String s) {
		for(MERCHANDISE_TYPE t : values) {
			if(t.name().compareTo(s.toUpperCase())==0) {
				return t;
			}
		}
		return null;
		
	}
}


