package com.example.api.config;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



public class TransactionHelper {

    public static void executeUpdate(Session session, String sql) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public static void executeUpdateWithParams(Session session, String sql, Object... params) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            setParameters(query, params);
            query.executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    private static void setParameters(Query query, Object... params) {
        if (params != null) {
            for (int i = 0; i < params.length; i += 2) {
                if (params[i] instanceof String && i + 1 < params.length) {
                    query.setParameter((String) params[i], params[i + 1]);
                } else {
                    throw new IllegalArgumentException("Parameters must be provided in 'name', value pairs.");
                }
            }
        }
    }
}
