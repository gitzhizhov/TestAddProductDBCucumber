package org.ibs.managers;

import java.sql.*;

/**
 * Класс управления соединением с БД
 * @author Andrey_Zhizhov
 */
public class ConnectionManager {

    private static ConnectionManager connectionManager;

    private static Connection connection;


    /**
     * Ленивая инициализация ConnectionManager
     *
     * @return PageManager
     */
    public static ConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManager();
        }
        return connectionManager;
    }

    /***
     * Метод ленивой инициализации соединения
     * @return Connection - возвращает Connection
     */
    public Connection getConnection(String stringConnDB,String login, String password) {
        if (connection == null) {
            initConnection(stringConnDB, login, password);
        }
        return connection;
    }

    /***
     * Метод получения соединения для PreparedStatement
     * @return Connection - возвращает Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /***
     * Метод инициализации соединения
     */
    private void initConnection(String stringConnDB, String login, String password) {
        try {
            connection = DriverManager.getConnection(stringConnDB,login,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод закрытия соединения с БД
     */
    public void quitConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
