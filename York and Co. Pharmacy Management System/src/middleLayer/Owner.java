package middleLayer;

public class Owner extends User {
	//having only this constructor avoids having an owner without a username and password
	public Owner(int username, int password) {
		this.username = username;
		this.password = password;
	}
}
