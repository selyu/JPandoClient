import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.selyu.pando.client.PandoClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    private PandoClient client;

    @BeforeEach
    void before() {
        client = new PandoClient("https://localhost:5001/api/v1/", "");
    }

    @Test
    void ping() {
        assertEquals("PONG!", client.ping());
    }
}
