package sth;

import java.io.Serializable;

import java.lang.IllegalArgumentException;
import sth.exceptions.StudentAlreadyEnrolledException;
import sth.exceptions.ProfessorAlreadyTeachingException;
import sth.exceptions.DisciplineLimitReachedException;
import sth.exceptions.AlienStudentException;
import sth.exceptions.EnrollmentLimitReachedException;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Discipline implementation.
 */
public class Discipline 
  implements Serializable {

  private String _name;
  private Course _course;
  private int _capacity;
  private List<Student> _students = new LinkedList<Student>();
  private List<Professor> _professors = new LinkedList<Professor>();
  private Map<String, Project> _projects = new TreeMap<String, Project>();

  Discipline(String name, int cap) {
    _name = name;
    _capacity = cap;
  }
  
  public String name() { return _name; }

  public void enrollStudent(Student s) 
    throws StudentAlreadyEnrolledException,
                    DisciplineLimitReachedException,
                    EnrollmentLimitReachedException,
                    AlienStudentException {
    if (!s.getCourse().equals(_course)) throw new AlienStudentException();
    if (_students.contains(s)) throw new StudentAlreadyEnrolledException();
    if (_students.size() == _capacity) throw new DisciplineLimitReachedException();
    s.enrollInDiscipline(this);
    _students.add(s);
  }
  public Collection<Student> getStudents() { return _students; }

  public void addProject(String name) { _projects.put(name, new Project(name)); }
  public boolean hasProject(String name) { return _projects.containsKey(name); }
  public Project getProject(String name) { return _projects.get(name); }

  public void addProfessor(Professor p) 
    throws ProfessorAlreadyTeachingException {
    if (_professors.contains(p))
      throw new ProfessorAlreadyTeachingException();

    _professors.add(p);
    p.addDiscipline(this);
  }

  public String toString() { return "<<Discipline :: to implement>>"; }

}
