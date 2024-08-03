package org.tehranuniversity.college.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.tehranuniversity.college.handlers.StudentHandler.getCountOfNotDeletedStudentByYear;

public class CapacityHandler {


    // old method
   /* public static void loadCapacity() {
        try {
            LocalDate date =LocalDate.now();
            capacity =   getYearTotalCapacity(date.getYear());
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    // new method
    public static Integer loadCapacity() {
        try {
            LocalDate date =LocalDate.now();
            return getYearTotalCapacity(date.getYear());
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private static Integer getYearTotalCapacity(int year) throws SQLException, ClassNotFoundException {
        int count = 0;
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String query = "SELECT cnt FROM public.capacity where year = "+ year + ";" ;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        connection.commit();
        statement.close();
        connection.close();
        System.out.println(" success db!!!");
        return count;
    }



    public static void showCapacity(int totalCapacity) throws SQLException, ClassNotFoundException {
        LocalDate date = LocalDate.now();
        Integer count = getCountOfNotDeletedStudentByYear(date.getYear(),totalCapacity);
        System.out.println("Remain registration capacity in year " + date.getYear() + " " + " is :" + count);
    }

}
