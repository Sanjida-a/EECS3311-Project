class Inventory{
    ArrayList<Merchandise> list = new ArrayList<Merchandise>();

    public void display(){
        for (int i = 0; i < list.size(); i ++){
            System.out.print(""+list.get(i).name+", ");
            System.out.print(""+list.get(i).quantity+", ");
            System.out.print(""+list.get(i).price+", ");
            System.out.print(""+list.get(i).type+", ");
            System.out.print(""+list.get(i).form+", ");
            System.out.print(""+list.get(i).isOTC+"\n");
        }
    }

    //increase quantity
    public void increaseQuantity(String name, int quantity){
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name == name){
                list.get(i).quantity += quantity;
            }
            //add notification here
        }
    }

    //decrease quantity
    public void decreaseQuantity(String name, int quantity){
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name == name){
                list.get(i).quantity -= quantity;
            }
            //add notification here
        }
    }
    public void delete(String name){
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name == name){
                list.remove(i);
            }
        }
    }
}