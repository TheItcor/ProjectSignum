import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class SignumManager {

    /// Создание SLP-файла и добавление его в список
    public static void newLoginAndPassword() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название сервиса для которого сохраняем логин и пароль");
        String service = scanner.nextLine();
        System.out.println("Введите ваш логин:");
        String login = scanner.nextLine();
        System.out.println("Введите ваш пароль или нажмите Enter для его генерации");
        String userChoice = scanner.nextLine();
        String password = "";

        if (Objects.equals(userChoice, "")) {
            // Сгенерированный пароль
            int lengthPassword = 16;
            try (BufferedReader reader = new BufferedReader(new FileReader(Main.settingsTxtPath))) {
                reader.readLine();

                // Читаем вторую строку, где находится параметр размера для пароля
                lengthPassword = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("ERROR: ошибка чтения файла: проверьте data/settings.txt");
            }
            password = Generator.generatePassword(lengthPassword);

        } else {
            // Свой пароль
            password = userChoice;
        }

        Main.clean();
        Main.title();
        System.out.printf("Сервис: %s\n", service);
        System.out.printf("Ваш логин: %s\n", login);
        System.out.printf("Ваш пароль: %s\n", password);

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


    static final String SlpPath = "data" + File.separator + "SLP";


    // Функция создания файла с сервисом, логином (Service Login Password SLP)
    public static void createSlpFile(String serviceName, String login, String password) {
        // Создаем директорию, если она не существует
        File directory = new File(SlpPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Директория создана: " + SlpPath);
            } else {
                System.err.println("Не удалось создать директорию: " + SlpPath);
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
            System.err.println("ERROR: создании файла: " + e.getMessage());
        }
    }

    // Функция чтения всех файлов .txt и вывода их содержимого (SLP)
    public static void readAllSlpFiles() {
        File directory = new File(SlpPath); // Указываем директорию

        // Проверяем, существует ли директория
        if (!directory.exists()) {
            System.out.println("Директория " + SlpPath + " не существует.");
            return;
        }

        // Ищем все .txt файлы в директории
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("Файлы сервисов не найдены в директории " + SlpPath + ".");
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
                System.err.println("Ошибка при чтении файла " + file.getName() + ": " + e.getMessage());
            }
        }
    }

}
