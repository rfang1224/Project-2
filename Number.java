public class Number implements Comparable<Number>{

    private Node head;
    private Node tail;

    /**
     * Private inner class node
     * Nodes representing the place values of each number value
     */

    private class Node{
        private int value;
        private Node next;

        public Node(int v, Node n){
            this.value = v;
            this.next = n;
        }

        public int getVal(){
            return value;
        }

        public Node getNext(){
            return next;
        }

        public void setNext(Node n){
            this.next = n;
        }

    }

    public Number(String number){

        if (number.equals(null)){
            throw new NullPointerException("Number cannot be null");
        }

        for(int i = 0; i < number.length(); i++){
            if(!Character.isDigit(number.charAt(i))){
                throw new IllegalArgumentException("Input must contain only integer characters");
            }
        }

        head = new Node(Character.getNumericValue(number.charAt(number.length()-1)), null);
        tail = head;

        for(int i = number.length()-2; i >=0; i--){
            Node temp = new Node(Character.getNumericValue(number.charAt(i)), tail.getNext());
            tail.setNext(temp);
            tail = temp;
        }
         
    }

    private Number removeTrailingZeroes(){

        String noTrailing = this.returnFullString();

        if(noTrailing.charAt(0) == '0' && noTrailing.length() > 1){
            int firstNonZero = 0;
            while(noTrailing.charAt(firstNonZero) == '0'){
                if(firstNonZero == noTrailing.length()-1){
                    break;
                }
                firstNonZero++;
            }
            noTrailing = noTrailing.substring(firstNonZero, noTrailing.length());
        }

        return new Number(noTrailing);
    }

    private Number(Node h, Node t){
        head = h;
        tail = t;
    }

    public int length(){
        int countNodes = 0;
        Node trackNodes = this.head;

        while(trackNodes != null){
            countNodes++;
            trackNodes = trackNodes.getNext();
        }
        return countNodes;
    }

    public Number add(Number other){

        if(other == null){
            throw new NullPointerException("Cannot add null number");
        }

        Number longer = this.removeTrailingZeroes();
        Number shorter = other.removeTrailingZeroes();

        if(this.removeTrailingZeroes().length() < other.removeTrailingZeroes().length()){
            longer = other.removeTrailingZeroes();
            shorter = this.removeTrailingZeroes();
        }

        Node trackerOfLonger = longer.head;
        Node trackerOfShorter = shorter.head;
        Node headOfSum = new Node((trackerOfLonger.getVal() + trackerOfShorter.getVal())%10, null);
        Node tailOfSum = headOfSum;
        int carry = (trackerOfLonger.getVal() + trackerOfShorter.getVal())/10;
        trackerOfLonger = trackerOfLonger.getNext();
        trackerOfShorter = trackerOfShorter.getNext();

        while(trackerOfLonger != null){
            
            if(trackerOfShorter != null){
                int sum = (trackerOfLonger.getVal() + trackerOfShorter.getVal()) + carry;
                Node tempOfSum = new Node(sum%10, tailOfSum.getNext());
                tailOfSum.setNext(tempOfSum);
                tailOfSum = tempOfSum;
                carry = sum/10;
                trackerOfLonger = trackerOfLonger.getNext();
                trackerOfShorter = trackerOfShorter.getNext();
            }

            else{
                int sum = (trackerOfLonger.getVal()) + carry;
                Node temp3 = new Node(sum%10, tailOfSum.getNext());
                tailOfSum.setNext(temp3);
                tailOfSum = temp3;
                carry = sum/10;
                trackerOfLonger = trackerOfLonger.getNext();
            }

        }
        
        if(carry > 0){
            Node tempOfSum = new Node(carry, null);
            tailOfSum.setNext(tempOfSum);
            tailOfSum = tempOfSum;
        }

        return new Number(headOfSum, tailOfSum);
    }

    public Number multiplyByDigit(int digit){

        if(digit < 0 || digit >9){
            throw new IllegalArgumentException("Please enter an integer between 0 and 9 inclusive");
        }

        Number withoutZero = this.removeTrailingZeroes();
        Node trackerOfOriginal = withoutZero.head;
        Node headOfProduct = new Node((trackerOfOriginal.getVal() * digit)%10, null);
        Node tailOfProduct = headOfProduct;
        int carry = (trackerOfOriginal.getVal() * digit)/10;
        trackerOfOriginal = trackerOfOriginal.getNext();

        while(trackerOfOriginal != null){
            Node tempOfProduct = new Node((digit*trackerOfOriginal.getVal() + carry)%10, tailOfProduct.getNext());
            tailOfProduct.setNext(tempOfProduct);
            tailOfProduct = tempOfProduct;
            carry = (digit*trackerOfOriginal.getVal() + carry)/10;
            trackerOfOriginal = trackerOfOriginal.getNext();
        }

        if(carry > 0){
            Node fNode = new Node(carry, null);
            tailOfProduct.setNext(fNode);
            tailOfProduct = fNode;
        }

        return new Number(headOfProduct, tailOfProduct);
    }

    public Number multiply(Number other){

        if(other == null){
            throw new NullPointerException("Cannot multiply null number");
        }

        Number factor1 = this.removeTrailingZeroes();
        Number factor2 = other.removeTrailingZeroes();
        Number product = new Number("0");
        Node trackerOfFactor1 = factor1.head;
        int zeroes = 0;

        while(trackerOfFactor1 != null){
            
            Number independentProduct = factor2.multiplyByDigit(trackerOfFactor1.getVal());

            if(zeroes > 0){
                Node headzero = new Node(0,independentProduct.head);
                Node tailzero = headzero;
                for(int i = 0; i < zeroes-1; i++){
                    Node tempzero = new Node(0,tailzero.getNext());
                    tailzero.setNext(tempzero);
                    tailzero = tempzero;
                }
                independentProduct.head = headzero;
            }

            product = product.add(independentProduct);
            trackerOfFactor1 = trackerOfFactor1.getNext();
            zeroes++;

        }

        return product;

    }

    public int compareTo(Number other){
        if (other == null){
            throw new NullPointerException("compared number cannot be null");
        }
        Number withoutZero1 = this.removeTrailingZeroes();
        Number withoutZero2 = other.removeTrailingZeroes();
        if(withoutZero1.length() > withoutZero2.length()){
            return 1;
        }
        else if(withoutZero1.length() < withoutZero2.length()){
            return -1;
        }
        Number reverse1 = new Number(new StringBuilder(withoutZero1.toString()).reverse().toString());
        Number reverse2 = new Number(new StringBuilder(withoutZero2.toString()).reverse().toString());

        Node tracker1 = reverse1.head;
        Node tracker2 = reverse2.head;

        for(int i = 0; i < reverse1.length(); i++){
            if(tracker1.getVal() > tracker2.getVal()){
                return 1;
            }
            if(tracker1.getVal() < tracker2.getVal()){
                return -1;
            }
            tracker1 = tracker1.getNext();
            tracker2 = tracker2.getNext();
        }
        return 0;
    }

    public boolean equals(Object obj){

        Number compared1 = this.removeTrailingZeroes();
        Number compared2 = ((Number) obj).removeTrailingZeroes();

        if(compared1.length() != compared2.length()){
            return false;
        }

        else{

            Node head1 = compared1.head;
            Node head2 = compared2.head;

            for(int i = 0; i < compared1.length(); i++){
                if(head1.getVal() != head2.getVal()){
                    return false;
                }
                head1 = head1.getNext();
                head2 = head2.getNext();
            }
        }

        return true;

    }

    private String returnFullString(){

        Number num = this;
        String stringRepresentation = "";
        Node tracker = num.head;

        for(int i = 0; i < num.length(); i++){
            stringRepresentation+=tracker.getVal();
            tracker = tracker.getNext();
        }

        stringRepresentation = new StringBuilder(stringRepresentation).reverse().toString();
        return stringRepresentation;
    }

    @Override
    public String toString(){
        Number representationWithoutZero = this.removeTrailingZeroes();
        return representationWithoutZero.returnFullString();
    }
}
