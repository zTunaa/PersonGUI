import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonDialog extends JDialog {
    private JTextField firstNameField = new JTextField(10);
    private JTextField lastNameField = new JTextField(10);
    private JTextField dobField = new JTextField(10); // Format: MM-dd-yyyy
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
        fieldsPanel.add(new JLabel("Date of Birth (MM-dd-yyyy):"));
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
        String dobString = dobField.getText().trim();  // Get raw input

        // Validate the date format before proceeding
        OCCCDate dob = validateDateFormat(dobString);
        if (dob == null) {  // If the date is invalid
            return null;  // Do not proceed with creating the person object
        }

        String govID = govIDField.getText().trim();
        String studentID = studentIDField.getText().trim();
        String type = (String) typeBox.getSelectedItem();

        // Proceed to create the person object
        return switch (type) {
            case "OCCCPerson" -> new OCCCPerson(fn, ln, dob, govID, studentID);
            case "RegisteredPerson" -> new RegisteredPerson(fn, ln, dob, govID);
            default -> new Person(fn, ln, dob);
        };
    }

    // Method to validate the date format (MM-dd-yyyy)
    private OCCCDate validateDateFormat(String date) {
        // Define the expected date format (MM-dd-yyyy)
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sdf.setLenient(false);  // Make sure the date is strictly validated

        try {
            // Try parsing the input date
            sdf.parse(date);  // Will throw ParseException if the format is incorrect

            // If parsing is successful, return the parsed OCCCDate
            return new OCCCDate(Integer.parseInt(date.substring(3, 5)),
                    Integer.parseInt(date.substring(0, 2)),
                    Integer.parseInt(date.substring(6, 10)));

        } catch (ParseException ex) {
            // If the date is invalid, show an error message
            JOptionPane.showMessageDialog(this, "Invalid date format. Please enter the date in MM-dd-yyyy format.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;  // Return null to indicate an error
        }
    }
}
