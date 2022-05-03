//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int exclaim = markdown.indexOf("!", currentIndex);
            int openBracket = markdown.indexOf("[", currentIndex);
            if (!(exclaim == -1) && ((exclaim + 1) == openBracket)) {
                currentIndex = openBracket + 1;
                continue;
            }
            if (openBracket == -1) break;
            int closeBracket = markdown.indexOf("]", openBracket);
            if (closeBracket == -1) break;
            int openParen = markdown.indexOf("(", closeBracket);
            if (openParen == -1) break;
            int checkOpen = markdown.indexOf("(", openParen + 1);
            int closeParen = markdown.indexOf(")", openParen);
            int checkClose = markdown.indexOf(")", closeParen + 1);
            if (closeParen == -1) break;
            if (checkOpen < checkClose) {
                closeParen = markdown.indexOf(")", closeParen + 1);
            }
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
