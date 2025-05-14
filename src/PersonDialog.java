import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PersonDialog extends JDialog {
    private JTextField firstNameField = new JTextField(10);
    private JTextField lastNameField = new JTextField(10);
    private JTextField dobField = new JTextField(10); // Format: YYYY-MM-DD
    private JTextField govIDField = new JTextField(10);
    private JTextField studentIDField = new JTextField(10);
    private JComboBox<String> typeBox = new JComboBox<>(new String[]{"Person", "RegisteredPerson", "OCCCPerson"});

    private boolean confirmed = false;

    public static Person showDialog(JFrame parent, Person person) {
        PersonDialog dialog = new PersonDialog(parent, person);
        dialog.setVisible(true);
        return dialog.confirmed ? dialog.getPerson() : null;
    }

    private PersonDialog(JFrame parent, Person person) {
        super(parent, "Person Details", true);
        setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2));
        fieldsPanel.add(new JLabel("Type:"));
        fieldsPanel.add(typeBox);
        fieldsPanel.add(new JLabel("First Name:"));
        fieldsPanel.add(firstNameField);
        fieldsPanel.add(new JLabel("Last Name:"));
        fieldsPanel.add(lastNameField);
        fieldsPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        fieldsPanel.add(dobField);
        fieldsPanel.add(new JLabel("Government ID:"));
        fieldsPanel.add(govIDField);
        fieldsPanel.add(new JLabel("Student ID:"));
        fieldsPanel.add(studentIDField);

        add(fieldsPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        cancelButton.addActionListener(e -> setVisible(false));

        // Fill in fields if editing
        if (person != null) {
            firstNameField.setText(person.getFirstName());
            lastNameField.setText(person.getLastName());
            dobField.setText(person.getDOB().toString());

            if (person instanceof OCCCPerson op) {
                typeBox.setSelectedItem("OCCCPerson");
                govIDField.setText(op.getGovernmentID());
                studentIDField.setText(op.getStudentID());
            } else if (person instanceof RegisteredPerson rp) {
                typeBox.setSelectedItem("RegisteredPerson");
                govIDField.setText(rp.getGovernmentID());
            } else {
                typeBox.setSelectedItem("Person");
            }
        }

        typeBox.addActionListener(e -> updateFieldVisibility());
        updateFieldVisibility();

        pack();
        setLocationRelativeTo(parent);
    }

    private void updateFieldVisibility() {
        String selected = (String) typeBox.getSelectedItem();
        govIDField.setEnabled(selected.equals("RegisteredPerson") || selected.equals("OCCCPerson"));
        studentIDField.setEnabled(selected.equals("OCCCPerson"));
    }

    private Person getPerson() {
        String fn = firstNameField.getText().trim();
        String ln = lastNameField.getText().trim();

        // Validate first name and last name
        if (fn.isEmpty() || ln.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First and last name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        OCCCDate dob = null;
        try {
            dob = OCCCDate.parse(dobField.getText().trim()); // Assuming this method exists in OCCCDate
            if (dob == null) {
                throw new IllegalArgumentException("Invalid date format.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Date format is invalid. Please use the format YYYY-MM-DD.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String govID = govIDField.getText().trim();
        String studentID = studentIDField.getText().trim();
        String type = (String) typeBox.getSelectedItem();

        // Validate government ID and student ID for specific types
        if (type.equals("RegisteredPerson") || type.equals("OCCCPerson")) {
            if (govID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Government ID cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        if (type.equals("OCCCPerson")) {
            if (studentID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Student ID cannot be empty for OCCCPerson.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        // Return the appropriate person type
        try {
            return switch (type) {
                case "OCCCPerson" -> new OCCCPerson(fn, ln, dob, govID, studentID);
                case "RegisteredPerson" -> new RegisteredPerson(fn, ln, dob, govID);
                default -> new Person(fn, ln, dob);
            };
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating person: " + ex.getMessage(), "Creation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
