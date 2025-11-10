import java.io.*;
import java.util.Scanner;

class Node {
    int data;
    Node left, right;

    Node(int data) {
        this.data = data;
        left = right = null;
    }
}

public class BinarySearchTree {
    Node root;

    // Insert node
    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);
        return root;
    }

    // 1️⃣ Print leaf nodes
    void printLeafNodes(Node node) {
        if (node == null)
            return;
        if (node.left == null && node.right == null)
            System.out.print(node.data + " ");
        printLeafNodes(node.left);
        printLeafNodes(node.right);
    }

    // 2️⃣ Search a node and print position
    Node search(Node root, int key) {
        if (root == null || root.data == key)
            return root;
        if (key < root.data)
            return search(root.left, key);
        return search(root.right, key);
    }

    // 3️⃣ Print parent and child nodes of a searched node
    void printParentChild(Node root, int key, Node parent) {
        if (root == null)
            return;
        if (root.data == key) {
            if (parent != null)
                System.out.println("Parent: " + parent.data);
            else
                System.out.println("Parent: None (Root Node)");
            System.out.println("Left Child: " + (root.left != null ? root.left.data : "None"));
            System.out.println("Right Child: " + (root.right != null ? root.right.data : "None"));
            return;
        }
        if (key < root.data)
            printParentChild(root.left, key, root);
        else
            printParentChild(root.right, key, root);
    }

    // 4️⃣ In-order traversal
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    // 5️⃣ Save BST to file using in-order traversal
    void saveToFile(Node root, BufferedWriter writer) throws IOException {
        if (root != null) {
            saveToFile(root.left, writer);
            writer.write(root.data + " ");
            saveToFile(root.right, writer);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("Enter number of elements to insert in BST:");
        int n = sc.nextInt();

        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++)
            bst.insert(sc.nextInt());

        System.out.println("\nLeaf nodes:");
        bst.printLeafNodes(bst.root);

        System.out.println("\n\nEnter number to search:");
        int num = sc.nextInt();
        Node result = bst.search(bst.root, num);

        if (result != null)
            System.out.println(num + " found in the BST!");
        else
            System.out.println(num + " not found in the BST!");

        System.out.println("\nParent and Child nodes for " + num + ":");
        bst.printParentChild(bst.root, num, null);

        System.out.println("\nIn-order Traversal:");
        bst.inorder(bst.root);

        // Saving BST to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("bst_data.txt"));
            bst.saveToFile(bst.root, writer);
            writer.close();
            System.out.println("\n\nBST saved to bst_data.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }

        // Reading file content
        System.out.println("\nContents of bst_data.txt:");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("bst_data.txt"));
            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file!");
        }

        sc.close();
    }
}