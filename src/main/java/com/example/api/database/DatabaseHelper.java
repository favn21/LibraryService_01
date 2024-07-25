package com.example.api.database;
import com.example.api.config.LibraryDatabaseConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

public class DatabaseHelper {
    private static Session getSession() {
        return LibraryDatabaseConfiguration.getSession();
    }

    public static void clearTable(String tableName) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM " + tableName).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public static <T> List<T> getRecordsByField(Class<T> entityClass, String fieldName, Object value) {
        try (Session session = getSession()) {
            Query<T> query = session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE " + fieldName + " = :value", entityClass);
            query.setParameter("value", value);
            return query.getResultList();
        }
    }

    public static <T> T getRecordByField(EntityManager entityManager, Class<T> entityClass, String fieldName, Object value) {
        String query = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value";
        return entityManager.createQuery(query, entityClass)
                .setParameter("value", value)
                .getSingleResult();
    }

}
