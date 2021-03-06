import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.selyu.pando.client.PandoClient;
import org.selyu.pando.client.model.Rank;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void lookup00_get_non_existent() {
        assertTrue(client.getLookupById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void lookup01_get_non_existent() {
        assertTrue(client.getLookupByUsername("37g9Fy").isEmpty());
    }

    @Test
    void rank00_get_all() {
        assertNotNull(client.getAllRanks());
    }

    @Test
    void rank01_create() {
        assertTrue(client.createRank("123"));
    }

    @Test
    void rank02_get() {
        assertTrue(client.getRankByName("123").isPresent());
    }

    @Test
    void rank03_get_non_existent() {
        assertTrue(client.getRankById(new UUID(0, 0)).isEmpty());
    }

    @Test
    void rank04_edit() {
        Rank rank = client.getRankByName("123").orElseThrow();
        assertTrue(client.editRank(rank.getUuid(), "Color", "WHITE"));
    }

    @Test
    void rank05_delete() {
        Rank rank = client.getRankByName("123").orElseThrow();
        assertTrue(client.deleteRank(rank.getUuid()));
    }
}
