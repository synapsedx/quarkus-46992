package org.acme.quickstart;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.narayana.jta.TransactionExceptionResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.UUID;

@ApplicationScoped
public class QuarkusJpaService {

    @Inject
    SessionFactory sessionFactory;

    public QuarkusEntity createEntity(String id, String name) {
        return QuarkusTransaction.requiringNew()
                .timeout(10)
                .exceptionHandler(throwable -> TransactionExceptionResult.ROLLBACK)
                .call(() -> doCreateEntity(id, name));
    }

    QuarkusEntity doCreateEntity(String id, String name) {
        try (Session session = sessionFactory.openSession()) {
            var quarkusEntity = new QuarkusEntity();
            quarkusEntity.setId(id);
            quarkusEntity.setName(name);
            session.persist(quarkusEntity);
            return quarkusEntity;
        }
    }
}