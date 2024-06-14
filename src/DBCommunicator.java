import java.sql.*;
import java.util.ArrayList;

public class DBCommunicator {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DBCommunicator() {
        try{
            String url = "jdbc:mysql://localhost:3306/numbers";
            String user = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, "database1234");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM imagematrix");

        }catch(ClassNotFoundException e){
            System.out.println("Driver not found");
        }catch(SQLException e){
            System.out.println("SQL error");
        }
    }

    public void printDB(){
        try{
            while(resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }
    }

    public void closeDB(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("failed to close connection");
        }
    }

    public void addToBD(int expected, String matrix){
        try{
            PreparedStatement s = connection.prepareStatement("INSERT INTO imagematrix(expectedNumber, matrix) values (?,?)");
            s.setInt(1, expected);
            s.setString(2, matrix);

            int executed = s.executeUpdate();
            if(executed == 1){
                System.out.println("Inserted successfully");
            }
        }
        catch(SQLException e){
            System.out.println("SQL error");
        }
    }

    public void addToDB(){
        new Drawboard();
    }

    public void deleteFromBD(String matrix){
        try {
            PreparedStatement s = connection.prepareStatement("DELETE FROM imagematrix WHERE matrix = ?");
            s.setString(1, matrix);

            int executed = s.executeUpdate();
            if(executed > 1){
                System.out.println("deleted successfully");
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }
    }

    public void deleteFromBD(int expected){
        try {
            PreparedStatement s = connection.prepareStatement("DELETE FROM imagematrix WHERE expectedNumber = ?");
            s.setInt(1, expected);

            int executed = s.executeUpdate();
            if(executed > 0){
                System.out.println("deleted successfully");
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }
    }

    public void resetBD() throws SQLException {
        statement.executeQuery("DELETE FROM imagematrix");
    }

    public ArrayList<Image> getImagesFromDB(){
        ArrayList<Image> images = new ArrayList<>();
        int expected;
        String matrix;
        Image image;

        try{
            while(resultSet.next()){
                expected = resultSet.getInt(1);
                matrix = resultSet.getString(2);
                image = new Image(expected, matrix);
                images.add(image);
            }
        }catch(SQLException e){
            System.out.println("SQL error");
        }

        return images;
    }

    public static void main (String[] args) {

        DBCommunicator db = new DBCommunicator();
        db.deleteFromBD("0000000000000000000000000000\n" +
                "0000000000000000000000000000\n" +
                "0000000000000011000000000000\n" +
                "0000000000011100110000000000\n" +
                "0000000001110000010000000000\n" +
                "0000000011000000011000000000\n" +
                "0000000010000000001000000000\n" +
                "0000000010000000001000000000\n" +
                "0000000010000000001000000000\n" +
                "0000000011000000001000000000\n" +
                "0000000001000000011000000000\n" +
                "0000000000100000010000000000\n" +
                "0000000000110000010000000000\n" +
                "0000000000010000010000000000\n" +
                "0000000000001000100000000000\n" +
                "0000000000000100100000000000\n" +
                "0000000000000011000000000000\n" +
                "0000000000000011000000000000\n" +
                "0000000000000111100000000000\n" +
                "0000000000000100110000000000\n" +
                "0000000000000100011000000000\n" +
                "0000000000000100001000000000\n" +
                "0000000000000100000100000000\n" +
                "0000000000000110000100000000\n" +
                "0000000000000011001100000000\n" +
                "0000000000000001111000000000\n" +
                "0000000000000000000000000000\n" +
                "0000000000000000000000000000\n");
        db.closeDB();
    }
}
