package lab2.service.chain;

public abstract class Middleware {
    private Middleware next;

    public Middleware linkWith(Middleware next){
        this.next=next;
        return next;
    }

    public abstract boolean check(String employeeName);

    protected boolean checkNext(String employeeName){
        if (next==null) return true;

        return next.check(employeeName);
    }
}
