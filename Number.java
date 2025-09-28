public class Number{

    private Node head;
    private Node tail;

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

    public Number(String s){
        head = new Node(Character.getNumericValue(s.charAt(s.length()-1)), null);
        tail = head;
        for(int i = s.length()-2; i >=0; i--){
            Node temp = new Node(Character.getNumericValue(s.charAt(i)), tail.getNext());
            tail.setNext(temp);
            tail = temp;
        }
    }

    public Number(Node h, Node t){
        head = h;
        tail = t;
    }

    public int len(){
        int count = 0;
        Node start = this.head;
        while(start != null){
            count++;
            start = start.getNext();
        }
        return count;
    }

    public Number add(Number num2){
        Number num1 = this;
        if(num1.len() > num2.len()){
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

    public Number multiply(Number num2){
        Number num1 = this;
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
        Number num1 = this;
        Number num2 = other;
        if(num1.len() > num2.len()){
            return 1;
        }
        else if(num1.len() < num2.len()){
            return -1;
        }
        Number rev1 = new Number(new StringBuilder(num1.toString()).reverse().toString());
        Number rev2 = new Number(new StringBuilder(num2.toString()).reverse().toString());
        Node temp1 = rev1.head;
        Node temp2 = rev2.head;
        for(int i = 0; i < rev1.len(); i++){
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
        if(num1.len() != num2.len()){
            return false;
        }
        else{
            Node head1 = num1.head;
            Node head2 = num2.head;
            for(int i = 0; i < num1.len(); i++){
                if(head1.getVal() != head2.getVal()){
                    return false;
                }
                head1 = head1.getNext();
                head2 = head2.getNext();
            }
        }
        return true;
    }

    @Override
    public String toString(){
        Number num = this;
        String s = "";
        Node n = num.head;
        for(int i = 0; i < num.len(); i++){
            s+=n.getVal();
            n = n.getNext();
        }
        s = new StringBuilder(s).reverse().toString();
        return s;
    }
}
