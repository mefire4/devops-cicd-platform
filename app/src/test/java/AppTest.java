
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void getMessageReturnsExpectedMessage() {
        assertEquals(
            "Mon premier pipeline CI/CD",
            App.getMessage()
        );
    }
}

