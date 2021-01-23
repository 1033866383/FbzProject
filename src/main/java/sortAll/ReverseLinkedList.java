package sortAll;



/**
 * 反转链表
 */
public class ReverseLinkedList {
    class LinkedList1{
        LinkedList1 next;
        int val;

        @Override
        public String toString() {
            return "LinkedList1{" +
                    "next=" + next +
                    ", val=" + val +
                    '}';
        }
    }

    public static void main(String[] args) {
        LinkedList1 head = new ReverseLinkedList().new LinkedList1();
        LinkedList1 one = new ReverseLinkedList().new LinkedList1();
        LinkedList1 two = new ReverseLinkedList().new LinkedList1();
        LinkedList1 three = new ReverseLinkedList().new LinkedList1();
        head.val = 1;
        one.val = 2;
        two.val = 3;
        three.val = 4;
        head.next = one;
        one.next = two;
        two.next = three;

        LinkedList1 endn = null;
        LinkedList1 headercopy = head;
        LinkedList1 res = null;
        while (headercopy != null){
            LinkedList1 tmp = headercopy.next;
            headercopy.next = endn;
            endn = headercopy;
            headercopy = tmp;
            res = endn;
        }


        StringBuilder stringBuilder = new StringBuilder();
        while (res != null){
            stringBuilder.append(res.val+" ");
            res = res.next;
        }
        System.out.println(stringBuilder.toString());
    }

}
