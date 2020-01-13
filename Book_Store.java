import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Book_Store {

    private static String DB_URL = "";
    private static String USER = "";
    private static String PASS = "";
    private static String JDBC_DRIVER = "";
    private static String currentUserOrAdminID = "";
    private static String currentUserOrAdminPassword = "";
    private static Scanner in = new Scanner(System.in); 


public static void main(String[] args) throws Exception {
    
    DB_URL = args[0];
    USER = args[1];
    PASS = args[2];
    JDBC_DRIVER = args[3];
    
       Connection conn=null;
       try {
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           boolean quit = true;
           do {
               System.out.println("--- MAIN MENU ---");
               System.out.println("1 - Login as Admin");
               System.out.println("2 - Login as User");
               System.out.println("3 - Create Account");
               System.out.println("4 - Exit The Program");
               System.out.print("Please select an option: ");

               int option = in.nextInt();

               switch (option) {
                   case 1:
                       System.out.print("Enter AdminID: ");
                       currentUserOrAdminID = in.next();
                       System.out.print("Enter Password: ");
                       currentUserOrAdminPassword = in.next();
                       if (adminLogin(conn, currentUserOrAdminID, currentUserOrAdminPassword)) {
                           System.out.println("Logged in as: " + currentUserOrAdminPassword);
                           adminMenu(conn, currentUserOrAdminID);
                       } else {
                           System.out.println("Invalid Admin Login");
                       }
                       break;
                   case 2:
                       System.out.print("Enter UserID: ");
                       currentUserOrAdminID = in.next();
                       System.out.print("Enter Password: ");
                       currentUserOrAdminPassword = in.next();
                       if (userLogin(conn, currentUserOrAdminID, currentUserOrAdminPassword)) {
                           System.out.println("Logged in as: " + currentUserOrAdminPassword);
                           userMenu(conn, currentUserOrAdminID);
                       } else {
                           System.out.println("Invalid User Login");
                       }
                       break;
                   case 3:
                       CreateUserAccount(conn);
                       break;
                   case 4:
                       quit = true;
                       break;
                   default:
                       System.out.println("Invalid option");
               }
           } while (!quit);
           conn.close();
           in.close();
       }finally {
           if (conn != null) {
               try {
                   conn.close();
               } catch (SQLException e) {
                   System.out.println("Connection Failed To Close");
                   throw e;
               }
           }
           }

   }
    //userMenu, Serves as a method to provide a Menu Once Loged In As A User
    //String userid, serves as the user's userid submitted 
    static void userMenu(Connection conn, String userid) throws Exception {
        

        boolean quit = false;
        int option;
        do {
            System.out.println("--- USER MENU ---");
            System.out.println("1 - List Books");
            System.out.println("2 - Search Book by Title");
            System.out.println("3 - Search Book by Author");
            System.out.println("4 - Search Book by ISBN");
            System.out.println("5 - Order Books");
            System.out.println("0 - Exit Program");
            System.out.print("Please select an option: ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    System.out.println("List of Books:");
                    viewAllBooks(conn);
                    break;
                case 2:
                    System.out.println("Search By Title");
                    SearchBookBy(conn, "Title");
                    break;
                case 3:
                    System.out.println("Search By Author's Name");
                    SearchBookBy(conn, "Author");
                    break;
                case 4:
                    System.out.println("Search By ISBN");
                    SearchBookBy(conn, "ISBN");
                    break;
                case 5:
                    System.out.println("Checkout");
                    checkOut(conn);
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!quit);

        in.close();
    }
    //adminMenu, serves as a Menu once, Logged In As An Admin
    //Connection conn, is the database connection object
    //adminid is the Admins id as an input
    static void adminMenu(Connection conn, String adminid) throws Exception {
        

        boolean quit = false;
        int option;
        do {
            System.out.println("--- ADMIN MENU ---");
            System.out.println("1 - Create Book");
            System.out.println("2 - Update Book");
            System.out.println("3 - Delete Book");
            System.out.println("4 - Add User");
            System.out.println("5 - View Purchases");
            System.out.println("6 - Search book by ISBN");
            System.out.println("0 - Exit The Program");
            System.out.print("Please select an option: ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Create Book");
                    createBook(conn);
                    break;
                case 2:
                    System.out.println("Update Book");
                    updateBook(conn);
                    break;
                case 3:
                    System.out.println("Delete Book");
                    deleteBook(conn);
                    break;
                case 4:
                    System.out.println("Add User");
                    CreateUserAccount(conn);
                    break;
                case 5:
                    System.out.println("View Purchases");
                    viewPurchases(conn);
                    break;
                case 6:
                    System.out.println("Search book by ISBN");
                    SearchBookBy(conn, "ISBN");
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!quit);

        in.close();
    }
    //CheckOut, this method serves as the ordering system for USERS
    //Connection conn, is the database connection object
    static void checkOut(Connection conn) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<String> isbnList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            String cartCountQuery = "SELECT count(CartID) AS totalCarts FROM has_Carts";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cartCountQuery);
            int currentCartID;
            rs.next();
            Scanner sc = in;
            currentCartID = rs.getInt("totalCarts") + 1;
            System.out.println("Your Cart ID IS: " + (currentCartID));
            rs.close();
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result Set Failed To Close");
                    throw e;
                }
            }
            String InserCart = "INSERT INTO has_carts (CartID, UserID) VALUES (" + currentCartID + ",\"" + currentUserOrAdminID + "\")";
            //System.out.println(InserCart);
            stmt.executeUpdate(InserCart);
            InserCart = "INSERT INTO Shopping_Cart values(" + currentCartID + ", " + 0 + ")";
            stmt.executeUpdate(InserCart);
            System.out.println("Please Enter The Books ISBNS That You Would Like To Add To Your Cart And Their Quantity.\nWhen Done Please Type 0 As The ISBN To Exit");
            String ISBN = "-1";
            int Quantity = 0;
            double total = 0.0;

            while (!ISBN.equals("0")) {
                if (total != 0.0) {
                    System.out.println(Arrays.toString(isbnList.toArray()));
                    System.out.println("Total Cost: " + String.format("%.2f", total));
                }
                System.out.print("ISBN: ");
                ISBN = sc.next();
                if (ISBN.equals("0")) {
                    break;
                }
                System.out.print("Quantity: ");
                Quantity = sc.nextInt();
                //SELECT Price FROM book where ISBN = 
                String priceTitle = "SELECT Price, Title FROM Book Where ISBN = " + ISBN;
                rs = stmt.executeQuery(priceTitle);
                rs.next();
                String price = rs.getString("Price");
                //System.out.println("Adding: " + rs.getString("Title") );
                for (int i = 0; i < Quantity; i++) {
                    isbnList.add(rs.getString("Title"));
                }
                total += Quantity * Double.parseDouble(price);
                rs.close();
                //

                String SQL = "INSERT INTO CONTAINS values(" + currentCartID + ", " + ISBN + ", " + Quantity + ")";
                stmt.executeUpdate(SQL);
            }
            System.out.println("Do You Want To Purchase Your Cart Enter 1 for Yes 0 for No?");
            if (1 == sc.nextInt()) {
                String SQL = "UPDATE Shopping_Cart SET Purchased_yn=1 WHERE CartID=" + currentCartID;
                stmt.executeUpdate(SQL);
                System.out.println("Purchase Completed:");
                System.out.println(Arrays.toString(isbnList.toArray()));
                System.out.println("Total Cost: " + String.format("%.2f", total));
            } else {
                String SQL = "DELETE FROM Shopping_Cart WHERE CartID= " + currentCartID;
                stmt.executeUpdate(SQL);
                SQL = "DELETE FROM has_Carts WHERE CartID= " + currentCartID;
                stmt.executeUpdate(SQL);
                SQL = "DELETE FROM CONTAINS WHERE CartID= " + currentCartID;
            }
            rs.close();
            stmt.close();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result Set Failed To Close");
                    throw e;
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
    //userLogin, serves to verify login info
    //Connection conn, is the database connection object
    //String userid, is the user's userid input
    //String password, serves as the user's password input
    static boolean userLogin(Connection conn, String userid, String password) throws Exception {
        PreparedStatement queryPrepared = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            String sql = "SELECT UserID, Password FROM USER WHERE UserID LIKE ? AND Password LIKE ?";
            queryPrepared = conn.prepareStatement(sql);
            queryPrepared.setString(1, userid);
            queryPrepared.setString(2, password);
            rs = queryPrepared.executeQuery();
            if (rs.next() == false) {
                //doesn't match a login
                rs.close();
                return false;
            }
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Result Set Failed To Close");
                    throw e;
                }
            }
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
        return true;
    }
    //userLogin, serves to verify login info
    //Connection conn, is the database connection object
    //String adminid, is the admin's adminid input
    //String password, serves as the admin's password input
    static boolean adminLogin(Connection conn, String adminid, String password) throws Exception {
        PreparedStatement queryPrepared = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            String sql = "SELECT AdminID, Password FROM ADMINISTRATOR WHERE AdminID LIKE ? AND Password LIKE ?";
            queryPrepared = conn.prepareStatement(sql);
            queryPrepared.setString(1, adminid);
            queryPrepared.setString(2, password);
            rs = queryPrepared.executeQuery();
            if (rs.next() == false) {
                //doesn't match a login
                rs.close();
                return false;
            }
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
        return true;
    }
    //CreateUserAccount, serves as the method to add a user account to the database
    //Connection conn, is the database connection object
    static void CreateUserAccount(Connection conn) {
        PreparedStatement queryPrepared = null;
        try {

            String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            queryPrepared = conn.prepareStatement(sql);
            Scanner sc = in;
            System.out.print("Please Enter A UserID: ");
            String userID = sc.next();
            queryPrepared.setString(1, userID);
            System.out.print("Please Enter A Last Name: ");
            String last_Name = sc.next();
            queryPrepared.setString(2, last_Name);
            System.out.print("Please Enter A Middle Name: ");
            String middleName = sc.next();
            queryPrepared.setString(3, middleName);
            System.out.print("Please Enter A First Name: ");
            String firstName = sc.next();
            queryPrepared.setString(4, firstName);
            System.out.print("Please Enter A Password: ");
            String password = sc.next();
            sc.nextLine(); // eat '\n'
            queryPrepared.setString(5, password);
            System.out.print("Please Enter A Street Adress: ");
            String street = sc.nextLine();
            queryPrepared.setString(6, street);
            System.out.print("Please Enter A City Name ");
            String city = sc.nextLine();
            queryPrepared.setString(7, city);
            System.out.print("Please Enter The Name Of The State You Would Like To Use: ");
            String state = sc.next();
            queryPrepared.setString(8, state);
            System.out.print("Please Enter Your ZIP Code: ");
            int zip = sc.nextInt();
            queryPrepared.setInt(9, zip);
            System.out.print("Please Enter A Email Adress: ");
            String emailAdress = sc.next();
            queryPrepared.setString(10, emailAdress);
            System.out.print("Please Enter A Phone Number: ");
            String phoneNumber = sc.next();
            queryPrepared.setString(11, phoneNumber);
            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("User Created");
            } else {
                System.out.println("User Creation Failed UserID Already Exist...");
            }
            // sc.close(); don't close or will close system.in for menu loop.
            // close in menu
            queryPrepared.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                }
            }
        }
    }
    //SerchBookBy, serves as a method to allow finding book data
    //Connection conn, is the database connection object input
    //String q, this serves as input to pick what type of search q=ISBN means search by ISBN
    //q=Title, means search by the title name
    //q=Author means search by a authors name
    static void SearchBookBy(Connection conn, String q) throws SQLException {
        PreparedStatement queryPrepared = null;
        ResultSet rs = null;
        Scanner sc = in;

        try {
            String input = "";
            String sql = "";
            if (q.equals("ISBN")) {
                sql = "SELECT * FROM Book WHERE ISBN = ?";
                queryPrepared = conn.prepareStatement(sql);
                System.out.print("Please Enter A Book's ISBN: ");
                sc.nextLine();
                input = sc.nextLine();
                queryPrepared.setString(1, input);
            } else if (q.equals("Title")) {
                sql = "SELECT * FROM Book WHERE Title = ?";
                queryPrepared = conn.prepareStatement(sql);
                System.out.print("Please Enter A Book's Name: ");
                sc.nextLine();
                input = sc.nextLine();
                queryPrepared.setString(1, input);
            } else if (q.equals("Author")) {
                sql = "SELECT * FROM Book WHERE ISBN=(SELECT ISBN FROM Wrote WHERE AuthorID=( SELECT AuthorID FROM Author WHERE First_Name = ? AND Last_Name = ?))";
                System.out.print("Please Enter A Author's First Name: ");
                queryPrepared = conn.prepareStatement(sql);
                sc.nextLine();
                input = sc.nextLine();
                queryPrepared.setString(1, input);
                System.out.print("Please Enter A Author's Last Name: ");
                input = sc.nextLine();
                queryPrepared.setString(2, input);
            }

            rs = queryPrepared.executeQuery();

            if (rs.next() == false) {
                System.out.println("No Book Matching " + q + " = " + input);
                return;
            }

            ResultSetMetaData rsmd = rs.getMetaData();

            int colNumber = rsmd.getColumnCount();

            for (int i = 1; i <= colNumber; i++) {
                if (i > 1) {
                    System.out.print(",  ");
                }
                String columnVal = rs.getString(i);
                System.out.print(rsmd.getColumnName(i) + " : " + columnVal);
            }
            System.out.println("");

        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
    //createBook, serves as a method to add a book to db
    //Connection conn, is the database connection object input
    static void createBook(Connection conn) {
        PreparedStatement queryPrepared = null;
        try {
            String sql = "INSERT INTO Book VALUES(?,?,?,?,?,?,?)";
            queryPrepared = conn.prepareStatement(sql);
            Scanner sc = in;

            System.out.print("Please Enter An ISBN: ");
            String isbn = sc.next();
            sc.nextLine();
            queryPrepared.setString(1, isbn);

            System.out.print("Please Enter A Title: ");
            String title = sc.nextLine();
            queryPrepared.setString(2, title);

            System.out.print("Please Enter A Published Date: ");
            String date = sc.next();
            sc.nextLine();
            java.sql.Date d = java.sql.Date.valueOf(date);
            queryPrepared.setDate(3, d);

            System.out.print("Please Enter A Publisher: ");
            String publisher = sc.nextLine();
            queryPrepared.setString(4, publisher);

            System.out.print("Please Enter A Price: ");
            double price = sc.nextDouble();
            queryPrepared.setDouble(5, price);

            System.out.print("Please Enter A Genre: ");
            String genre = sc.next();
            sc.nextLine();
            queryPrepared.setString(6, genre);

            System.out.print("Please Enter A Book Description: ");
            String description = sc.nextLine();
            queryPrepared.setString(7, description);

            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("Book Created");
                // Add author.
                addAuthor(conn, isbn);
            } else {
                System.out.println("Book Creation Failed ISBN Already Exists...");
            }

            // sc.close(); don't close
            queryPrepared.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Duplicate Primary Key.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                }
            }
        }
    }
    //addAuthor, serves as a method to add an author to the database
    //Connection conn, is the database connection object input
    static void addAuthor(Connection conn, String isbn) throws SQLException {
        PreparedStatement queryPrepared = null;
        try {

            String sql_author = "SELECT COUNT(*) as ID FROM Author";
            PreparedStatement queryPrepared_author = conn.prepareStatement(sql_author);

            int authorID = -1;

            ResultSet rs = queryPrepared_author.executeQuery();
            if (rs.next()) {
                authorID = rs.getInt("ID");
                authorID++;
            }

            rs.close();
            queryPrepared_author.close();

            String sql = "INSERT INTO Author (AuthorID, Last_Name, First_Name, BirthDate) VALUES(?,?,?,?)";
            queryPrepared = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Scanner sc = in;

            queryPrepared.setInt(1, authorID);

            System.out.print("Please Enter Author's Last Name: ");
            String lname = sc.next();
            sc.nextLine();
            queryPrepared.setString(2, lname);

            System.out.print("Please Enter Author's First Name: ");
            String fname = sc.next();
            queryPrepared.setString(3, fname);

            System.out.print("Please Enter Author's Birth Date: ");
            String date = sc.next();
            java.sql.Date d = java.sql.Date.valueOf(date);
            queryPrepared.setDate(4, d);

            // check if author exists
            if (doesAuthorExist(conn, lname, fname, d)) {
                System.out.println("Can't add an author that already exists");
                return;
            }

            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("Author Created");
            } else {
                System.out.println("Author Already Exists...");
            }

            // add author wrote 
            authorWrote(conn, authorID, isbn);

            // sc.close(); don't close
            queryPrepared.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Duplicate Primary Key.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                }
            }
        }
    }
    //authorWrote, serves as a method to add an author to the database
    //Connection conn, is the database connection object input
    //int authorID, serves as the authorID input for the db
    //String isbn, serves as the string input for the isbn
    static void authorWrote(Connection conn, int authorID, String isbn) {

        PreparedStatement queryPrepared = null;
        try {
            String sql = "INSERT INTO Wrote VALUES(?,?)";
            queryPrepared = conn.prepareStatement(sql);
            queryPrepared.setInt(1, authorID);
            queryPrepared.setString(2, isbn);

            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("Author Wrote Created");
            } else {
                System.out.println("Author Wrote Failed, It Already Exists...");
            }
            // sc.close(); don't close or will close system.in for menu loop.
            // close in menu
            queryPrepared.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                }
            }
        }
    }
    //doesAuthorExist, serves as a method to check if an author exist
    //Connection conn, is the database connection object input
    //String lname, serves as the author's last name input
    //String fname, serves as the author's first name input
    static boolean doesAuthorExist(Connection conn, String lname, String fname, java.sql.Date bday) throws SQLException {
        PreparedStatement queryPrepared = null;

        try {
            String sql_check = "SELECT Last_Name, First_Name, BirthDate FROM Author WHERE Last_Name = ? AND First_Name = ? AND BirthDate = ?";

            queryPrepared = conn.prepareStatement(sql_check);
            queryPrepared.setString(1, lname);
            queryPrepared.setString(2, fname);
            queryPrepared.setDate(3, bday);
            ResultSet rs = queryPrepared.executeQuery();

            if (rs.next() == false) {
                // Author exists
                rs.close();
                return false;
            }
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
        return true;
    }
    //doesAuthorExist, serves as a method to check if an author exist
    //Connection conn, is the database connection object input
    //String lname, serves as the author's last name input
    //String fname, serves as the author's first name input
    static boolean hasBookBeenPurchased(Connection conn, String isbn) throws SQLException {
        PreparedStatement queryPrepared = null;
        int purchased = 0;
        try {
            String sql_check = "SELECT Shopping_Cart.Purchased_yn FROM Shopping_Cart, CONTAINS WHERE ISBN = ? AND Shopping_Cart.CartID = CONTAINS.CartID";

            queryPrepared = conn.prepareStatement(sql_check);
            queryPrepared.setString(1, isbn);

            ResultSet rs = queryPrepared.executeQuery();

            if (rs.next() == false) {
                // ISBN doesn't match a book
                rs.close();
                return false;
            }
            purchased = rs.getInt("ISBN");
            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
        return purchased == 1;
    }
    //deleteBook, serves as a method to delte a book
    //Connection conn, is the database connection object input
    static void deleteBook(Connection conn) throws Exception {
        PreparedStatement queryPrepared = null;

        try {
            Scanner sc = in;
            System.out.print("Please Enter An ISBN of a book to delete it: ");
            String isbn = sc.next();

            if (hasBookBeenPurchased(conn, isbn)) {
                System.out.println("Can't delete a book that has been bought or doesn't exist.");
                //sc.close();
                return;
            }

            String sql = "DELETE FROM Book WHERE ISBN = ?";
            queryPrepared = conn.prepareStatement(sql);

            queryPrepared.setString(1, isbn);

            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("Book Deleted");
            } else {
                System.out.println("Book Deletion Failed, ISBN Does Not Exist...");
            }
            // sc.close(); don't close
            queryPrepared.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
    //updateBook, serves as a method to updateBook a book
    //Connection conn, is the database connection object input
    static void updateBook(Connection conn) throws Exception {
        PreparedStatement queryPrepared = null;
        try {

            Scanner sc = in;
            System.out.print("Please Enter A Book ISBN to update: ");
            String isbn = sc.next();

            if (hasBookBeenPurchased(conn, isbn)) {
                System.out.println("Can't update a book that has been bought or doesn't exist.");
                //sc.close();
                return;
            }

            String sql = "UPDATE Book SET Title = ?, PublishedDate = ?, Publisher = ?, Price = ?,  Genre = ?, Description = ? WHERE ISBN = ?";
            queryPrepared = conn.prepareStatement(sql);

            System.out.print("Please Enter A Title: ");
            String title = sc.nextLine();
            queryPrepared.setString(1, title);

            System.out.print("Please Enter A Published Date: ");
            String date = sc.next();
            java.sql.Date d = Date.valueOf(date);
            queryPrepared.setDate(2, d);

            System.out.print("Please Enter A Publisher: ");
            String publisher = sc.next();
            queryPrepared.setString(3, publisher);

            System.out.print("Please Enter A Price: ");
            double price = sc.nextDouble();
            queryPrepared.setDouble(4, price);

            System.out.print("Please Enter A Genre: ");
            String genre = sc.next();
            sc.nextLine(); // eat '\n'
            queryPrepared.setString(5, genre);

            System.out.print("Please Enter A Book Description: ");
            String description = sc.nextLine();
            queryPrepared.setString(6, description);

            // set ISBN for check
            queryPrepared.setString(7, isbn);

            int i = queryPrepared.executeUpdate();
            if (i != 0) {
                System.out.println("Book Updated");
            } else {
                System.out.println("Book Update Failed ISBN Doesn't Exist...");
            }
            // sc.close(); don't close
            queryPrepared.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
    //viewPurchases, serves as a method to output purchases
    //Connection conn, is the database connection object input
    static void viewPurchases(Connection conn) throws SQLException {
        PreparedStatement queryPrepared = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT CONTAINS.CartID, Book.ISBN, CONTAINS.Quanity, Book.Price, Book.Title, User.UserID, User.First_Name, User.Last_Name, User.Street, User.City, User.State, User.Zip, User.Email FROM CONTAINS, Book, has_Carts, User WHERE CONTAINS.ISBN IN (SELECT CONTAINS.ISBN FROM CONTAINS WHERE CartID IN (SELECT CartID FROM Shopping_Cart WHERE Purchased_yn=1)) AND CONTAINS.CartID = has_carts.CartID AND has_Carts.UserID = User.UserID AND Book.ISBN = CONTAINS.ISBN ORDER BY CONTAINS.CartID ASC";
            queryPrepared = conn.prepareStatement(sql);
            rs = queryPrepared.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= colNumber; i++) {
                    if (i > 1) {
                        System.out.print(",  ");
                    }
                    String columnVal = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + " : " + columnVal);
                }
                System.out.println("");
            }

            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
    //viewAllBooks, serves as a method to output all books
    //Connection conn, is the database connection object input
    static void viewAllBooks(Connection conn) throws SQLException {
        PreparedStatement queryPrepared = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT Book.* FROM Book LEFT JOIN (SELECT CONTAINS.ISBN, SUM(CONTAINS.Quanity) as q FROM CONTAINS Group By CONTAINS.ISBN) con ON con.ISBN = Book.ISBN ORDER BY con.q desc";
            queryPrepared = conn.prepareStatement(sql);
            rs = queryPrepared.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= colNumber; i++) {
                    if (i > 1) {
                        System.out.print(",  ");
                    }
                    String columnVal = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + " : " + columnVal);
                }
                System.out.println("");
            }

            rs.close();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (queryPrepared != null) {
                try {
                    queryPrepared.close();
                } catch (SQLException e) {
                    System.out.println("Statement Failed To Close");
                    throw e;
                }
            }
        }
    }
}
