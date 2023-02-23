package middleLayer;

import presentation.*;

public class pharmacyAppMain {
	
	// main method to initiate our program
	public static void main(String[] args) {
		
		//DisplayLogin logIn = new DisplayLogin();
		//logIn.displayLogin();
		DisplayInitialScreen screen = new DisplayInitialScreen();
		screen.displayInitialScreen(USER.GUEST);
		
	}
	
}
