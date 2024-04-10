package org.ibs.managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Клаас менеджер запросов
 * @author Andrey_Zhizhov
 */
public class RequestsManager {

    /**
     * Менеджер запросов
     */
    private static RequestsManager requestsManager;

    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManager();

    private Statement statement;

    private PreparedStatement preparedStatement;

    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see RequestsManager
     */
    private RequestsManager() {}

    /**
     * Ленивая инициализация RequestsManager
     * @return requestsManager
     */
    public static RequestsManager getRequestsManager() {
        if (requestsManager == null) {
            requestsManager = new RequestsManager();
        }
        return requestsManager;
    }

    /**
     * Мутод ленивой инициализации Statement
     * @return statement - возвращает statement
     */
    public Statement getStatement() {
        if (statement == null) {
            initStatement();
        }
        return statement;
    }

    /**
     * Метод insert в БД с использованием PreparedStatement
     * @param request   - строка запроса
     * @param id        - id строки
     * @param nameFood  - имя товар
     * @param typeFood  - тип товара
     * @param isExotic  - экзотичный?
     */
    public void insertPrepStatement(String request, int id, String nameFood, String typeFood, boolean isExotic) {
        try {
            int ex;
            ex = (isExotic)? 1 : 0;
            initPreparedStatement(request);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nameFood);
            preparedStatement.setString(3, typeFood.toUpperCase());
            preparedStatement.setInt(4, ex);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод insert в БД с использованием PreparedStatement
     * @param request   - строка запроса
     * @param nameFood  - имя товар
     * @param typeFood  - тип товара
     * @param isExotic  - экзотичный?
     */
    public void insertPrepStatement(String request, String nameFood, String typeFood, boolean isExotic) {
        try {
            int ex;
            ex = (isExotic)? 1 : 0;
            initPreparedStatement(request);
            preparedStatement.setString(1, nameFood);
            preparedStatement.setString(2, typeFood.toUpperCase());
            preparedStatement.setInt(3, ex);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Матод select по всем значениям таблицы
     * @param request - строка запроса
     * @return  - ResultSet
     */
    public ResultSet selectRequest(String request) {
        try {
            return getStatement().executeQuery(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод удаления строки по названию товара
     * @param request   - строка запроса
     * @param nameFood  - название товара
     */
    public void deleteRowPS(String request, String nameFood) {
        try {
            initPreparedStatement(request);
            preparedStatement.setString(1, nameFood);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Инициализация PreparedStatement
      * @param request
     * @throws SQLException
     */
    private void initPreparedStatement(String request) throws SQLException {
        preparedStatement = connectionManager.getConnection().prepareStatement(request);
    }

    /**
     * Метод инициализации statement
     */
    private void initStatement() {
        try {
            statement = connectionManager.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
