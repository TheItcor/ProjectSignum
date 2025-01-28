import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menu() {
        System.out.println("      Project Signum ALPHA v 0.1");
        System.out.println("      Author: Itcor (Aleksandr Shewchuk)");
    }

    public static void line() {
        System.out.println("===============================================");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        boolean run = true;
        while (run == true) {
            // Начало консольного интерфейса
            line();
            menu();
            line();
            System.out.println("[h] - Помощь");
            System.out.println("[e] - Выход из приложения");
            System.out.println("[l] - Список логинов и паролей, редактирование");
            System.out.println("[n] - Добавить новый логин и пароль");
            System.out.println("[g] - Сгенерировать пароль");
            line();
            System.out.println("Ваш ввод (английские буквы):");


            // Выбор пользователя
            String userChoice = scanner.nextLine();

            switch (userChoice) {
                // Вывод текста о помощи
                case ("h"):
                    clean();
                    //help()
                    break;

                // Выход из программы
                case ("e"):
                    clean();
                    run = false;
                    break;

                // Список логинов и паролей
                case ("l"):
                    clean();
                    //passwordsList()
                    break;

                // Добавить новый логин и пароль к словарю
                case ("n"):
                    clean();
                    //addNewPassword()
                    break;

                // Генератор паролей
                case ("g"):
                    clean();
                    //passwordsGenerator()
                    break;

            }


            // Очистка консоли после каждой итерации
            clean();
        }
    }
}