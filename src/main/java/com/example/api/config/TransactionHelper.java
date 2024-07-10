package com.example.api.config;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;

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
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery(sql);
            for (int i = 0; i < params.length; i += 2) {
                String paramName = (String) params[i];
                Object paramValue = params[i + 1];
                query.setParameter(paramName, paramValue);
            }
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    public static void executeDeleteWithParams(Session session, String sql, Object... params) {
        Query query = session.createNativeQuery(sql);
        setParameters(query, params);
        query.executeUpdate();
    }
    private static void setParameters(Query query, Object... params) {
        if (params != null) {
            Map<String, Object> paramMap = new HashMap<>();
            for (int i = 0; i < params.length; i += 2) {
                if (params[i] instanceof String && i + 1 < params.length) {
                    paramMap.put((String) params[i], params[i + 1]);
                } else {
                    throw new IllegalArgumentException("Parameters must be provided in 'name', value pairs.");
                }
            }
            paramMap.forEach(query::setParameter);
        }
    }
}
