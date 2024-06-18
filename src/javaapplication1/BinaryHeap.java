package javaapplication1;

public class BinaryHeap {  //min heap is used to maintain a list a of frequencies in ascending order
    CharNode[] heap = new CharNode[HashTable.noOfUnicodeChars];
    int empty = 0; // index of first empty slot in array

    public boolean isFull() {
        return empty >= heap.length;
    }

    public boolean isEmpty() {
        return empty == 0;  //first index is zero
    }

    public int heapSize() {
        return empty;
    }

    public boolean isLeaf(int index) {
        return 2*index + 1 >= heapSize();   //no left child so no children as in heap left child is filled first
    }

    public int leftChild(int index) {
        return index * 2 + 1;
    }

    public int rightChild(int index) {
        return index * 2 + 2;
    }

    public int parent(int index) {
        return (index-1) / 2;
    }

    public void swap(int index1, int index2) {
        CharNode temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public int min(int n1, int n2) {   //used in deletion
        if(heap[n1].compareTo(heap[n2]) < 0)
            return n1;
        else
            return n2;
    }

    public void insert(CharNode node) {
        if (!isFull()) {
            heap[empty] = node; //inserting new node at first empty index
            int curr = empty;
            empty++;
            int parent = parent(curr);  //get parent to compare with
            while(heap[curr].compareTo(heap[parent]) < 0) {
                if (heap[curr].compareTo(heap[parent]) < 0) {
                    swap(curr, parent);
                    curr = parent;
                    parent = parent(curr);
                }
            }
        } else {
            System.out.println("Heap is full.");
        }
    }

    public void reHeap(int index) {  //used in deletion -> to maintain min heap
        //starting index is 0
        while (!isLeaf(index)) {
            int minChild = min(leftChild(index), rightChild(index));
            if (heap[minChild].compareTo(heap[index]) < 0) {
                swap(index, minChild);
                index = minChild;
            } else {
                break;
            }
        }
    }

    public CharNode delete() {
        CharNode deleted = heap[0];
        empty--;
        heap[0] = heap[empty];
        reHeap(0);
        return deleted;
    }

    public void printHeap() {
        for (int i = 0; i < empty; i++) {
            System.out.println(heap[i]);
        }
    }
}