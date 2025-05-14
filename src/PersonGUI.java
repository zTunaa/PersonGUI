/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class PersonGUI extends JFrame {
    private ArrayList<Person> people = new ArrayList<>();
    private JComboBox<Person> personComboBox = new JComboBox<>();
    private JTextArea detailsArea = new JTextArea(10, 30);
    private File currentFile = null;

    public PersonGUI() {
        super("Person Manager");

        setLayout(new BorderLayout());
        setupMenuBar();

        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton newBtn = new JButton("Add Person");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        bottomPanel.add(new JLabel("Select Person:"));
        bottomPanel.add(personComboBox);
        bottomPanel.add(newBtn);
        bottomPanel.add(editBtn);
        bottomPanel.add(deleteBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        personComboBox.addActionListener(e -> displaySelectedPerson());
        newBtn.addActionListener(e -> addNewPerson());
        editBtn.addActionListener(e -> editSelectedPerson());
        deleteBtn.addActionListener(e -> deleteSelectedPerson());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open...");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As...");
        JMenuItem exit = new JMenuItem("Exit");

        newFile.addActionListener(e -> newFile());
        openFile.addActionListener(e -> openFile());
        saveFile.addActionListener(e -> saveFile());
        saveAsFile.addActionListener(e -> saveFileAs());
        exit.addActionListener(e -> System.exit(0));

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Person Manager GUI\nCreated for OCCC"));

        helpMenu.add(helpItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void newFile() {
        people.clear();
        personComboBox.removeAllItems();
        detailsArea.setText("");
        currentFile = null;
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {
                people.clear();
                while (true) {
                    try {
                        Object o = oin.readObject();
                        if (o instanceof OCCCPerson) {
                            people.add((OCCCPerson) o);
                        } else if (o instanceof Person) {
                            people.add((Person) o);
                        }
                    } catch (EOFException e) {
                        break; // End of file reached
                    }
                }
                updateComboBox();
                currentFile = chooser.getSelectedFile();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load file: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFile))) {
                for (Person p : people) {
                    System.out.println("Saving: " + p);
                    oos.writeObject(p);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
            }
        } else {
            saveFileAs();
        }
    }

    private void saveFileAs() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            saveFile();
        }
    }

    private void updateComboBox() {
        personComboBox.removeAllItems();
        for (Person p : people) {
            personComboBox.addItem(p);
        }
        detailsArea.setText("");
    }

    private void displaySelectedPerson() {
        Person selected = (Person) personComboBox.getSelectedItem();
        if (selected != null) {
            detailsArea.setText(selected.toString());
        }
    }

    private void addNewPerson() {
        String[] options = {"Person", "OCCCPerson"};
        int type = JOptionPane.showOptionDialog(this, "Select type to add:", "New Person",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (type == -1) return;

        String fn = JOptionPane.showInputDialog(this, "First Name:");
        String ln = JOptionPane.showInputDialog(this, "Last Name:");

        if (type == 0) {
            Person newPerson = new Person(fn, ln);
            people.add(newPerson);
            updateComboBox();
            personComboBox.setSelectedItem(newPerson);
        } else {
            String id = JOptionPane.showInputDialog(this, "ID Number:");
            OCCCPerson occc = new OCCCPerson(fn, ln, id);
            people.add(occc);
            updateComboBox();
            personComboBox.setSelectedItem(occc);
        }
    }

    private void editSelectedPerson() {
        Person selected = (Person) personComboBox.getSelectedItem();
        if (selected == null) return;

        String fn = JOptionPane.showInputDialog(this, "First Name:", selected.getFirstName());
        String ln = JOptionPane.showInputDialog(this, "Last Name:", selected.getLastName());

        selected.setFirstName(fn);
        selected.setLastName(ln);

        updateComboBox();
        personComboBox.setSelectedItem(selected);
    }

    private void deleteSelectedPerson() {
        Person selected = (Person) personComboBox.getSelectedItem();
        if (selected != null) {
            people.remove(selected);
            updateComboBox();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PersonGUI::new);
    }
}
*/
