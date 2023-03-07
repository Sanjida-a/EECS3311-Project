
package testCases.Unit;


import org.junit.jupiter.api.Test;
import middleLayer.AuthenticateUser;
import presentation.USER;
import org.junit.jupiter.api.Assertions;


import static org.junit.jupiter.api.Assertions.*;

class AuthenticateUserTest {

    @Test
    void getInstance() {

    }
    @Test
    void checkUserValidTest() { //for incorrect entry
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(null, val.checkUserValid(2344445, 2344445));
    }

    @Test
    void checkUserValidOwnerLogin() {
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.OWNER, val.checkUserValid(1111111111, 11111111));
    }

    @Test
    void checkUserValidPharamcistLogin() {
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PHARMACIST, val.checkUserValid(1234567890, 12345678));
    }

    @Test
    void checkUserValidPatientLogin() {
        AuthenticateUser val = AuthenticateUser.getInstance();
        assertEquals(USER.PATIENT, val.checkUserValid(1111122222, 11111222));
    }
}
