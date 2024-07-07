package fun.ajing.entity;

public class MPair<L, R> {

    private L left;
    private R right;

    public MPair(L left, R right){
        this.left = left;
        this.right = right;
    }

    public static <L, R> MPair<L, R> of(L left, R right){
        return new MPair<>(left, right);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }
}
