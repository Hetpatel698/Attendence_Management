public class linkList {
    class node {
        Student data;
        node next;

        public node(Student data) {
            this.data = data;
            this.next = null;
        }
    }

    node first = null;

    void Add(Student data) {
        node n = new node(data);
        if (first == null) {
            first = n;
        } else {
            node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
            n.next = null;
        }
    }

    void DELETE_STUDENT(int data){
        if (first == null) {
            return ;
        } else {
            node temp = first ;
            while (temp != null) {
                if (temp.next.data.getId() == data) {
                    temp.next = temp.next.next ;
                } else {
                   temp = temp.next; 
                }
            }
        }
    }

    void display(){
        if (first == null) {
            return ;
        } else {
            node temp = first ;
            while (temp != null) {
                System.out.println("*****************************************");
                System.out.println("ID : "+ temp.data.getId());
                System.out.println("NAME : "+temp.data.getName());
                System.out.println("BIRTH DATE : "+temp.data.getBirth_date());
                System.out.println("PHONE NUMBER : "+temp.data.getPhone_number());
                System.out.println("MAIL ID : "+temp.data.getMail_id());
            }
            System.out.println("*****************************************");
        }
    }
}