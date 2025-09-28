public class Main{
    public static void main(String[] args) {
        Number n = new Number("12");
        Number n2 = new Number("123");
        int n3 = n.compareTo(n2);
        System.out.println(n3);
    }
}
