package org.tehranuniversity.college.utils.menuutils;
import static org.tehranuniversity.college.utils.printutils.IOUtils.*;

public class MenuUtil {
    public static void showMenu() {
        print("Please select from menu  :");
        print("[1] -> Register new student");
        print("[2] -> Inquiry capacity");
        print("[3] -> Edit student information");
        print("[4] -> Cancel registration");
        print("[5] -> Inquiry student information");
        print("[0] -> Exit");
    }
}
