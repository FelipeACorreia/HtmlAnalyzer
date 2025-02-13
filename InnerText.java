public class InnerText {

    private final int depth;
    private final String text;
    private final int id;

    public InnerText(int depth, String text, int id) {
        this.depth = depth;
        this.text = text;
        this.id = id;
    }
    public int getId() {
        return id;
    }


    public int getDepth() {
        return depth;
    }


    public String getText() {
        return text;
    }


}
