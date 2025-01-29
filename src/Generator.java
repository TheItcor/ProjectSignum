import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.*;

public class Generator {
    public static String generatePassword(int length) {
        List<String> symbols = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "_", "-", "*", "&");
        Random random = new Random();


        StringBuilder password = new StringBuilder();
        for (int i = 0; i != length; i++) {
            int randomIndex = random.nextInt(symbols.size());
            password.append(symbols.get(randomIndex));

        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(password.toString());
        clipboard.setContents(transferable, null);

        return password.toString();
    }
}
