import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

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
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Combo box panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Person:"));
        topPanel.add(personComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Details panel
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
        add(detailsPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        personComboBox.addActionListener(e -> displaySelectedPerson());

        addButton.addActionListener(e -> {
            Person p = PersonDialog.showDialog(this, null);
            if (p != null) {
                people.add(p);
                updateComboBox();
                personComboBox.setSelectedItem(p);
            }
        });

        editButton.addActionListener(e -> {
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

        deleteButton.addActionListener(e -> {
            Person selected = (Person) personComboBox.getSelectedItem();
            if (selected != null) {
                people.remove(selected);
                updateComboBox();
            }
        });

        saveButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fc.getSelectedFile()))) {
                    out.writeObject(people);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file.");
                }
            }
        });

        loadButton.addActionListener(e -> {
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

        setVisible(true);
    }

    private void updateComboBox() {
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
        SwingUtilities.invokeLater(PersonGUI::new);
    }
}
