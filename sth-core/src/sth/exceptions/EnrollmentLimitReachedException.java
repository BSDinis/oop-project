package sth.exceptions;

/**
 * Thrown when a student cannot enroll in any more disciplines
 */
public class EnrollmentLimitReachedException extends Exception {
  private String _disciplineName;
  private int _studentId;
  public EnrollmentLimitReachedException(int id, String disciplineName) {
    _studentId = id;
    _disciplineName = disciplineName;
  }
  public int getId() { return _studentId; }
  public String getDisciplineName() { return _disciplineName; }
}
