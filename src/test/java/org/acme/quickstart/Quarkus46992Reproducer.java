package org.acme.quickstart;

import io.quarkus.narayana.jta.QuarkusTransactionException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.RollbackException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class Quarkus46992Reproducer {
    @Inject
    QuarkusJpaService service;

    @Test
    void testCreateOk() {
        var id = UUID.randomUUID()
                .toString();
        assertDoesNotThrow(() -> service.createEntity(id, "ok"));
    }

    @Test
    void testCreateKo() {
        var id = UUID.randomUUID()
                .toString();
        assertDoesNotThrow(() -> service.createEntity(id, "ok"));
        var exception = assertThrows(QuarkusTransactionException.class, () -> service.createEntity(id, "ko"));
        assertEquals(RollbackException.class, exception.getCause().getClass());
        RollbackException cause =(RollbackException) exception.getCause();
        assertEquals(cause.getCause(),cause);
    }
}