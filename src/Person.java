import java.io.Serializable;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private OCCCDate dob;

    // CONSTRUCTOR FOR FIRST AND LAST NAME
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = new OCCCDate(); // Default to current date
    }

    // CONSTRUCTOR FOR FIRST, LAST, AND DOB
    public Person(String firstName, String lastName, OCCCDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    // COPY CONSTRUCTOR
    public Person(Person p) {
        this.firstName = p.firstName;
        this.lastName = p.lastName;
        this.dob = new OCCCDate(p.dob); // Copy dob
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public OCCCDate getDOB() {
        return dob;
    }

    public int getAge() {
        return dob.getDifferenceInYears(); // Calculate age using OCCCDate
    }

    public boolean equals(Person p) {
        return this.firstName.equalsIgnoreCase(p.firstName) &&
                this.lastName.equalsIgnoreCase(p.lastName);
    }


    @Override
    public String toString() {
        return lastName + ", " + firstName + " (" + dob.toString() + ")";
    }

    public void eat() {
        System.out.println(getClass().getSimpleName() + " " + this.toString() + " is eating...");
    }

    public void sleep() {
        System.out.println(getClass().getSimpleName() + " " + this.toString() + " is sleeping...");
    }

    public void play() {
        System.out.println(getClass().getSimpleName() + " " + this.toString() + " is playing...");
    }

    public void run() {
        System.out.println(getClass().getSimpleName() + " " + this.toString() + " is running...");
    }

    @Override
    public int compareTo(Person other) {
        int lastCmp = this.lastName.compareToIgnoreCase(other.lastName);
        if (lastCmp != 0) return lastCmp;
        return this.firstName.compareToIgnoreCase(other.firstName);
    }
}