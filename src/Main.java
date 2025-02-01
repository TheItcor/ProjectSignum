import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    ///              ============== !!! Особое внимание!!! ==============
    static String version = "BETA v 1.0";
    ///              ============== !!! Особое внимание!!! ==============




    // Путь к настройкам
    static final String settingsTxtPath = "data" + File.separator + "Settings.txt";
    static Path pathS = Paths.get(settingsTxtPath);

    public static void title() {
        line();
        System.out.println("\u001b[35m" + "      Project Signum " + version + "\u001b[0m");
        System.out.println("      \u001b[1mAuthor: Itcor (Aleksandr Shewchuk)\u001b[0m");
        line();
    }

    public static void help() {
        Map<String, Object> translations = TranslationManager.loadTranslations(language);

        title();
        System.out.println(translations.get("txtHelp"));
        line();


        Scanner scanner = new Scanner(System.in);
        System.out.println(translations.get("TxtEnterForContinue"));
        scanner.nextLine();
    }

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void line() {
        System.out.println("\u001b[33m" + "===============================================" + "\u001b[0m");
    }

    public static String language = "";

    // Проверка языка
    public static void checkLanguage() {
        language = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(settingsTxtPath))) {
            // Чтение первой строки в Settings.txt
            language = reader.readLine();
        } catch (IOException e) {
            System.err.println("ERROR" + e.getMessage());
        }

    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String itsStartedBefore = null;

        checkLanguage();


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
            System.err.println("ERROR: " + e.getMessage());
        }

        while (Objects.equals(itsStartedBefore, "0")) {
            System.out.println("Выберите язык. Впишите 'ru' - для продолжения на русском языке.");
            System.out.println("Select language. Input 'en' for english language");
            //System.out.println("Seleccionar idioma. Escribe 'es' para continuar en español."); - в будущем
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


        // НАЧАЛО ЦИКЛА ПРОГРАММЫ
        boolean run = true;
        while (run) {
            checkLanguage(); // Проверка языка при каждой итерации (Немного неудобно, но в прочем работает нормально)
            Map<String, Object> translations = TranslationManager.loadTranslations(language);
            clean(); // очищает консоль после каждой итерации с функциями (методами)

            // Начало консольного интерфейса
            title();
            System.out.println(translations.get("txtMainMenu"));
            line();
            System.out.print(">");


            // Выбор пользователя
            String userChoice = scanner.nextLine();

            switch (userChoice) {
                // Вывод текста о программе
                case ("h"):
                    clean();
                    help();
                    break;

                // Выход из программы
                case ("e"):
                    clean();
                    run = false;
                    break;

                // Вывод списока логинов и паролей
                case ("l"):
                    clean();
                    title();
                    SignumManager.readAllSlpFiles();
                    line();
                    System.out.println(translations.get("TxtEnterForContinue"));
                    scanner.nextLine();
                    break;

                // Добавить новый SLP
                case ("n"):
                    clean();
                    title();
                    SignumManager.newLoginAndPassword();
                    System.out.println(translations.get("TxtEnterForContinue"));
                    scanner.nextLine();
                    break;

                // Изменение настроек
                case ("s"):

                    boolean settingsNotDone = true;
                    while (settingsNotDone) {
                        clean();
                        title();
                        System.out.println(translations.get("txtSettingsMenu"));
                        line();
                        System.out.print(">");
                        String userChoiceSettings = scanner.nextLine();

                        String[] commandAndInput = userChoiceSettings.split(" ");

                        // Выбор языка
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

                        // Изменение длины генерируемого пароля
                        // Проверка: является ли первая буква y и есть ли только числа во второй части команды
                        } else if (Objects.equals(commandAndInput[0], "y") && commandAndInput[1].matches("\\d+")) {
                            List<String> lines = Files.readAllLines(pathS);
                            int number = Integer.parseInt(commandAndInput[1]);
                            lines.set(1, String.valueOf(number));
                            Files.write(pathS, lines);
                            settingsNotDone = false;
                            break;
                        } else if (Objects.equals(commandAndInput[0], "e")) {
                            settingsNotDone = false;
                        } else {
                            System.out.println(translations.get("txtErrInput"));
                            scanner.nextLine();
                        }
                    }

                    clean();
                    title();
                    System.out.println(translations.get("txtSettingsSet"));
                    line();
                    System.out.println(translations.get("TxtEnterForContinue"));
                    scanner.nextLine();
                    break;

                // Генератор паролей
                case ("g"):
                    clean();
                    title();

                    // Тут начинается генерация пароля на основе параметра размера пароля, который лежит на второй строчке в Settings.txt
                    int lengthPassword = 0;
                    try (BufferedReader reader = new BufferedReader(new FileReader(settingsTxtPath))) {
                        reader.readLine();

                        // Читаем вторую строку, где находится параметр размера для пароля
                        lengthPassword = Integer.parseInt(reader.readLine());
                    } catch (IOException e) {
                        System.out.println(translations.get("txtErrRead"));
                    }
                    System.out.print(translations.get("txtGeneratedPassword"));
                    System.out.printf("%d):\n\n", lengthPassword);
                    System.out.println(Generator.generatePassword(lengthPassword));
                    System.out.println(translations.get("txtPasswordCopy"));
                    System.out.println(translations.get("TxtEnterForContinue"));
                    scanner.nextLine();
                    break;

            }


            // Очистка консоли после каждой итерации
            clean();
        }
    }
}