import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static String[] translations = Language.checkLanguage();
    static String helpText = translations[0];
    static String startText = translations[1];
    static String mainMenuText = translations[2];

    public static void title() {
        System.out.println("      Project Signum ALPHA v 0.2");
        System.out.println("      Author: Itcor (Aleksandr Shewchuk)");
    }

    public static void help() {
        title();
        line();
        System.out.println(helpText);
        line();


        Scanner scanner = new Scanner(System.in);
        System.out.println(startText);
        scanner.nextLine();
    }

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void line() {
        System.out.println("===============================================");
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String itsStartedBefore = null;


        // НАЧАЛО НАСТРОЙКИ ЯЗЫКА
        // Чтение Settings.txt
        // itsStartedBefore = ТРЕТЬЯ СТРОКА ФАЙЛА Settings.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Settings.txt"))) {
            // Пропускаем две строки
            reader.readLine();
            reader.readLine();

            // Читаем третью строку
            itsStartedBefore = reader.readLine();
        } catch (IOException e) {
            System.out.println("ERROR!");
        }

        while (Objects.equals(itsStartedBefore, "0")) {
            System.out.println("Выберите язык. Впишите 'ru' - для продолжения на русском языке.");
            System.out.println("Select language. Input 'en' for english language");
            System.out.println("Seleccionar idioma. Escribe 'es' para continuar en español.");
            String selectLanguage = scanner.nextLine();

            itsStartedBefore = switch (selectLanguage) {
                case "ru", "en", "es" -> "1";
                default -> itsStartedBefore;
            };

            // запись "пользователь впервые открывает программу?" значени "1" в 3-ю строку Settings.txt.
            // запись языка в Settings.txt 1-ая строка
            String filePath = "src/Settings.txt";
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);
            lines.set(2, "1");
            lines.set(0, selectLanguage);
            Files.write(path, lines);


        }
        // КОНЕЦ НАСТРОЙКИ ЯЗЫКА
        clean();



        // НАЧАЛО ЦИКЛА ПРОГРАММЫ
        boolean run = true;
        while (run) {
            clean(); // очищает консоль после каждой итерации с функциями (методами)



            // Начало консольного интерфейса
            line();
            title();
            line();
            System.out.println(mainMenuText);
            line();
            System.out.println("Ваш ввод (английские буквы):");


            // Выбор пользователя
            String userChoice = scanner.nextLine();

            switch (userChoice) {
                // Вывод текста о помощи
                case ("h"):
                    clean();
                    help();
                    break;

                // Выход из программы
                case ("e"):
                    clean();
                    run = false;
                    break;

                // Список логинов и паролей
                case ("l"):
                    clean();
                    //passwordsList();
                    break;

                // Добавить новый логин и пароль к словарю
                case ("n"):
                    clean();
                    //addNewPassword();
                    break;

                // Добавить новый логин и пароль к словарю
                case ("s"):
                    clean();
                    title();
                    line();
                    System.out.println("           Настройки");
                    System.out.println("[l] - изменить язык");
                    System.out.println("[y (число)] - изменить длину генерируемого пароля");
                    System.out.println(startText);
                    scanner.nextLine();

                    //settings();
                    break;

                // Генератор паролей
                case ("g"):
                    clean();
                    title();
                    line();

                    int lengthPassword = 0;
                    try (BufferedReader reader = new BufferedReader(new FileReader("src/Settings.txt"))) {
                        reader.readLine();

                        // Читаем вторую строку
                        lengthPassword = Integer.parseInt(reader.readLine());
                    } catch (IOException e) {
                        System.out.println("ERROR!");
                    }

                    System.out.printf("Ваш сгенерированный пароль (длина %d):\n\n", lengthPassword);
                    System.out.println(Generator.generatePassword(lengthPassword));
                    System.out.println("\n(Уже скопирован в буфер обмена)");
                    System.out.println(startText);
                    scanner.nextLine();
                    break;

            }


            // Очистка консоли после каждой итерации
            clean();
        }
    }
}