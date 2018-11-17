package sth;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;

import sth.exceptions.StudentAlreadyEnrolledException;
import sth.exceptions.ProfessorAlreadyTeachingException;
import sth.exceptions.ProjectAlreadyExistsException;
import sth.exceptions.DisciplineLimitReachedException;
import sth.exceptions.AlienStudentException;
import sth.exceptions.EnrollmentLimitReachedException;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;

public class Discipline 
  implements Serializable {

  private String _name;
  private Course _course;
  private int _capacity;
  private List<Student> _students = new ArrayList<Student>();
  private List<Professor> _professors = new LinkedList<Professor>();
  private Map<String, Project> _projects = new TreeMap<String, Project>();

  Discipline(String name, int cap, Course c) {
    _name = name;
    _capacity = cap;
    _course = c;
  }
  
  public String name() { return _name; }
  public Course course() { return _course; }

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
  public Collection<Student> getStudents() {
    Collections.sort(_students, new Comparator<Student>() {
      public int compare(Student a, Student b) {
        Collator c = Collator.getInstance(Locale.getDefault());
        return c.compare(a.id()+a.name(), b.id()+b.name());
      }
    });

    return _students;
  }


  public void addProject(String name) 
    throws ProjectAlreadyExistsException {
    if (hasProject(name)) 
      throw new ProjectAlreadyExistsException(name(), name);
    _projects.put(name, new Project(name));
  }
  public boolean hasProject(String name) { return _projects.containsKey(name); }
  public Project getProject(String name) { return _projects.get(name); }

  public void addProfessor(Professor p) 
    throws ProfessorAlreadyTeachingException {
    if (_professors.contains(p))
      throw new ProfessorAlreadyTeachingException();

    _professors.add(p);
    p.addDiscipline(this);
  }

  public String toString() { DisciplinePrinter printer = new DisciplineDefaultPrinter(); return printer.format(this); }
}
