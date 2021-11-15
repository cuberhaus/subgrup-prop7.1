package domini.classes;
// TODO: javadoc

public class Pair<X extends Comparable<X>,Y /*extends Comparable<Y>*/> implements Comparable<Pair<X,Y>>{
    public final X x;
    public final Y y;
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Pair<X,Y> object) {
        int primer = x.compareTo(object.x);
        //if (primer == 0) {
        //    return y.compareTo(object.y);
        //}
        return primer;
    }
}
