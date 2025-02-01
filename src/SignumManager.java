import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SignumManager {
    /* SLP-файл - это файл, который в зашифрованном виде хранит информацию о сервисе, логине и пароле (Service Login Password)
     *
     * Данный класс с функциями:
     * 1) newLoginAndPassword() сборка SLP-файла (Раньше задумывалось о добавлении его в список/словарь, но в итоге отказался
     * от этой идеи в пользу сохранения каждого Сервиса-Логина-Пароля по отдельным папкам). На этой стадии так-же происходит
     * шифрование информации.
     * 2) createSlpFile(Сервис, Логин, Пароль) - создание SLP-файла как .txt файл.
     * 3) readAllSlpFiles() - Чтение, расшифровка и вывод всех SLP-файлов в терминал
     */


    /// Создание SLP-файла
    public static void newLoginAndPassword() {
        Map<String, Object> translations = TranslationManager.loadTranslations(Main.language);

        /* Сначала вводится сервис, логин и пароль, затем он шифруется и создаётся SLP-файл
         */
        Scanner scanner = new Scanner(System.in);

        // Ввод
        System.out.println(translations.get("txtCreatingService"));
        String service = scanner.nextLine();
        System.out.println(translations.get("txtCreatingLogin"));
        String login = scanner.nextLine();
        System.out.println(translations.get("txtCreatingPassword"));
        String userChoice = scanner.nextLine();
        String password = "";


        // Если пользователь оставил пустым поле с паролем, то производится генерация пароля с размером, который указан в Settings.txt
        if (Objects.equals(userChoice, "")) {
            // Сгенерированный пароль
            int lengthPassword = 16;
            try (BufferedReader reader = new BufferedReader(new FileReader(Main.settingsTxtPath))) {
                reader.readLine();

                // Читаем вторую строку, где находится параметр размера для пароля
                lengthPassword = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println(translations.get("txtErrRead"));
            }
            password = Generator.generatePassword(lengthPassword);

        } else {
            // Свой пароль
            password = userChoice;
        }

        Main.clean();
        Main.title();
        System.out.printf(translations.get("txtYourService") + "%s\n", service);
        System.out.printf(translations.get("txtYourLogin") + "%s\n", login);
        System.out.printf(translations.get("txtYourPassword") + "%s\n", password);

        String cryptedService = Cryption.encrypt(service);
        String cryptedLogin = Cryption.encrypt(login);
        String cryptedPassword = Cryption.encrypt(password);

        // Тест Шифрования и расшифрования
        //System.out.println(cryptedLogin);
        //System.out.println(cryptedPassword);
        //System.out.println(Cryption.decrypt(cryptedLogin));
        //System.out.println(Cryption.decrypt(cryptedPassword));

        createSlpFile(cryptedService, cryptedLogin, cryptedPassword);
    }

    // Путь к SLP-файлам
    static final String SlpPath = "data" + File.separator + "SLP";


    // Функция создания файла с сервисом, логином (Service Login Password SLP)
    public static void createSlpFile(String serviceName, String login, String password) {
        Map<String, Object> translations = TranslationManager.loadTranslations(Main.language);

        // Создаем директорию, если она не существует
        File directory = new File(SlpPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println(SlpPath);
            } else {
                System.err.println("ERROR: " + SlpPath);
                return;
            }
        }

        // Полный путь к файлу
        String filePath = SlpPath + "/" + serviceName + ".txt";

        // Записываем данные в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(serviceName + "\n"); // Записываем название сервиса
            writer.write(login + "\n");       // Записываем логин
            writer.write(password + "\n");    // Записываем пароль
        } catch (IOException e) {
            System.err.println("ERROR:" + e.getMessage());
        }
    }

    // Функция чтения всех файлов .txt и вывода их содержимого (SLP)
    public static void readAllSlpFiles() {
        Map<String, Object> translations = TranslationManager.loadTranslations(Main.language);

        File directory = new File(SlpPath); // Указываем директорию

        // Проверяем, существует ли директория
        if (!directory.exists()) {
            System.out.println("ERROR: " + SlpPath);
            return;
        }

        // Ищем все .txt файлы в директории
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("ERROR: " + SlpPath);
            return;
        }

        // Читаем и выводим содержимое каждого файла
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String serviceName = reader.readLine(); // Читаем название сервиса
                String login = reader.readLine();       // Читаем логин
                String password = reader.readLine();    // Читаем пароль

                System.out.println(Cryption.decrypt(serviceName) + ": " + Cryption.decrypt(login) + " " + Cryption.decrypt(password)); // Выводим в консоль
            } catch (IOException e) {
                System.err.println(translations.get("txtErrRead") + file.getName() + ": " + e.getMessage());
            }
        }
    }

}
