import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 *
 * Note: This is like a tree, but some nodes in the structure have two parents.
 *
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 *
 *                  a
 *                b   c
 *              d   e   f
 *            h   i   j   k
 *
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 *
 *
 * See NumberTriangleTest.java for a few basic test cases.
 *
 * Extra: If you decide to solve the Project Euler problems (see main),
 *        feel free to add extra methods to this class. Just make sure that your
 *        code still compiles and runs so that we can run the tests on your code.
 *
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     *
     * Hint: think recursively and use the idea of partial tracing from first year :)
     *
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        // for fun [not for credit]:
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     *
     * You can decide if you want to use a recursive or an iterative approach in your solution.
     *
     * You can assume that:
     *      the length of path is less than the height of this NumberTriangle structure.
     *      each character in the string is either 'l' or 'r'
     *
     * @param path the path to follow through this NumberTriangle
     * @return the root value at the location indicated by path
     *
     */
    public int retrieve(String path) {
        if (!path.isEmpty()) {
            switch (path.charAt(0)) {
                case 'l':
                    return this.left.retrieve(path.substring(1, path.length()));

                case 'r':
                    return this.right.retrieve(path.substring(1, path.length()));
            }
        }
        else{
            return this.getRoot();
        }
        return -1;
    }

    // A helper method for loadTriangle method that recursively create NumberTriangles and assign the corresponding left and right.
    private static NumberTriangle[] _loadTriangleHelper(NumberTriangle[] triangleTops, String[] numbers) {

        if (numbers.length != 0) {

            // nextRow contains the child numbers of the NumberTriangles in triangleTops
            String[] nextRow = Arrays.copyOfRange(numbers, 0, triangleTops.length + 1);

            // otherRows contain rest of the numbers
            String[] otherRows = Arrays.copyOfRange(numbers, triangleTops.length + 1, numbers.length);
            NumberTriangle[] newTriangles = new NumberTriangle[nextRow.length];

            // Create NumberTriangles for the numbers on the next row
            for (int a = 0; a < nextRow.length; a++) {
                newTriangles[a] = new NumberTriangle(Integer.parseInt(nextRow[a]));
            }

            // Assign left and right to the newTriangles recursively
            newTriangles = _loadTriangleHelper(newTriangles, otherRows);

            // Assign left and right for each of the triangleTop
            for (int i = 0; i < triangleTops.length; i++) {
                triangleTops[i].setLeft(newTriangles[i]);
                triangleTops[i].setRight(newTriangles[i + 1]);
            }


        }
        return triangleTops;
    }

    /** Read in the NumberTriangle structure from a file.
     *
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     *
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<String[]> rows = new ArrayList<>();// Stores each line's numbers
        int totalNumbers = 0;// Count for the total number of the numbers in the .txt file
        int allNumbers_index = 0;// Index of allNumbers

        // will need to return the top of the NumberTriangle,
        // so might want a variable for that.
        NumberTriangle top = null;

        String line = br.readLine();

        while (line != null) {

            // remove when done; this line is included so running starter code prints the contents of the file
            System.out.println(line);

            String[] content = line.split(" ");
            rows.add(content);
            totalNumbers += content.length;

            //read the next line
            line = br.readLine();
        }

        String[] allNumbers = new String[totalNumbers];// Stores all the numbers in a String[]
        for  (String[] row : rows) {
            for (String number : row) {
                allNumbers[allNumbers_index] = number;
                allNumbers_index++;
            }
        }
        // Initiating the recursive process of constructing the NumberTriangle from the top
        top = _loadTriangleHelper(new NumberTriangle[]{new NumberTriangle(Integer.parseInt(allNumbers[0]))}, Arrays.copyOfRange(allNumbers, 1, totalNumbers))[0];

        br.close();
        return top;
    }

    public static void main(String[] args) throws IOException {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        mt.maxSumPath();
        System.out.println(mt.getRoot());
    }
}
