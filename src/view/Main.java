package view;

import controller.StudentController;
import model.Student;
import util.StudentControllerImp;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentController studentController = new StudentControllerImp();

        System.out.println("\n" +
                "   ██████╗    ███████╗    ████████╗     █████╗     ██████╗ \n" +
                "  ██╔════╝    ██╔════╝    ╚══██╔══╝    ██╔══██╗    ██╔══██╗\n" +
                "  ██║         ███████╗       ██║       ███████║    ██║  ██║\n" +
                "  ██║         ╚════██║       ██║       ██╔══██║    ██║  ██║\n" +
                "  ╚██████╗    ███████║       ██║       ██║  ██║    ██████╔╝\n" +
                "   ╚═════╝    ╚══════╝       ╚═╝       ╚═╝  ╚═╝    ╚═════╝ \n" +
                "                                                           \n");

        View view = new View();
        view.loadData();
        view.displayMenu();
    }
    }

