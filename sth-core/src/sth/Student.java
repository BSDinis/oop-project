package sth;

import java.io.Serializable;
import sth.exceptions.EnrollmentLimitReachedException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
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

    if (_disciplines.size() == 6) throw new EnrollmentLimitReachedException();
    addDiscipline(d);
  }
  
  Collection<Discipline> getCourseDisciplines() {
    return _course.getDisciplines(); // invariant: course is not null
  }
  Discipline getCourseDiscipline(String discipline) 
      throws DisciplineNotFoundException {
    Collection<Discipline> disciplines = getCourseDisciplines();
    for (Discipline d : disciplines)
      if (d.name().equals(discipline))
        return d;

    throw new DisciplineNotFoundException(discipline);
  }
  Project getCourseProject(String discipline, String project) 
      throws DisciplineNotFoundException, ProjectNotFoundException {
    Discipline d = getCourseDiscipline(discipline);
    return d.getProject(project);
  }
  Survey getCourseSurvey(String discipline, String project) 
      throws DisciplineNotFoundException, ProjectNotFoundException, SurveyNotFoundException {
    Project p = getCourseProject(discipline, project);
    return p.getSurvey();
  }

}
