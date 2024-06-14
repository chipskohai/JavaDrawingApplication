public class Image {
    private int[][] imageMatrix;
    private int[] inputValues;
    private int expectedNumber;

    public Image(int size) {
        imageMatrix = new int[size][size];
        inputValues = new int[size * size];
    }

    public Image(int expected, String matrix){
        expectedNumber = expected;
        int size = (int) Math.sqrt(matrix.length());
        imageMatrix = new int[size][size];

        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                if (matrix.charAt(counter) == '1') imageMatrix[j][i] = 1;
                counter++;
            }
        }
    }

    public int getSize(){
        return imageMatrix.length;
    }

    public void setExpectedNumber(int expectedNumber) {
        this.expectedNumber = expectedNumber;
    }

    public int getExpectedNumber() {
        return expectedNumber;
    }

    public int[][] getImageMatrix() {
        return imageMatrix;
    }

    public void setPoint(int x, int y, int value) {
        imageMatrix[x][y] = value;
    }

    public void imageReset(){
        imageMatrix = new int[imageMatrix.length][imageMatrix[0].length];
    }

    public int[] calculateInputValues() {
        int counter = 0;
        for(int[] row : imageMatrix) {
            for(int value : row) {
                inputValues[counter] = value;
                counter++;
            }
        }
        return inputValues;
    }

    public int[] getInputValues() {
        return inputValues;
    }

    public String print(StringBuilder sb){
        String s = "";
        for(int i = 0; i<imageMatrix.length; i++){
            for(int j = 0; j<imageMatrix[i].length; j++){
                sb.append(imageMatrix[j][i]);
            }
            sb.append("\n");
        }
        s = sb.toString();
        return s;
    }

    public void saveToBD(DBCommunicator db){
        db.addToBD(expectedNumber, this.print(new StringBuilder()));
    }
}
