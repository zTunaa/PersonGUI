import java.io.Serializable;

public class OCCCPerson extends RegisteredPerson implements Serializable {
    private String studentID;

    // Constructor with RegisteredPerson object and student ID
    public OCCCPerson(RegisteredPerson p, String studentID) {
        super(p);
        this.studentID = studentID;
    }

    public OCCCPerson(String firstName, String lastName, String studentID) {
        super(firstName, lastName, studentID);  // This calls the Person constructor
        this.studentID = studentID;  // Sets the student ID
    }





    // Copy constructor
    public OCCCPerson(OCCCPerson p) {
        super(p);
        this.studentID = p.studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    @Override
    public String toString() {
        return super.toString() + " {" + studentID + "}";
    }

    public boolean equals(OCCCPerson p) {
        return super.equals(p) && this.studentID.equalsIgnoreCase(p.studentID);
    }

    public boolean equals(RegisteredPerson p) {
        return super.equals(p); // Compare only RegisteredPerson fields, ignore studentID
    }

    public boolean equals(Person p) {
        return super.equals(p); // Compare only name and DOB fields
    }
}