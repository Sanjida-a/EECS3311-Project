package middleLayer;

public class NegativeInputException extends Exception {

    public NegativeInputException(String statement){
        super(statement); // this exception class was made for whenever user enters a negative value for double/int input fields that should be positive
    }
	
}
