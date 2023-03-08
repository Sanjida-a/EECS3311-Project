package testCases.IntegrationTests;

import org.junit.jupiter.api.Test;

import middleLayer.Merchandise;

/*to be tested:
 * Order(int orderNum,int medicationID, int patientID, int quantityBought, double priceAtPurchase )
 * addOrderToPatient(int _patientID, int _medicationId, int _qty , int _numOfRefills) throws Exception 
 * void Save() throws Exception
 * void refillOrderPatient(int _patientID, int _medicationId, int _qty) throws Exception
 * void refillAdd() throws Exception
 * Boolean checkEnoughQuantity(Merchandise m, int quantityWantToBuy)
 * double calculateSellingPrice()
 * int calculateRevenue()
 * String seeSummaryOfSales()
 * 
 * Order class has dependency to the database and it cannot be removed/replaced. Thus, this should be integration test. 
 * 
 */

public class OrderTest {
	@Test
	void OrderConstructorTest() {
		
	}
}
