import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ImageViewer extends JFrame {
    ArrayList<Image> original;
    ArrayList<Image> images;
    Image currentImage;
    Content content;
    int imageCounter = 0;
    int gridSquareSize;
    JButton next;
    JButton draw;

    public ImageViewer(ArrayList<Image> images) {
        original = images;
        this.images = images;
        sortByNumber();
        currentImage = images.get(imageCounter);
        setBounds(0,0,800,800);
        content = new Content(currentImage.getSize());
        add(content);

        next = new JButton("Next");
        next.addActionListener(e -> {
            imageCounter = (imageCounter + 1) % images.size();
            currentImage = images.get(imageCounter);
            content.repaint();
        });
        content.add(next);

        draw = new JButton("Draw");
        content.add(draw);
        draw.addActionListener(e -> {
            new Drawboard();
            this.dispose();
        });

        gridSquareSize = getWidth()/currentImage.getSize();
        setTitle("ImageViewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        diashow();
    }

    public void sortByNumber(){
        imageCounter = 0;
        images.sort(Comparator.comparingInt(Image::getExpectedNumber));
    }

    public void getNumber(int number){
        sortByNumber();

        for(Image i : images){
            imageCounter++;
            if(i.getExpectedNumber() == number) return;
        }
    }

    public ArrayList<Image> originalOrder(){
        imageCounter = 0;
        return original;
    }

    public void diashow(){
        try{
            while(true){
                imageCounter = (imageCounter + 1) % images.size();
                currentImage = images.get(imageCounter);
                content.repaint();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public class Content extends JPanel {
        int imageSize;
        public Content(int matrixSize) {
            imageSize = matrixSize;
        }

        public void paintComponent(Graphics g) {
            paintMatrix(g);
            //drawGrid(g);
        }

        public void drawGrid(Graphics g){
            g.setColor(Color.GRAY);
            for(int i = 0; i < currentImage.getSize(); i++){
                g.drawLine(i * gridSquareSize, 0, i * gridSquareSize, getHeight());
                g.drawLine(0, i * gridSquareSize, getWidth(),i * gridSquareSize);
            }
        }

        public void paintMatrix(Graphics g){
            g.clearRect(0,0,getWidth(),getHeight());
            g.setColor(Color.RED);
            for(int i = 0; i < currentImage.getSize(); i++){
                for(int j = 0; j < currentImage.getSize(); j++){
                    if(currentImage.getImageMatrix()[i][j] == 1){
                        g.fillRect(i * gridSquareSize, j * gridSquareSize, gridSquareSize, gridSquareSize);
                    }
                }
            }
        }
    }
}
