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
        while (currentIndex < markdown.length()) {
            int exclamationMark = markdown.indexOf("!", currentIndex);
            int openBracket = markdown.indexOf("[", currentIndex);
            // Checking if there is an exclamation mark, which are for images.
            if (!(exclamationMark == -1) && ((exclamationMark + 1) == openBracket)) {
                currentIndex = openBracket + 1;
                continue;
            }
            // The following will break the loop if we cannot find any of the
            // syntax for the links.
            if (openBracket == -1) break;
            int closeBracket = markdown.indexOf("]", openBracket);
            if (closeBracket == -1) break;
            int openParen = markdown.indexOf("(", closeBracket);
            if (openParen == -1) break;
            int closeParen = markdown.indexOf(")", openParen);
            if (closeParen == -1) break;
            // Get the link and add it to the list.
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
