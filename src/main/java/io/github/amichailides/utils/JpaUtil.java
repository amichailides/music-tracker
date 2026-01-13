package io.github.amichailides.utils;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;

public class JpaUtil {
    private static final EntityManagerFactory EMF;

    static {
        try {
            String dbURL = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_User");
            String dbPassword = System.getenv("DB_PASSWORD");

            HashMap<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url", dbURL);
            properties.put("jakarta.persistence.jdbc.user", dbUser);
            properties.put("jakarta.persistence.jdbc.password", dbPassword);

            EMF = Persistence.createEntityManagerFactory("myPostgresUnit", properties);
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }
}
