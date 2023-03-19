package testCases.IntegrationTests;


import databaseDAO.superDAO;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Users.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import presentation.USER;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	//beforeAll is just used to established a connection with the database before all tests
	@BeforeAll
	public static void before() {
		try {
			superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
    
    @Test
    void searchMedicineByName() {
        
        Inventory inv = Inventory.getInstance();
        
        ArrayList<Merchandise> merList = inv.getMerchandise();
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        
        ArrayList<Merchandise> searchResult = o.searchMedicineByName("ADVIL", USER.OWNER);
        
        int j = 0;
        for (int i = 0; i < merList.size(); i++) {
        	if (merList.get(i).getName().compareTo("ADVIL") == 0) {
        		assertEquals(merList.get(i).toString(), searchResult.get(j).toString());
        		j++;
        	}
        }

    }
    @Test
    void searchMedicineByNameNotFound() {
        
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[]", o.searchMedicineByName("CompletelyRandomNoName", USER.OWNER).toString());
    }
    
    @Test
    void searchMedicineByType() {
        
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        Inventory inv = Inventory.getInstance();
        ArrayList<Merchandise> listToSearchFrom = inv.getMerchandise();
        ArrayList<Merchandise> properResult = new ArrayList<Merchandise>();
        for (int i = 0; i < listToSearchFrom.size();i ++){
            if (listToSearchFrom.get(i).getType() == MERCHANDISE_TYPE.COLD){
            	properResult.add(listToSearchFrom.get(i));
            }
        }
        assertEquals(properResult.toString(), o.searchMedicineByType(MERCHANDISE_TYPE.COLD, USER.OWNER).toString());
    }

    @Test
    void searchMedicineByTypeNotFound() {
        
        Owner o = new Owner(0, 0);	//User is abstract so it has to be created using its subclasses
        assertEquals("[]", o.searchMedicineByName("fever", USER.OWNER).toString());
    }
}