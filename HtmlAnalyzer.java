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

//    public static void validateTag(Stack<String> stack, String line) {
//        if(line.contains("</")) {
//
//        }
//    }


    public static LinkedList<InnerText> extractAllTextFromHtml(BufferedReader input) throws Exception {
        Stack<String> tagStack = new Stack<>(); // this stack is used for the syntactic analysis used to validate the HTML formation.
        int id = 0;
        String inputLine;
        LinkedList<InnerText> innerTexts = new LinkedList<>();

        while ((inputLine = input.readLine()) != null) { // this while makes a syntactic analysis of the HTML received, and validates its formation.
            if(inputLine.trim().isEmpty()){
                continue;
            }

            if (inputLine.contains("</")) {

                if (!extractTagName(inputLine).equals(tagStack.pop())) { // in case the closing tag doesn't match with the stack's top, it throws an error.
                    throw new Exception("malfunction HTML");
                }

            } else if (inputLine.contains("<")) {
                tagStack.push(extractTagName(inputLine));
            } else {

                id++;
                InnerText text = new InnerText(tagStack.size(),inputLine.trim(), id);

                innerTexts.add(text);
            }
        }

        if(!tagStack.isEmpty()){ // if it is not empty, it means there are tags that were not properly closed.
            throw new Exception("malfunction HTML");
        }

        return innerTexts;

    }


    public static String getDeepestText(LinkedList<InnerText> innerTexts){
        InnerText deepestText = innerTexts.removeFirst();


        for(InnerText innerText : innerTexts){
            //searches for the deepest text with the smallest id (in case 2 or more text contents have the same depth).
            if(innerText.getDepth() > deepestText.getDepth() || (innerText.getDepth() == deepestText.getDepth() && innerText.getId() < deepestText.getId())){
                deepestText = innerText;
            }
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

