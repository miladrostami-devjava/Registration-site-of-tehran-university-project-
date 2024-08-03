package org.tehranuniversity;

import static org.tehranuniversity.college.handlers.CapacityHandler.loadCapacity;
import static org.tehranuniversity.college.handlers.CapacityHandler.showCapacity;
import static org.tehranuniversity.college.handlers.StudentHandler.*;
import static org.tehranuniversity.college.utils.menuutils.MenuUtil.showMenu;
import static org.tehranuniversity.college.utils.printutils.IOUtils.getInput;

public class TehranUniversity {
    public static  Integer capacity = 0;
    public static void main(String[] args) {

       capacity = loadCapacity();
        while (true) {
            try {
                showMenu();
Integer menu = Integer.parseInt(getInput());
                switch (menu) {
                    case 1:
                        registerStudent();
                        break;
                    case 2:
                        showCapacity(capacity);

                        break;
                    case 3:
                        updatedStudentData();
                        break;
                    case 4:
                        cancelRegistration();

                        break;
                    case 5:
                        showStudentInformation();

                        break;
                    case 0:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }


        }


    }













}
