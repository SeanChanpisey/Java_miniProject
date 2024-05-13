package view;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n" +
                "   ██████╗    ███████╗    ████████╗     █████╗     ██████╗ \n" +
                "  ██╔════╝    ██╔════╝    ╚══██╔══╝    ██╔══██╗    ██╔══██╗\n" +
                "  ██║         ███████╗       ██║       ███████║    ██║  ██║\n" +
                "  ██║         ╚════██║       ██║       ██╔══██║    ██║  ██║\n" +
                "  ╚██████╗    ███████║       ██║       ██║  ██║    ██████╔╝\n" +
                "   ╚═════╝    ╚══════╝       ╚═╝       ╚═╝  ╚═╝    ╚═════╝ \n" +
                "                                                           \n");

        View view = new View();
        view.displayMenu();
        view.closeScanner();
    }
    }

