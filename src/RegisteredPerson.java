import java.io.Serializable;

public class RegisteredPerson extends Person implements Serializable {
    private String govID;

    // Constructor with first name, last name, DOB, and government ID
    public RegisteredPerson(String firstName, String lastName, OCCCDate dob, String govID) {
        super(firstName, lastName, dob);
        this.govID = govID;
    }

    public RegisteredPerson(String firstName, String lastName, String govID) {
        super(firstName, lastName);
        this.govID = govID;
    }

    // Constructor with Person object and government ID
    public RegisteredPerson(Person p, String govID) {
        super(p);
        this.govID = govID;
    }

    // Copy constructor
    public RegisteredPerson(RegisteredPerson p) {
        super(p);
        this.govID = p.govID;
    }

    public String getGovernmentID() {
        return govID;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + govID + "]";
    }

    public boolean equals(RegisteredPerson p) {
        return super.equals(p) && this.govID.equalsIgnoreCase(p.govID);
    }

    public boolean equals(Person p) {
        return super.equals(p); // Compare only name and DOB, ignore govID
    }
}