package middleLayer.Orders;

import java.util.ArrayList;

public class Prescription { // prescription forms by doctor
	int prescriptionNum;
	int medicationID;
	long patientID;
	int originalNumOfRefills;    // numbers of refills prescribed by doctor
	
	// constructor for front end
	public Prescription(int medicationID, long patientID, int originalNumOfRefills) {
		this.medicationID = medicationID;
		this.patientID = patientID;
		this.originalNumOfRefills = originalNumOfRefills;
	}
	
	// constructor for reading from database
	public Prescription(int prescriptionNum, int medicationID, long patientID, int originalNumOfRefills) {
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

	public long getPatientID() {
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
	
	@Override 
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		Prescription pres = (Prescription) obj;
		if(this.medicationID != pres.medicationID || 
			this.originalNumOfRefills != pres.originalNumOfRefills ||
			this.patientID != pres.patientID ||
			this.prescriptionNum != pres.prescriptionNum) {
			return false;
		}
		return true;
	}

}
