import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Drawboard extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
    Image image;
    JPanel content;
    final int imageMatrixSize = 32;
    int gridSquareSize;
    JButton save;
    JButton delete;
    JButton view;
    DBCommunicator db;

    public Drawboard() {
        db = new DBCommunicator();
        image = new Image(imageMatrixSize);

        content = new Content(imageMatrixSize);
        setContentPane(content);

        save = new JButton("Save");
        content.add(save);
        save.addActionListener(this);

        delete = new JButton("Delete");
        content.add(delete);
        delete.addActionListener(e -> {
            image.imageReset();
            repaint();
        });

        view = new JButton("View");
        content.add(view);
        view.addActionListener(e -> {
            new ImageViewer(db.getImagesFromDB());
            this.dispose();
        });

        setBounds(0,0,800,800);
        gridSquareSize = this.getWidth() / imageMatrixSize;
        setTitle("Drawing");

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        addMouseMotionListener(this);
        setResizable(false);
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Closing");
                db.closeDB();
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / gridSquareSize;
        int y = e.getY() / gridSquareSize - 1;
        if(x >= imageMatrixSize || y >= imageMatrixSize || x < 0 || y < 0) return;
        image.setPoint(x, y, 1);
        content.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == save) {
            int input = -1;
            while(input == -1) {
                try{
                    input = Integer.parseInt(JOptionPane.showInputDialog(content, "Enter expected number:"));
                    if(input < 0 || input > 9) {
                        input = -1;
                        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            image.setExpectedNumber(input);
            image.saveToBD(db);
            image.imageReset();
            repaint();
        }
    }

    public class Content extends JPanel {
        int imageSize;
        public Content(int matrixSize) {
            imageSize = matrixSize;
        }

        public void paintComponent(Graphics g) {
            paintMatrix(g);
            drawGrid(g);
        }

        public void drawGrid(Graphics g){
            g.setColor(Color.GRAY);
            for(int i = 0; i < imageMatrixSize; i++){
                g.drawLine(i * gridSquareSize, 0, i * gridSquareSize, getHeight());
                g.drawLine(0, i * gridSquareSize, getWidth(),i * gridSquareSize);
            }
        }

        public void paintMatrix(Graphics g){
            g.setColor(Color.RED);
            for(int i = 0; i < imageMatrixSize; i++){
                for(int j = 0; j < imageMatrixSize; j++){
                    if(image.getImageMatrix()[i][j] == 1){
                        g.fillRect(i * gridSquareSize, j * gridSquareSize, gridSquareSize, gridSquareSize);
                    }
                }
            }
        }
    }
}
