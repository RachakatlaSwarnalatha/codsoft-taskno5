import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;
    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getSchedule() {
        return schedule;
    }
    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }
    public void removeStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }
    public String toString() {
        return courseCode + ": " + title + "\nDescription: " + description + "\nSchedule: " + schedule
                + "\nCapacity: " + capacity + ", Enrolled: " + enrolledStudents + "/" + capacity;
    }
}
class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;
    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }
    public boolean registerForCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }
    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
            return true;
        }
        return false;
    }
    public void viewRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("You have not registered for any courses yet.");
        } else {
            System.out.println("Your registered courses:");
            for (Course course : registeredCourses) {
                System.out.println(course);
            }
        }
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }
}

public class StudentCourseRegistrationSystem {

    private static ArrayList<Course> courseList = new ArrayList<>();
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Course course1 = new Course("CS101", "Java Programming", "Learn the fundamentals of Java", 30, "Mon, Wed, Fri - 9:00 AM to 10:30 AM");
        Course course2 = new Course("CS102", "Data Structures", "Introduction to Data Structures", 25, "Tue, Thu - 10:00 AM to 11:30 AM");
        Course course3 = new Course("CS103", "Database Systems", "Introduction to SQL and database design", 40, "Mon, Wed - 2:00 PM to 3:30 PM");
        Course course4 = new Course("CS104", "Software Engineering", "Learn about software development life cycle", 20, "Tue, Thu - 1:00 PM to 2:30 PM");

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. Register as a new student");
            System.out.println("2. View available courses");
            System.out.println("3. Register for a course");
            System.out.println("4. Drop a course");
            System.out.println("5. View registered courses");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    Student newStudent = new Student(studentID, studentName);
                    studentList.add(newStudent);
                    System.out.println("Student " + studentName + " registered successfully.");
                    break;
                case 2:
                    System.out.println("\n--- Available Courses ---");
                    for (Course course : courseList) {
                        System.out.println(course);
                    }
                    break;
                case 3:
                    System.out.print("Enter your student ID: ");
                    studentID = scanner.nextLine();
                    Student student = findStudentByID(studentID);
                    if (student != null) {
                        System.out.print("Enter the course code to register for: ");
                        String courseCode = scanner.nextLine();
                        Course courseToRegister = findCourseByCode(courseCode);
                        if (courseToRegister != null && student.registerForCourse(courseToRegister)) {
                            System.out.println("Successfully registered for " + courseToRegister.getTitle());
                        } else {
                            System.out.println("Course registration failed. Check availability or course code.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter your student ID: ");
                    studentID = scanner.nextLine();
                    student = findStudentByID(studentID);
                    if (student != null) {
                        System.out.print("Enter the course code to drop: ");
                        String courseCode = scanner.nextLine();
                        Course courseToDrop = findCourseByCode(courseCode);
                        if (courseToDrop != null && student.dropCourse(courseToDrop)) {
                            System.out.println("Successfully dropped " + courseToDrop.getTitle());
                        } else {
                            System.out.println("Course drop failed.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter your student ID: ");
                    studentID = scanner.nextLine();
                    student = findStudentByID(studentID);
                    if (student != null) {
                        student.viewRegisteredCourses();
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for using the Student Course Registration System.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static Student findStudentByID(String studentID) {
        for (Student student : studentList) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }
    private static Course findCourseByCode(String courseCode) {
        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
