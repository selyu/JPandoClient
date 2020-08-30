import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.selyu.pando.client.PandoClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Tests {
    private PandoClient client;

    @BeforeEach
    void before() {
        client = new PandoClient("https://localhost:5001/api/v1/", "");
    }

    @Test
    void user00_get_all() {
        assertNotNull(client.getAllUsers());
    }

    @Test
    void user01_get_non_existent() {
        assertNotNull(client.getUserById(UUID.randomUUID()));
    }

    @Test
    void user02_create() {
        assertNotNull(client.createUser(UUID.fromString("88352a08-321e-48d0-83aa-1dca5e803ce2"), "L1LLIAN"));
    }
}
