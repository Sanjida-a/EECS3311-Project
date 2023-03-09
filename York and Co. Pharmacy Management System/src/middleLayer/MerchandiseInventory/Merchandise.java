package middleLayer.MerchandiseInventory;

public class Merchandise{
//	static int medicationIDClassVar = 6; //Cant use static variable because does not work correctly with database.
	int medicationID;
    String name;
    int quantity;
    double price;
    MERCHANDISE_TYPE type;
    MERCHANDISE_FORM form;
    boolean isOTC;
    String description;
    private boolean isValid; // added due to column added in database that depicts whether a medication has been deleted or not

    // constructor without description and ID? do we need constructor with same as this but with description or no are we not allowing admins to add description first only add later?
    public Merchandise(String name, int quantity, double price, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean isOTC){
//    	medicationIDClassVar++;
//    	
//    	this.medicationID = medicationIDClassVar;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.form = form;
        this.isOTC = isOTC;
        this.setIsValid(true); // automatic??
    }
    
    // constructor for when reading from database (ID is being read and isValid is being read --> need those as parameters)
    public Merchandise(int medicationID, String name, int quantity, double price, MERCHANDISE_TYPE type, 
    		            MERCHANDISE_FORM form, boolean isOTC, String description, boolean isValid){
    	this.medicationID = medicationID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.form = form;
        this.isOTC = isOTC;
        this.description = description;
        this.setIsValid(isValid);
    }
    
    public Merchandise(Merchandise m){
    	this.medicationID = m.medicationID;
        this.name = m.name;
        this.quantity = m.quantity;
        this.price = m.price;
        this.type = m.type;
        this.form = m.form;
        this.isOTC = m.isOTC;
        this.description = m.description;
        this.setIsValid(m.isValid());
    }

    // below methods are all getters/setters for class variables
    public int getMedicationID() {
    	return medicationID;
    }
    
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public MERCHANDISE_TYPE getType(){
        return type;
    }

    public void setType(MERCHANDISE_TYPE type){
        this.type = type;
    }

    public MERCHANDISE_FORM getForm(){
        return form;
    }

    public void setForm(MERCHANDISE_FORM form){
        this.form = form;
    }

    public boolean getisOTC(){
        return isOTC;
    }
    
    public boolean getisValid(){
        return isValid();
    }

    public void setForm(boolean isOTC){
        this.isOTC = isOTC;
    }
    
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
    
    // outputs merchandise object by specifying all instance variables
    @Override
    public String toString(){
        return "ID: " + medicationID + ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price + ", Type: " + type + ", Form: " + form + ", isOTC: " + isOTC + ", Description: " + description + "\n";
    }

	public boolean isValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

}