import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XORList {

    private Node[] linkedList;
    private Integer tail;

    public XORList() {
        this.tail = 1;
    }

    private void readFile(String fileName) {
        String[] names = null;
        try {
            String line;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader readFile = new BufferedReader(fileReader);
            while ((line = readFile.readLine()) != null) {
                names = line.split(",");
            }
            fileReader.close();

            this.linkedList = new Node[names.length*2];

            for (String name : names) {
                addToList(name);
            }
        }
        catch (IOException e) {
            //Exception thrown if file not found.
            System.out.println("File not found.");
            System.exit(0);
        }

        printList();
    }

    private void printList() {
        int temp;
        int end = tail;
        int current = linkedList[end].getNpx();
        System.out.println(current);

        int i = 1;
        System.out.println(linkedList[end].getData());

        while (current != 0) {
            i++;
            System.out.println(linkedList[current].getData());
            temp = current;
            current = end ^ linkedList[current].getNpx();
            end = temp;
        }
        System.out.println(i);
    }

    private void insertAfter(String name, String insert) {
        int end = tail;
        int current = linkedList[end].getNpx();
        int temp;
        if (linkedList[tail].getData().equals(name)) {
            addToList(insert);
            current = 0;
        }
        while (current != 0) {
            //change current to end for insert before
            if (linkedList[current].getData().equals(name)) {
                int newAdd = findSpace();
                linkedList[newAdd] = new Node(end ^ current, insert);
                //XOR with prev to cancel and xor with new to establish GAYLORD
                linkedList[current].setNpx(linkedList[current].getNpx() ^ end ^ newAdd);
                //see above
                linkedList[end].setNpx(linkedList[end].getNpx() ^ current ^ newAdd);
                break;
            }
            else {
                temp = current;
                current = end ^ linkedList[current].getNpx();
                end = temp;
            }
        }
    }

    private void insertBefore(String name, String insert) {
        int end = tail;
        int current = linkedList[end].getNpx();
        int temp;
        if (linkedList[tail].getData().equals(name)) {
            addToList(insert);
            current = 0;
        }
        while (current != 0) {
            //change current to end for insert before
            if (linkedList[end].getData().equals(name)) {
                int newAdd = findSpace();
                linkedList[newAdd] = new Node(end ^ current, insert);
                //XOR with prev to cancel and xor with new to establish GAYLORD
                linkedList[current].setNpx(linkedList[current].getNpx() ^ end ^ newAdd);
                //see above
                linkedList[end].setNpx(linkedList[end].getNpx() ^ current ^ newAdd);
                break;
            }
            else {
                temp = current;
                current = end ^ linkedList[current].getNpx();
                end = temp;
            }
        }
    }

    private String removeAfter(String name) {
        String output = null;
        int end = tail;
        int current = linkedList[end].getNpx();
        int temp;
        while (current != 0) {
            if (linkedList[current].getData().equals(name)) {

                output = linkedList[end].getData();

                temp = current ^ linkedList[end].getNpx();
                linkedList[current].setNpx(linkedList[current].getNpx() ^ end ^ temp);
                linkedList[temp].setNpx(linkedList[temp].getNpx() ^ end ^ current);
                linkedList[end] = null;
                break;
            }
            else {
                temp = current;
                current = end ^ linkedList[current].getNpx();
                end = temp;
            }
        }
        return output;
    }

    private String removeBefore(String name) {
        String output = null;
        int end = tail;
        int current = linkedList[end].getNpx();
        int temp;
        while (current != 0) {

            if (linkedList[end].getData().equals(name)) {

                output = linkedList[current].getData();

                temp = end ^ linkedList[current].getNpx();

                linkedList[end].setNpx(linkedList[end].getNpx() ^ current ^ temp);
                linkedList[temp].setNpx(linkedList[temp].getNpx() ^ end ^ current);

                linkedList[current] = null;
                break;
            }
            else {
                temp = current;
                current = end ^ linkedList[current].getNpx();
                end = temp;
            }
        }
        return output;
    }

    private int findSpace() {
        for (int i = 1; i < linkedList.length; i++) {
            if (linkedList[i] == null) {
                return i;
            }
        }
        return 0;
    }

    private void addToList(String name) {
        int address = findSpace();

        if (address != 0) {
            linkedList[address] = new Node(tail, name);
            linkedList[tail].setNpx(linkedList[tail].getNpx()^address);
            this.tail = address;
        }

    }

    public static void main(String[] args) {
        XORList list = new XORList();
        String fileName = "names.txt";
        list.readFile(fileName);
    }
}
