package layout;

/**
 * Created with IntelliJ IDEA.
 * User: Blystone
 * Date: 12/9/12
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Attachment {

    AA(0,0),
    AB(0,1),
    BA(1,0),
    BB(1,1);

    private int from;
    private int to;

    Attachment(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
