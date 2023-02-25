package middleLayer;

public class Merchandise{
	static int medicationIDClassVar = 0;
	
	int medicationID;
    String name;
    int quantity;
    double price;
    MERCHANDISE_TYPE type;
    MERCHANDISE_FORM form;
    boolean isOTC;

    // constructor
    public Merchandise(String name, int quantity, double price, MERCHANDISE_TYPE type, MERCHANDISE_FORM form, boolean OTC){
    	medicationIDClassVar++;
    	
    	this.medicationID = medicationIDClassVar;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.form = form;
        this.isOTC = OTC;
    }

    // below methods are all getters/setters for class variables
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

    public void setForm(boolean isOTC){
        this.isOTC = isOTC;
    }
    
    // outputs merchandise object by specifying all instance variables
    @Override
    public String toString(){
        return "Name: " + name + ", Quantity: " + quantity + ", Price: " + price + ", Type: " + type + ", Form: " + form + ", isOTC: " + isOTC + "\n";
    }

}