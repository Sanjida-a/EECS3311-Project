package middleLayer;

class Merchandise{
    String name;
    int quantity;
    double price;
    String type;
    String form;
    boolean isOTC;

    public Merchandise(String n, int q, double p, String t, String f, boolean OTC){
        this.name = n;
        this.quantity = q;
        this.price = p;
        this.type = t;
        this.form = f;
        this.isOTC = OTC;
    }



}