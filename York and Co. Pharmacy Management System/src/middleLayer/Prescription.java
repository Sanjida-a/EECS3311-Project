package middleLayer;

import java.util.ArrayList;

public class Prescription { // prescription forms by doctor
	int prescriptionNum;
	int medicationID;
	int patientID;
	int originalNumOfRefills;
	
//	ArrayList<Order> ordersForPrescription = new ArrayList<Order>();
	
//	public Prescription(int medicationID, int patientID, int numRefills, Order o) {
//		this.medicationID = medicationID;
//		this.patientID = patientID;
//		this.numRefills = numRefills;
//		
//		ordersForPrescription.add(o);
//	}
	
	public Prescription(int medicationID, int patientID, int originalNumOfRefills) {
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.originalNumOfRefills = originalNumOfRefills;
	}
	
	public Prescription(int prescriptionNum, int medicationID, int patientID, int originalNumOfRefills) {
		this.prescriptionNum = prescriptionNum;
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.originalNumOfRefills = originalNumOfRefills;
	}
	
	public void setPrescriptionNum(int prescriptionNum) {
		this.prescriptionNum = prescriptionNum;
	}
	
	public int getPrescriptionNum() {
		return prescriptionNum;
	}
	
	public int getMedicationID() {
		return medicationID;
	}

	public void setMedicationID(int medicationID) {
		this.medicationID = medicationID;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getOriginalNumOfRefills() {
		return originalNumOfRefills;
	}

	public void setOriginalNumOfRefills(int originalNumOfRefills) {
		this.originalNumOfRefills = originalNumOfRefills;
	}

}
