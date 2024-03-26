class Node {
    int key;
    int value;
    Node next;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class HashTable {
    private static final int INITIAL_CAPACITY = 10;
    private static final double LOAD_FACTOR_THRESHOLD = 0.80;

    private int size;
    private int capacity;
    private Node[] table;

    public HashTable() {
        size = 0;
        capacity = INITIAL_CAPACITY;
        table = new Node[capacity];
    }

    private int hashFunc(int key, int capacity) {
        final double A = 0.6180339887;
        double temp = key * A;
        temp -= (int) temp;
        int Mul = (int) (temp * capacity);

        int Div = key % capacity;

        return (Mul + Div) % capacity;
    }

    public void insert(int key, int value) {
        if ((double) size / capacity >= LOAD_FACTOR_THRESHOLD) {
            resize(true);
        }
        int index = hashFunc(key, capacity);
        Node newNode = new Node(key, value);
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    public void remove(int key) {
        int index = hashFunc(key, capacity);
        Node current = table[index];
        Node prev = null;
        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                if ((double) size / capacity <= 0.25) {
                    resize(false);
                }
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public int search(int key) {
        int index = hashFunc(key, capacity);
        Node current = table[index];
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return -1;
    }
    public void Table() {
        for (int i = 0; i < capacity; i++) {
            Node current = table[i];
            System.out.print("Index " + i + ": ");
            while (current != null) {
                System.out.print("(" + current.key + ", " + current.value + ") ");
                current = current.next;
            }
            System.out.println();
        }
    }
    

    private void resize(boolean increase) {
        int new_Cap = increase ? capacity * 2 : capacity / 2;
        Node[] newTable = new Node[new_Cap];
        for (int i = 0; i < capacity; i++) {
            Node current = table[i];
            while (current != null) {
                Node next = current.next;
                int newIndex = hashFunc(current.key, new_Cap);
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        capacity = new_Cap;
        table = newTable;
    }
    

    public static void main(String[] args) {
        HashTable H = new HashTable();
        H.insert(10, 80);
        H.insert(15, 20);
        H.insert(20, 100);
        H.insert(40, 90);
        System.out.println(H.search(15));
        H.remove(40);
        System.out.println(H.search(40)); 
        System.out.println(H.search(10));
        H.remove(20);
    }
}
