package org.ibs.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс вывода рузультат запроса ResultSet в консоль
 * @author Andrey_Zhizhov
 */
public class DisplayResult {

    private static DisplayResult displayResult;
    private DisplayResult() {}

    /**
     * Линивая инициализация DisplayResult
     * @return displayResult - displayResult
     */
    public static DisplayResult getDisplayResult() {
        if (displayResult == null) {
            displayResult = new DisplayResult();
        }
        return displayResult;
    }

    public void printResult(ResultSet resultSet) throws SQLException {
        boolean isExotic;
        while (resultSet.next()) {
            int id = resultSet.getInt("FOOD_ID");
            String name = resultSet.getString("FOOD_NAME");
            String type = resultSet.getString("FOOD_TYPE");
            byte exotic = resultSet.getByte("FOOD_EXOTIC");
            if (exotic == 1) isExotic = true; else isExotic = false;
            System.out.printf("%d, %s, %s, %b%n", id, name, type, isExotic);
        }
        System.out.println("#####");
    }
}
