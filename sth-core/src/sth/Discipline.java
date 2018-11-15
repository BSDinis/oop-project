package sth;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;

/**
 * Discipline implementation.
 */
public class Discipline 
  implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151044L;

  private int _capacity;
  private List<Student> _students;
  private List<Professor> _professors;
  private Map<String, Project> _projects = new TreeMap<String, Project>();

  public Discipline(int cap, List<Student> students, List<Professor> profs) {
    if (students.size() > cap) throw IllegalArgumentException();
    _capacity = cap;
    _students = students;
    _professors = profs;
  }
  
  public void addProject(String name) { _projects.put(name, new Project(name)); }
  public boolean hasProject(String name) { return _projects.containsKey(name); }
  public Project getProject(String name) { return _projects.get(name); }
  public Collection<Student> getStudents() { return _students; }

  public String toString() { return "<<Discipline :: to implement>>"; }

}
