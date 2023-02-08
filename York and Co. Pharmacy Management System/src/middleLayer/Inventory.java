package middleLayer;

import java.util.ArrayList;

import databaseDAO.MerchandiseDAO;
import databaseDAO.UserDAO;
class Inventory{
    private static Inventory singletonInstance = null;

    ArrayList<Merchandise> list = new ArrayList<Merchandise>();
 
    //Minh changed here
	private MerchandiseDAO _merDAO;
	
	public Inventory() {
		try {
			_merDAO = new MerchandiseDAO();
			list = _merDAO.getListOfMerchandise();
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
	}

    public static Inventory getInstance(){
        if (singletonInstance == null)
            singletonInstance = new Inventory();
        return singletonInstance;
    }

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
            //notifies low stock
            if(list.get(i).name==name){
                if(list.get(i).quantity < 3){
                    System.out.print(list.get(i).name+" low in stock : only " +list.get(i).quantity+ " left." );
                }
            }
        }
    }
    public void delete(String name){
        for (int i = 0; i < list.size(); i ++){
            if (list.get(i).name == name){
                list.remove(i);
            }
        }
    }
    public void addToInventory(Merchandise m){
        list.add(m);
    }
    public ArrayList<Merchandise> getMerchandise(){
    	return list;
    }
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		Inventory merList = Inventory.getInstance();
		for (Merchandise i : merList.getMerchandise())
			System.out.println(i);
	}

}
    
