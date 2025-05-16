import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class PersonGUI extends JFrame {
    private ArrayList<Person> people = new ArrayList<>();
    private JComboBox<Person> personComboBox = new JComboBox<>();

    private JPanel detailsPanel = new JPanel(new GridLayout(0, 2));
    private JLabel nameLabel = new JLabel();
    private JLabel dobLabel = new JLabel();
    private JLabel ageLabel = new JLabel();
    private JLabel govIDLabel = new JLabel();
    private JLabel studentIDLabel = new JLabel();

    public PersonGUI() {
        setTitle("Person Manager");
        setSize(500, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        // Confirm before exiting via window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(PersonGUI.this, "Exit the application?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        editMenu.add(addItem);
        editMenu.add(editItem);
        editMenu.add(deleteItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        // Combo box panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Person:"));
        topPanel.add(personComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Details panel (scrollable)
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(nameLabel);
        detailsPanel.add(new JLabel("Date of Birth:"));
        detailsPanel.add(dobLabel);
        detailsPanel.add(new JLabel("Age:"));
        detailsPanel.add(ageLabel);
        detailsPanel.add(new JLabel("Gov ID:"));
        detailsPanel.add(govIDLabel);
        detailsPanel.add(new JLabel("Student ID:"));
        detailsPanel.add(studentIDLabel);

        // Add bottom border to each label and value component for separation
        for (Component comp : detailsPanel.getComponents()) {
            if (comp instanceof JComponent jc) {
                jc.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            }
        }

        JScrollPane detailsScrollPane = new JScrollPane(detailsPanel);
        detailsScrollPane.setPreferredSize(new Dimension(400, 300));
        add(detailsScrollPane, BorderLayout.CENTER);

        // Listeners
        personComboBox.addActionListener(e -> displaySelectedPerson());

        addItem.addActionListener(e -> {
            Person p = PersonDialog.showDialog(this, null);
            if (p != null) {
                people.add(p);
                updateComboBox();
                personComboBox.setSelectedItem(p);
            }
        });

        editItem.addActionListener(e -> {
            Person selected = (Person) personComboBox.getSelectedItem();
            if (selected != null) {
                Person updated = PersonDialog.showDialog(this, selected);
                if (updated != null) {
                    people.set(people.indexOf(selected), updated);
                    updateComboBox();
                    personComboBox.setSelectedItem(updated);
                }
            }
        });

        deleteItem.addActionListener(e -> {
            Person selected = (Person) personComboBox.getSelectedItem();
            if (selected != null) {
                int result = JOptionPane.showConfirmDialog(this, "Delete this person?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    people.remove(selected);
                    updateComboBox();
                }
            }
        });

        saveItem.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fc.getSelectedFile()))) {
                    out.writeObject(people);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file.");
                }
            }
        });

        loadItem.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fc.getSelectedFile()))) {
                    people = (ArrayList<Person>) in.readObject();
                    updateComboBox();
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading file.");
                }
            }
        });

        exitItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "Exit the application?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void updateComboBox() {
        people.sort(Comparator.comparing(Person::toString)); // Sort by name
        personComboBox.removeAllItems();
        for (Person p : people) {
            personComboBox.addItem(p);
        }
        displaySelectedPerson();
    }

    private void displaySelectedPerson() {
        Person p = (Person) personComboBox.getSelectedItem();
        if (p == null) {
            nameLabel.setText("");
            dobLabel.setText("");
            ageLabel.setText("");
            govIDLabel.setText("");
            studentIDLabel.setText("");
            return;
        }

        nameLabel.setText(p.getFirstName() + " " + p.getLastName());
        dobLabel.setText(p.getDOB().toString());
        ageLabel.setText(String.valueOf(p.getAge()));

        if (p instanceof RegisteredPerson rp) {
            govIDLabel.setText(rp.getGovernmentID());
        } else {
            govIDLabel.setText("—");
        }

        if (p instanceof OCCCPerson op) {
            studentIDLabel.setText(op.getStudentID());
        } else {
            studentIDLabel.setText("—");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(PersonGUI::new);
    }
}
