import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HtmlAnalyzer {




    public static BufferedReader fetchHtmlContentFromUrl(String urlText) throws Exception {

        try {

            URL url = new URL(urlText);
            return new BufferedReader(new InputStreamReader(url.openStream()));

        }catch (Exception e) {

            throw new Exception("URL connection error");

        }
    }


    public static String extractTagName(String line) {

        return line.replace("<", "")
                   .replace("/", "")
                   .replace("\n", "")
                   .replaceAll("[ \\t]+", "")
                   .replace(">", "");
    }


    public static String formatTextContent(String line) {

        return line.replaceAll("\n", "")
                   .replaceFirst("^[ \\t]+", "");

    }
    public static LinkedList<InnerText> extractAllTextFromHtml(BufferedReader input) throws Exception {
        Stack<String> tagStack = new Stack<>(); // this stack is used for the syntactic analysis used to validate the HTML formation.
        int id = 0;
        String inputLine;
        LinkedList<InnerText> innerTexts = new LinkedList<>();

        while ((inputLine = input.readLine()) != null) { // this while makes a syntactic analysis of the HTML received, and validates its formation.
            if (inputLine.contains("</")) {

                if (!extractTagName(inputLine).equals(tagStack.pop())) { // in case the closing tag doesn't match with the stack's top, it throws an error.
                    throw new Exception("malfunction HTML");
                }

            } else if (inputLine.contains("<")) {
                tagStack.push(extractTagName(inputLine));
            } else {

                id++;
                InnerText text = new InnerText(tagStack.size(),formatTextContent(inputLine), id);

                innerTexts.add(text);
            }
        }

        if(!tagStack.isEmpty()){ // if it is not empty, it means there are tags that were not properly closed.
            throw new Exception("malfunction HTML");
        }

        return innerTexts;

    }


    public static String getDeepestText(LinkedList<InnerText> innerTexts){
        innerTexts.sort(Comparator.comparingInt(InnerText::getDepth));


        InnerText deepestText = innerTexts.removeLast();

        List<InnerText> similarDepthTexts = innerTexts.stream()  // get all texts that has the same depth.
                                            .filter(text-> text.getDepth() == deepestText.getDepth())
                                            .collect(Collectors.toList());


        if(!similarDepthTexts.isEmpty()){ // in case there are 2 or more texts on the same depth, returns the text with the smallest id (first to appear).
            similarDepthTexts.add(deepestText);
            similarDepthTexts.sort(Comparator.comparingInt(InnerText::getId));
            return similarDepthTexts.get(0).getText();
        }

        return deepestText.getText();

    }




    public static void main(String[] args){
        try {

            BufferedReader input = fetchHtmlContentFromUrl(args[0]); // receives a URL as an argument and returns the HTML content from it.

            LinkedList<InnerText> innerTexts = extractAllTextFromHtml(input); // receives a DOM as an argument and returns all the text content from it.

            System.out.println(getDeepestText(innerTexts)); // gets a text content based on the depth it was found inside the DOM.


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}


