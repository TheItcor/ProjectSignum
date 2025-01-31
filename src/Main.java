import java.io.File;
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

    ///              ============== !!! Особое внимание!!! ==============
    static String version = "ALPHA v 0.5";
    ///              ============== !!! Особое внимание!!! ==============



    // Путь к настройкам
    static final String settingsTxtPath = "data" + File.separator + "Settings.txt";
    static Path pathS = Paths.get(settingsTxtPath);

    public static void title() {
        line();
        System.out.println("      Project Signum " + version);
        System.out.println("      Author: Itcor (Aleksandr Shewchuk)");
        line();
    }

    public static void help() {
        title();
        line();
        System.out.println("""
                                Project Signum - это программа для сохранения\s
                                логинов и паролей. Программа сохраняет ваши
                                данные в зашифрованном виде, так чтобы злоумышленники
                                не смогли бы их прочитать. Программа не отправляет\s
                                данные пользователя куда-либо. Всё хранится исклю-
                                чительно на вашем ПК. Для того, чтобы воспользо-
                                ваться функциями программы в главном меню на-\s
                                пишите соответствующую букву в списке команд.
                                Так, к примеру введите букву 'n', чтобы создать\s
                                Новую связку логина и паролей. Вы также можете\s
                                воспользоваться документацией в README.MD для\s
                                большей информации.\s
                                """);
        line();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Нажмите любую кнопку чтобы продолжить");
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


        try (BufferedReader reader = new BufferedReader(new FileReader(settingsTxtPath))) {
            // Пропускаем две строки
            reader.readLine();
            reader.readLine();

            // Читаем третью строку
            itsStartedBefore = reader.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
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
            List<String> lines = Files.readAllLines(pathS);
            lines.set(2, "1");
            lines.set(0, selectLanguage);
            Files.write(pathS, lines);


        }
        // КОНЕЦ НАСТРОЙКИ ЯЗЫКА
        clean();



        // НАЧАЛО ЦИКЛА ПРОГРАММЫ
        boolean run = true;
        while (run) {
            clean(); // очищает консоль после каждой итерации с функциями (методами)



            // Начало консольного интерфейса
            title();
            System.out.println("""
                        Команды:\s
                        [h] - Помощь / О программе\s
                        [e] - Выход из приложения\s
                        [l] - Список логинов и паролей, редактирование\s
                        [n] - Добавить новый логин и пароль\s
                        [s] - Настройки программы\s
                        [g] - Сгенерировать пароль""");
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
                    title();
                    SignumManager.readAllSlpFiles();
                    line();
                    System.out.println("Нажмите, чтобы продолжить");
                    scanner.nextLine();
                    break;

                // Добавить новый логин и пароль к словарю
                case ("n"):
                    clean();
                    title();
                    SignumManager.newLoginAndPassword();
                    System.out.println("Нажмите, чтобы продолжить");
                    scanner.nextLine();
                    break;

                // Изменение настроек
                case ("s"):

                    boolean settingsNotDone = true;
                    while (settingsNotDone) {
                        clean();
                        title();
                        System.out.println("           Настройки");
                        System.out.println("[l ru/en/es] - изменить язык");
                        System.out.println("[y (число)] - изменить длину генерируемого пароля");
                        System.out.println("Нажмите любую кнопку чтобы продолжить");
                        String userChoiceSettings = scanner.nextLine();

                        String[] commandAndInput = userChoiceSettings.split(" ");

                        // Изменение языка
                        if (Objects.equals(commandAndInput[0], "l")) {
                            List<String> lines = Files.readAllLines(pathS);
                            switch (userChoiceSettings) {
                                case ("l ru"):
                                    lines.set(0, "ru");
                                    Files.write(pathS, lines);
                                    settingsNotDone = false;
                                    break;
                                case ("l en"):
                                    lines.set(0, "en");
                                    Files.write(pathS, lines);
                                    settingsNotDone = false;
                                    break;
                                case ("l es"):
                                    lines.set(0, "es");
                                    Files.write(pathS, lines);
                                    settingsNotDone = false;
                                    break;
                            }

                        // изменение длины генерируемого пароля
                        // Проверка: является ли первая буква y и есть ли только числа во второй части команды
                        } else if (Objects.equals(commandAndInput[0], "y") && commandAndInput[1].matches("\\d+")) {
                                List<String> lines = Files.readAllLines(pathS);
                                int number = Integer.parseInt(commandAndInput[1]);
                                lines.set(1, String.valueOf(number));
                                Files.write(pathS, lines);
                                settingsNotDone = false;
                                break;
                        } else {
                            System.out.println("ERROR: Неверный ввод");
                            scanner.nextLine();
                        }
                    }

                    clean();
                    title();
                    System.out.println("Настройки применены!");
                    line();
                    System.out.println("Нажмите любую кнопку чтобы продолжить");
                    scanner.nextLine();
                    break;

                // Генератор паролей
                case ("g"):
                    clean();
                    title();

                    int lengthPassword = 0;
                    try (BufferedReader reader = new BufferedReader(new FileReader(settingsTxtPath))) {
                        reader.readLine();

                        // Читаем вторую строку, где находится параметр размера для пароля
                        lengthPassword = Integer.parseInt(reader.readLine());
                    } catch (IOException e) {
                        System.out.println("ERROR: ошибка чтения файла: проверьте data/settings.txt");
                    }

                    System.out.printf("Ваш сгенерированный пароль (длина %d):\n\n", lengthPassword);
                    System.out.println(Generator.generatePassword(lengthPassword));
                    System.out.println("\n(Уже скопирован в буфер обмена)");
                    System.out.println("Нажмите любую кнопку чтобы продолжить");
                    scanner.nextLine();
                    break;

            }


            // Очистка консоли после каждой итерации
            clean();
        }
    }
}