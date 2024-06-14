import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        new Drawboard();
        DBCommunicator db = new DBCommunicator();
        ArrayList<Image> images = db.getImagesFromDB();
        ImageViewer imageViewer = new ImageViewer(images);

        db.closeDB();
    }
}