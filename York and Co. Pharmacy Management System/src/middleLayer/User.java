package middleLayer;

public abstract class User {

	public int username;
	public int password;
	
	public void setUsername(int username) {
		this.username = username;
	}
	
	public void setPassword(int password) {
		this.password = password;
	}
	
	public int getUsername() {
		return this.username;
	}
	
	public int getPassword() {
		return this.password;
	}
	
}
