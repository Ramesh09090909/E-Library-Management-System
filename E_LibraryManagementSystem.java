import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// Book class
class Book {
    int id;
    String title;
    boolean isBorrowed;

    Book(int id, String title) {
        this.id = id;
        this.title = title;
        this.isBorrowed = false;
    }
}

// User class
class User {
    String name;
    ArrayList<Book> borrowedBooks;

    User(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.isBorrowed = true;
    }

    void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.isBorrowed = false;
    }
}

// Main Library System using AWT
public class E_LibraryManagementSystem {
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    static TextArea displayArea = new TextArea();
    static TextField nameField = new TextField();
    static TextField bookIdField = new TextField();

    public static void main(String[] args) {
        // Predefined books in the library
        books.add(new Book(190, "Java Programming"));
        books.add(new Book(245, "Data Structures"));
        books.add(new Book(342, "Operating Systems"));
        books.add(new Book(401, "Programming in c"));
        books.add(new Book(564, "Probability and Statistics"));

        // Create Frame
        Frame frame = new Frame("E-Library Management System");
        frame.setSize(700, 500);
        frame.setLayout(new FlowLayout());

        // Create buttons for actions
        Button viewBooksButton = new Button("View Available Books");
        Button borrowBookButton = new Button("Borrow a Book");
        Button returnBookButton = new Button("Return a Book");
        Button exitButton = new Button("Exit");

        viewBooksButton.setBackground(Color.MAGENTA);
        viewBooksButton.setForeground(Color.black);

        borrowBookButton.setBackground(Color.MAGENTA);
        borrowBookButton.setForeground(Color.BLACK);

        returnBookButton.setBackground(Color.MAGENTA);
        returnBookButton.setForeground(Color.BLACK);

        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);

        // Label for user input
        Label nameLabel = new Label("Enter your name:");
        Label bookIdLabel = new Label("Enter Book ID:");
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 13);
        nameLabel.setBackground(Color.LIGHT_GRAY);
        bookIdLabel.setBackground(Color.LIGHT_GRAY);
        nameField.setPreferredSize(new Dimension(150, 30));
        bookIdField.setPreferredSize(new Dimension(150, 30));

        nameLabel.setFont(labelFont);
        bookIdLabel.setFont(labelFont);
        nameField.setFont(textFieldFont);
        bookIdField.setFont(textFieldFont);
        viewBooksButton.setFont(buttonFont);
        borrowBookButton.setFont(buttonFont);
        returnBookButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        displayArea.setFont(new Font("Courier New", Font.BOLD, 14));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Add components to frame
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(bookIdLabel);
        frame.add(bookIdField);
        frame.add(viewBooksButton);
        frame.add(borrowBookButton);
        frame.add(returnBookButton);
        frame.add(exitButton);
        frame.add(scrollPane);

        // Set default text for display area
        displayArea.setText("Welcome to the E-Library Management System!\n");
        displayArea.setBackground(Color.white);
        displayArea.setForeground(Color.blue);

        // Event for viewing books
        viewBooksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewBooks();
            }
        });

        // Event for borrowing a book
        borrowBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        // Event for returning a book
        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        // Event for exiting the application
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Function to view all books
    static void viewBooks() {
        StringBuilder booksList = new StringBuilder("Available Books:\n");
        for (Book book : books) {
            booksList.append(book.id).append(". ").append(book.title)
                    .append(book.isBorrowed ? " (Borrowed)" : " (Available)").append("\n");
        }
        displayArea.setText(booksList.toString());
    }

    // Function to borrow a book
    static void borrowBook() {
        String name = nameField.getText();
        String bookIdText = bookIdField.getText();
        if (name.isEmpty() || bookIdText.isEmpty()) {
            displayArea.setText("Please enter both name and book ID.");
            return;
        }

        int bookId = Integer.parseInt(bookIdText);
        User user = findOrCreateUser(name);

        for (Book book : books) {
            if (book.id == bookId && !book.isBorrowed) {
                user.borrowBook(book);
                displayArea.setText("You have borrowed: " + book.title);
                return;
            }
        }
        displayArea.setText("Book not available or invalid ID.");
    }

    // Function to return a book
    static void returnBook() {
        String name = nameField.getText();
        String bookIdText = bookIdField.getText();
        if (name.isEmpty() || bookIdText.isEmpty()) {
            displayArea.setText("Please enter both name and book ID.");
            return;
        }

        int bookId = Integer.parseInt(bookIdText);
        User user = findOrCreateUser(name);

        for (Book book : user.borrowedBooks) {
            if (book.id == bookId) {
                user.returnBook(book);
                displayArea.setText("You have returned: " + book.title);
                return;
            }
        }
        displayArea.setText("You have not borrowed this book.");
    }

    // Find an existing user or create a new one
    static User findOrCreateUser(String name) {
        for (User user : users) {
            if (user.name.equals(name)) {
                return user;
            }
        }
        User newUser = new User(name);
        users.add(newUser);
        return newUser;
    }

    static void displayText(String text) {
        StyledDocument doc = displayArea.getStyledDocument();
        Style style = displayArea.addStyle("Bold", null);
        StyleConstants.setBold(style, true);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
