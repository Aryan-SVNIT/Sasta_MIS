package Student;

public class TeachingAssistant extends Student {

    private String assignedCourseId;

    public TeachingAssistant(int id, String name, String courseId) {
        super(id, name);
        this.assignedCourseId = courseId;
    }

    public void viewStudentGrades() {
        System.out.println("Viewing grades for course: " + assignedCourseId);
        TAService.viewGrades(assignedCourseId);
    }

    public void updateStudentGrade(int studentId, int marks) {
        System.out.println("Updating grade...");
        TAService.updateGrade(studentId, assignedCourseId, marks);
    }

    public String getAssignedCourseId() {
        return assignedCourseId;
    }
}