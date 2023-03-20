package middleLayer.MerchandiseInventory;

public class Merchandise{
	int medicationID;
    String name;
    int quantity;
    double price;
    MERCHANDISE_TYPE type;
    MERCHANDISE_FORM form;
    boolean isOTC;
    String description;
    private boolean isValid; // whether a medication has been deleted or not

    // constructor for front end
    public Merchandise(String name, int quantity, double price, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean isOTC){
        this.name = name;
        this.quantity = quantity;
        this.price = (Math.floor(price*100))/100;
        this.type = type;
        this.form = form;
        this.isOTC = isOTC;
        this.setIsValid(true); 
    }
    
    // constructor for when reading from database 
    public Merchandise(int medicationID, String name, int quantity, double price, MERCHANDISE_TYPE type, 
    		            MERCHANDISE_FORM form, boolean isOTC, String description, boolean isValid){
    	this.medicationID = medicationID;
        this.name = name;
        this.quantity = quantity;
        this.price = (Math.floor(price*100))/100;
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
        return "ID: " + medicationID + ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price + ", Type: " + type + ", Form: " + form + ", isOTC: " + isOTC + ", Description: " + description + ", isValid: " + isValid + "\n";
    }

	public boolean isValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}
	@Override
	public boolean equals(Object obj) {
		Merchandise comparator = (Merchandise)obj;
		if(this.medicationID != comparator.medicationID|| 
			!this.name.equals(comparator.getName()) || 
			this.quantity != comparator.quantity || 
			Double.compare(this.price, comparator.price) != 0 || 
			this.type != comparator.type || 
			this.form != comparator.form ||
			this.isOTC != comparator.isOTC || 
			!this.description.equals(comparator.description) ||
			this.isValid != comparator.isValid) {
			return false;
		}
		return true;
	}

}