package org.tehranuniversity.college.handlers;

import org.tehranuniversity.college.entities.Student;
import org.tehranuniversity.college.exceptions.StudentNotFoundException;

import java.sql.*;
import java.time.LocalDate;

import static org.tehranuniversity.college.utils.printutils.IOUtils.*;

public class StudentHandler {

    public static void showStudentInformation() throws StudentNotFoundException, SQLException, ClassNotFoundException {
        Student student = getStudentByInputNationalCode();
        print(student.toString());
    }

    public static void cancelRegistration() throws StudentNotFoundException, SQLException, ClassNotFoundException {
        Student student = getStudentByInputNationalCode();
        student.setDeleted(true);
        updateData(student);
        System.out.println("Student registration canceled!");
    }

    public static void updatedStudentData() throws StudentNotFoundException, SQLException, ClassNotFoundException {
        Student student = getStudentByInputNationalCode();
        if (student == null) return;
        print("For editing information please enter new data or enter to ignore.");
        print("First name :" + student.getFirstName());
        String firstName = getInput();
        if (!firstName.equals("")) {
            student.setFirstName(firstName);
        }
        print("Last name :" + student.getLastName());
        String lastName = getInput();
        if (!lastName.equals("")) {
            student.setLastName(lastName);
        }
        print("Address :" + student.getAddress());
        String address = getInput();
        if (!address.equals("")) {
            student.setAddress(address);
        }
        updateData(student);
        System.out.println("Information updated successfully!");
    }

    public static Student getStudentByInputNationalCode() throws StudentNotFoundException, SQLException, ClassNotFoundException {
        System.out.println("Please enter student national code:");
        Student student = loadStudentByNationalCode(getInput());
        if (student == null) {
            System.out.println("Not found any student with national code");
            throw new StudentNotFoundException();
        }
        return student;
    }
//old method
   /* public static void updateData(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String queryCommand = "UPDATE public.universitystudent SET first_name = '"+student.getFirstName() +"' ," +
                " " + "last_name = '"+student.getLastName()+"'," +
                ""+ "score = "+student.getScore()+"," +
                ""+ "address = '"+student.getAddress()+"'," +
                "" + "national_code = '"+student.getNationalCode()+"'," +
                ""+ "is_deleted = "+student.getDeleted()+" " +
                "" + " where id = " + student.getId();

        statement.executeUpdate(queryCommand);
        connection.commit();
        statement.close();
        connection.close();
        System.out.println("student registration updated  in db!!!");
    }
*/

    // new method
    public static void updateData(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectManager.getConnection();
        connection.setAutoCommit(false);
        String queryCommand = "UPDATE public.universitystudent SET first_name = ?," +
                " " + "last_name = ?," +
                ""+ "score = ?," +
                ""+ "address =?," +
                "" + "national_code =?," +
                ""+ "is_deleted =? where id = ?" ;
        PreparedStatement preparedStatement = connection.prepareStatement(queryCommand);
        preparedStatement.setString(1,student.getFirstName());
        preparedStatement.setString(2,student.getLastName());
        preparedStatement.setFloat(3,student.getScore());
        preparedStatement.setString(4,student.getAddress());
        preparedStatement.setString(5,student.getNationalCode());
        preparedStatement.setBoolean(6,student.getDeleted());
        preparedStatement.setInt(7,student.getId());
preparedStatement.executeUpdate();
        connection.commit();
        preparedStatement.close();
        connection.close();
        System.out.println("student registration updated  in db!!!");
    }




    //old method
    /*
    public static Student loadStudentByNationalCode(String input) throws SQLException, ClassNotFoundException, StudentNotFoundException {
        Student student = new Student();
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String query = "SELECT * FROM public.universitystudent where national_code = '"+ input.trim() +  " ';" ;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
           student.setId(resultSet.getInt("id"));
           student.setFirstName(resultSet.getString("first_name"));
           student.setLastName(resultSet.getString("last_name"));
           student.setScore(resultSet.getFloat("score"));
           student.setStudentNumber(resultSet.getInt("student_number"));
           student.setAddress(resultSet.getString("address"));
           student.setNationalCode(resultSet.getString("national_code"));
           student.setDeleted(resultSet.getBoolean("is_deleted"));
           student.setYear(resultSet.getInt("year"));
        }else {
            System.out.println("Student not found with national code :" + input.trim());
            throw new StudentNotFoundException();
        }
        connection.commit();
        statement.close();
        connection.close();
        return student;
    }*/


    public static Student loadStudentByNationalCode(String input) throws SQLException, ClassNotFoundException, StudentNotFoundException {
        Student student = new Student();
        Connection connection = ConnectManager.getConnection();

        connection.setAutoCommit(false);
        String query = "SELECT * FROM public.universitystudent where national_code = ?" ;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,input.trim());
                ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
           student.setId(resultSet.getInt("id"));
           student.setFirstName(resultSet.getString("first_name"));
           student.setLastName(resultSet.getString("last_name"));
           student.setScore(resultSet.getFloat("score"));
           student.setStudentNumber(resultSet.getInt("student_number"));
           student.setAddress(resultSet.getString("address"));
           student.setNationalCode(resultSet.getString("national_code"));
           student.setDeleted(resultSet.getBoolean("is_deleted"));
           student.setYear(resultSet.getInt("year"));
        }else {
            System.out.println("Student not found with national code :" + input.trim());
            throw new StudentNotFoundException();
        }
        connection.commit();
        preparedStatement.close();
        connection.close();
        System.out.println("loading student with national code is okay!");
        return student;
    }




    //old method
/*
    public static Integer getCountOfNotDeletedStudentByYear(int year) throws SQLException, ClassNotFoundException {
        int count = 0;
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String query = "SELECT count(*) FROM public.universitystudent where year = "+ year + " and is_deleted = false"+ ";" ;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        connection.commit();
        statement.close();
        connection.close();
        System.out.println(" updated db!!!");
        int result = (capacity - count);
        return result;
    }
*/
    //new method
    public static Integer getCountOfNotDeletedStudentByYear(int year,int capacity) throws SQLException, ClassNotFoundException {
        int count = 0;
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String query = "SELECT count(*) FROM public.universitystudent where year = "+ year + " and is_deleted = false"+ ";" ;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        connection.commit();
        statement.close();
        connection.close();
        System.out.println(" updated db!!!");
        int result = (capacity - count);
        return result;
    }

    public static void registerStudent() throws SQLException, ClassNotFoundException {
        Student student = new Student();
        System.out.println("Please enter first name:");
        student.setFirstName(getInput());
        System.out.println("Please enter last name:");
        student.setLastName(getInput());
        System.out.println("Please enter score:");
        student.setScore(Float.parseFloat(getInput()));
        System.out.println("Please enter student number::");
        student.setStudentNumber(Integer.parseInt(getInput()));
        System.out.println("Please enter address:");
        student.setAddress(getInput());
        System.out.println("Please enter national code:");
        student.setNationalCode(getInput());
        student.setDeleted(false);
        LocalDate date = LocalDate.now();
        System.out.println("Please enter year:");
        student.setYear(date.getYear());
        insertToDB(student);
        System.out.println("Student registration successfully!");
    }

    public static void insertToDB(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectManager.getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);

        // with id : because postgresql have not set on auto increment primary key alter table
      /*  String queryCommand = "INSERT INTO public.universityStudent(" +
                "id, first_name, last_name, score, student_number, address, national_code, is_deleted, year)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? );";*/
//   // without id : because postgresql have  set on auto increment primary key alter table
        String queryCommand = "INSERT INTO public.universitystudent(" +
                " first_name, last_name, score, student_number, address, national_code, is_deleted, year)"
                +
                " VALUES ( '"
                +student.getFirstName()
                + "', '"+ student.getLastName()
                +"', "+student.getScore()
                + ","+student.getStudentNumber()
                +", '"+student.getAddress()
                +"', '"+student.getNationalCode()
                +"', "+ false
                +", "+student.getYear()
                +" );";
        statement.executeUpdate(queryCommand);
        connection.commit();
        statement.close();
        connection.close();
        System.out.println(" updated db!!!");
    }

}
