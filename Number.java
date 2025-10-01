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
        String s = number;
        if (s.equals(null)){
            throw new NullPointerException("Number cannot be null");
        }
        for(int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                throw new IllegalArgumentException("Input must contain only integer characters");
            }
        }
        head = new Node(Character.getNumericValue(s.charAt(s.length()-1)), null);
        tail = head;
        for(int i = s.length()-2; i >=0; i--){
            Node temp = new Node(Character.getNumericValue(s.charAt(i)), tail.getNext());
            tail.setNext(temp);
            tail = temp;
        }
         
    }

    private Number removeTrailingZeroes(){
        String s = this.returnFullString();
        if(s.charAt(0) == '0' && s.length() > 1){
            int ind = 0;
            while(s.charAt(ind) == '0'){
                if(ind == s.length()-1){
                    break;
                }
                ind++;
            }
            s = s.substring(ind, s.length());
        }
        Number n = new Number(s);
        return n;
    }

    private Number(Node h, Node t){
        head = h;
        tail = t;
    }

    public int length(){
        int count = 0;
        Node start = this.head;
        while(start != null){
            count++;
            start = start.getNext();
        }
        return count;
    }

    public Number add(Number other){
        if(other == null){
            throw new NullPointerException("Cannot add null number");
        }
        Number num1 = this.removeTrailingZeroes();
        Number num2 = other.removeTrailingZeroes();
        if(num1.length() > num2.length()){
            Node temp1 = num1.head;
            Node temp2 = num2.head;
            Node nhead = new Node((temp1.getVal() + temp2.getVal())%10, null);
            Node ntail = nhead;
            int carry = (temp1.getVal() + temp2.getVal())/10;
            temp1 = temp1.getNext();
            temp2 = temp2.getNext();
            while(temp1 != null){
                if(temp2 != null){
                    int sum = (temp1.getVal() + temp2.getVal()) + carry;
                    Node temp3 = new Node(sum%10, ntail.getNext());
                    ntail.setNext(temp3);
                    ntail = temp3;
                    carry = sum/10;
                    temp1 = temp1.getNext();
                    temp2 = temp2.getNext();
                }
                else{
                    int sum = (temp1.getVal()) + carry;
                    Node temp3 = new Node(sum%10, ntail.getNext());
                    ntail.setNext(temp3);
                    ntail = temp3;
                    carry = sum/10;
                    temp1 = temp1.getNext();
                }
            }
            if(carry > 0){
                Node temp3 = new Node(carry, null);
                ntail.setNext(temp3);
                ntail = temp3;
            }
            return new Number(nhead, ntail);
        }

        Node temp1 = num1.head;
        Node temp2 = num2.head;
        Node nhead = new Node((temp1.getVal() + temp2.getVal())%10, null);
        Node ntail = nhead;
        int carry = (temp1.getVal() + temp2.getVal())/10;
        temp1 = temp1.getNext();
        temp2 = temp2.getNext();
        while(temp2 != null){
            if(temp1 != null){
                int sum = (temp1.getVal() + temp2.getVal()) + carry;
                Node temp3 = new Node(sum%10, ntail.getNext());
                ntail.setNext(temp3);
                ntail = temp3;
                carry = sum/10;
                temp1 = temp1.getNext();
                temp2 = temp2.getNext();
            }
            else{
                int sum = (temp2.getVal()) + carry;
                Node temp3 = new Node(sum%10, ntail.getNext());
                ntail.setNext(temp3);
                ntail = temp3;
                carry = sum/10;
                temp2 = temp2.getNext();
            }
        }
        if(carry > 0){
            Node temp3 = new Node(carry, null);
            ntail.setNext(temp3);
            ntail = temp3;
        }
        return new Number(nhead, ntail);
    }

    public Number multiplyByDigit(int digit){
        if(digit < 0 || digit >9){
            throw new IllegalArgumentException("Please enter an integer between 0 and 9 inclusive");
        }
        Number num1 = this.removeTrailingZeroes();
        Node temp = num1.head;
        Node nHead = new Node((temp.getVal() * digit)%10, null);
        Node nTail = nHead;
        int carry = (temp.getVal() * digit)/10;
        temp = temp.getNext();
        while(temp != null){
            Node nTemp = new Node((digit*temp.getVal() + carry)%10, nTail.getNext());
            nTail.setNext(nTemp);
            nTail = nTemp;
            carry = (digit*temp.getVal() + carry)/10;
            temp = temp.getNext();
        }
        if(carry > 0){
            Node fNode = new Node(carry, null);
            nTail.setNext(fNode);
            nTail = fNode;
        }
        return new Number(nHead, nTail);
    }

    public Number multiply(Number other){
        if(other == null){
            throw new NullPointerException("Cannot multiply null number");
        }
        Number num1 = this.removeTrailingZeroes();
        Number num2 = other.removeTrailingZeroes();
        Number product = new Number("0");
        Node temp1 = num1.head;
        Node temp2 = num2.head;
        int zeroes = 0;
        while(temp1 != null){
            Node indHead = new Node((temp2.getVal() * temp1.getVal())%10, null);
            Node indTail = indHead;
            int carry = (temp2.getVal() * temp1.getVal())/10;
            temp2 = temp2.getNext();
            while(temp2 != null){
                Node temp3 = new Node((temp1.getVal() * temp2.getVal() + carry)%10, indTail.getNext());
                indTail.setNext(temp3);
                indTail = temp3;
                carry = (temp1.getVal() * temp2.getVal() + carry)/10;
                temp2 = temp2.getNext();
            }
            if(carry > 0){
                Node temp3 = new Node(carry, null);
                indTail.setNext(temp3);
                indTail = temp3;
            }
            Number indProd = new Number(indHead, indTail);
            if(zeroes > 0){
                Node headzero = new Node(0,indProd.head);
                Node tailzero = headzero;
                for(int i = 0; i < zeroes-1; i++){
                    Node tempzero = new Node(0,tailzero.getNext());
                    tailzero.setNext(tempzero);
                    tailzero = tempzero;
                }
                indProd.head = headzero;
            }
            product = product.add(indProd);
            temp2 = num2.head;
            temp1 = temp1.getNext();
            zeroes++;
        }
        return product;
    }

    public int compareTo(Number other){
        if (other == null){
            throw new NullPointerException("compared number cannot be null");
        }
        Number num1 = this.removeTrailingZeroes();
        Number num2 = other.removeTrailingZeroes();
        if(num1.length() > num2.length()){
            return 1;
        }
        else if(num1.length() < num2.length()){
            return -1;
        }
        Number rev1 = new Number(new StringBuilder(num1.toString()).reverse().toString());
        Number rev2 = new Number(new StringBuilder(num2.toString()).reverse().toString());
        Node temp1 = rev1.head;
        Node temp2 = rev2.head;
        for(int i = 0; i < rev1.length(); i++){
            if(temp1.getVal() > temp2.getVal()){
                return 1;
            }
            if(temp1.getVal() < temp2.getVal()){
                return -1;
            }
            temp1 = temp1.getNext();
            temp2 = temp2.getNext();
        }
        return 0;
    }

    public boolean equals(Object obj){
        Number num1 = this;
        Number num2 = (Number) obj;
        if(num1.length() != num2.length()){
            return false;
        }
        else{
            Node head1 = num1.head;
            Node head2 = num2.head;
            for(int i = 0; i < num1.length(); i++){
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
        String s = "";
        Node n = num.head;
        for(int i = 0; i < num.length(); i++){
            s+=n.getVal();
            n = n.getNext();
        }
        s = new StringBuilder(s).reverse().toString();
        return s;
    }

    @Override
    public String toString(){
        Number n = this.removeTrailingZeroes();
        return n.returnFullString();
    }
}
