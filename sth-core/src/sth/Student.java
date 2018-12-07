package sth;

import java.io.Serializable;
import sth.exceptions.EnrollmentLimitReachedException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.SurveyNotFoundException;
import java.util.Collection;

public class Student 
  extends PersonWithDisciplines
  implements Serializable {

  Course _course = null;

  Student(String n, String pN, int id, School s) { super(n, pN, id, s); }

  Course getCourse() { return _course; }

  void enrollInCourse(Course c) { _course = c; }

  void enrollInDiscipline(Discipline d) 
    throws EnrollmentLimitReachedException { 

    Collection<Discipline> col = getDisciplines();
    if (col.size() == 6) 
      throw new EnrollmentLimitReachedException(id(), d.name());

    addDiscipline(d);
  }

  Project getProject(String disciplineName, String projectName) 
    throws DisciplineNotFoundException, ProjectNotFoundException {
    Project p = super.getProject(disciplineName, projectName);
    if (!p.hasSubmissionFrom(this)) 
      throw new ProjectNotFoundException(disciplineName, projectName);

    return p;
  }

  void submitProject(String disciplineName, String projectName, String submission) 
    throws ProjectNotFoundException, DisciplineNotFoundException, ProjectNotOpenException {
    Project p = super.getProject(disciplineName, projectName);
    p.acceptSubmission(this, submission);
  }
}
