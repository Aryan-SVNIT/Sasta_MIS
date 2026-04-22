package Student;

public class Student {
    protected int studentId;
    protected String name;

    public Student(int id, String name) {
        this.studentId = id;
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void viewCourses() {
        StudentService.viewCourses();
    }

    public void registerCourse() {
        StudentService.registerCourse();
    }
}