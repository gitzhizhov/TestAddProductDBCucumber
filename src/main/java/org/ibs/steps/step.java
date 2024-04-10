package org.ibs.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.И;
import org.ibs.managers.ConnectionManager;
import org.ibs.managers.DisplayResult;
import org.ibs.managers.RequestsManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class step {

    private static final String connString = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static final String login = "user";
    private static final String password = "pass";

    private static final ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
    Connection connection = connectionManager.getConnection(connString, login, password);
    protected static RequestsManager rm = RequestsManager.getRequestsManager();
    protected static DisplayResult displayResult = DisplayResult.getDisplayResult();


    @И("подключение к БД")
    public void connectToDB() throws SQLException {
        connectionManager.getConnection(connString, login, password);
    }

    @И("добавляем товар {}, {}, {}")
    public void addRow(String name, String type, String exotic) {
        String request = "INSERT INTO food(food_name, food_type, food_exotic) values (?, ?, ?)";
        String nameFood = name;
        String typeFood = type;
        String ex = exotic;
        boolean isExotic;
        if (ex.equals("TRUE")) isExotic = true; else isExotic = false;
        rm.insertPrepStatement(request, nameFood, typeFood, isExotic);
    }

    @И("проверяем, что находится в таблице")
    public void simpleSelect() {
        ResultSet result = rm.selectRequest("select * from food");
        printTab(result);

    }

    @И("удаляем товар {}")
    public void deleteRow(String name) {
        String request = "DELETE FROM food WHERE food_name = ?";
        String nameFood = name;
        rm.deleteRowPS(request,nameFood);
    }

    @И("выводим результат")
    public void printTab(ResultSet set) {
        try {
            displayResult.printResult(set);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @И("закрыть соединение к БД")
    public void quitConnect() {
        connectionManager.quitConnection();
    }

    public void передадим_в_метод_datatable(DataTable dataTable) {
        for (int i = 0; i < dataTable.width(); i++) {
            System.out.println(dataTable.column(i));
        }
    }
}
