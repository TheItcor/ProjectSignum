import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/// Остаётся неиспользованным.


public class Language {
    public static String[] checkLanguage() {
        String language = "";
        String helpText = null;
        String startText = null;
        String mainMenuText = null;


        try (BufferedReader reader = new BufferedReader(new FileReader("src/Settings.txt"))) {
        // Читаем первую строку
            language = reader.readLine();
        } catch (IOException e) {
            System.out.println("ERROR!");
        }

        switch (language) {
            case "ru": {
                helpText =
                        """
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
                                """;

                startText = "Нажмите любую кнопку чтобы продолжить";

                mainMenuText = """
                        Команды:\s
                        [h] - Помощь / О программе\s
                        [e] - Выход из приложения\s
                        [l] - Список логинов и паролей, редактирование\s
                        [n] - Добавить новый логин и пароль\s
                        [s] - Настройки программы\s
                        [g] - Сгенерировать пароль\s
                        """;
                break;
            }

            case "en": {
                helpText =
                        """
                                Project Signum - it's a program for saving
                                your logins and passwords.\s
                                """;

                startText = "Press any key";

                mainMenuText = """
                        commands:\s
                        [h] - help / about\s
                        [e] - exit\s
                        [l] - list of logins & passwords\s
                        [n] - add new login & password\s
                        [s] - settings\s
                        [g] - Generate new password\s
                        """;
                break;
            }




            case "es": {

                break;
            }
        }

        return new String[]{helpText, startText, mainMenuText};
    }
}
