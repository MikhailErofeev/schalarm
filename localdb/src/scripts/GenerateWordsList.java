import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author m-erofeev
 * @since 24.08.14
 */
public class GenerateWordsList {

    public static void main(String[] args) throws IOException {
        File srcPath = new File("/Users/m-erofeev/projects/schalarm/localdb/src/main/resources/source-russian-text.txt");
        BufferedReader reader = new BufferedReader(new FileReader(srcPath));
        String s;
        Set<String> strings = new HashSet<>();
        Pattern pattern = Pattern.compile("[а-я]*");
        while ((s = reader.readLine()) != null) {
            String[] words = s.split(" ");
            for (String word : words) {
                word = word.toLowerCase();
                final Matcher matcher = pattern.matcher(word);
                if (matcher.find()) {
                    word = matcher.group();
                } else {
                    continue;
                }
                if (word.length() > 3) {
                    strings.add(word);
                }
            }
        }
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
