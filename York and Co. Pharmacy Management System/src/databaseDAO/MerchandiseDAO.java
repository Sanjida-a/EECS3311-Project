package databaseDAO;

import java.util.ArrayList;

import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;
import middleLayer.Merchandise;


public class MerchandiseDAO {

	private ArrayList<Merchandise> allInventory = new ArrayList<Merchandise>();
	
	public MerchandiseDAO() throws ClassNotFoundException {
		try {
			Merchandise mer1 = new Merchandise( "Advil",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
			Merchandise mer2 = new Merchandise( "Tylenol",  5,  8.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, true);
			Merchandise mer3 = new Merchandise( "Advil",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.TABLET, true);
			Merchandise mer4 = new Merchandise( "Tylenol",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, true);
			Merchandise mer5 = new Merchandise( "Pill1",  10,  5.00, MERCHANDISE_TYPE.COLD , MERCHANDISE_FORM.LIQUID, false);
			Merchandise mer6 = new Merchandise( "Pill2",  10,  5.00, MERCHANDISE_TYPE.FEVER , MERCHANDISE_FORM.TABLET, false);

			allInventory.add(mer1);
			allInventory.add(mer2);
			allInventory.add(mer3);
			allInventory.add(mer4);
			allInventory.add(mer5);
			allInventory.add(mer6);
//			allUsernamesAndPasswordsList.add(pharma1);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Merchandise> getListOfMerchandise() {
		return allInventory;
	
	}
	

}
