package com.automation.selenium.clase6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class MySqlPersonasViewer {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "root";

    private MySqlPersonasViewer() {
    }

    public static void main(String[] args) {
        String url = args.length > 0 ? args[0] : DEFAULT_URL;
        String user = args.length > 1 ? args[1] : DEFAULT_USER;
        String password = args.length > 2 ? args[2] : DEFAULT_PASSWORD;

        String query = "SELECT id, nombre, apellido, edad, email, telefono FROM Personas";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontro el driver MySQL. Revisa la dependencia mysql-connector-j.");
            e.printStackTrace();
            return;
        }

        try (
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()
        ) {
            printHeader(resultSet.getMetaData());

            while (resultSet.next()) {
                System.out.printf(
                    "%-3d %-12s %-12s %-4d %-30s %-15s%n",
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getInt("edad"),
                    resultSet.getString("email"),
                    resultSet.getString("telefono")
                );
            }
        } catch (SQLException e) {
            System.err.println("No fue posible consultar la tabla Personas.");
            System.err.println("URL usada: " + url);
            e.printStackTrace();
        }
    }

    private static void printHeader(ResultSetMetaData metaData) throws SQLException {
        System.out.printf(
            "%-3s %-12s %-12s %-4s %-30s %-15s%n",
            metaData.getColumnName(1),
            metaData.getColumnName(2),
            metaData.getColumnName(3),
            metaData.getColumnName(4),
            metaData.getColumnName(5),
            metaData.getColumnName(6)
        );
        System.out.println("-------------------------------------------------------------------------------");
    }
}
