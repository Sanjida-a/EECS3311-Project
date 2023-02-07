package middleLayer;

class Merchandise{
    String name;
    int quantity;
    double price;
    String type;
    String form;
    boolean isOTC;

    //constructor
    public Merchandise(String n, int q, double p, String t, String f, boolean OTC){
        this.name = n;
        this.quantity = q;
        this.price = p;
        this.type = t;
        this.form = f;
        this.isOTC = OTC;
    }

    //methods for class variables

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public String getQuantity(){
        return quantity;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getForm(){
        return form;
    }

    public void setForm(String form){
        this.form = form;
    }

    public boolean getisOTC(){
        return isOTC;
    }

    public void setForm(boolean isOTC){
        this.isOTC = isOTC;
    }

    public String toString(){
        return "Name: " + name + ", Quantity: " + quantity + ", Price: " + price + ", Type: " + type + ", Form: " + form + ", isOTC: " + isOTC + "\n";
    }

}