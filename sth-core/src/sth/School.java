package sth;

import java.io.IOException;
import java.io.Serializable;
import java.lang.UnsupportedOperationException;

import java.util.Map;
import java.util.TreeMap;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;

import sth.exceptions.BadEntryException;
import sth.exceptions.DisciplineNotFoundException;


/**
 * School implementation.
 */
class School implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;

  private int _currentId = 100000; // Initial ID
  private Map<Integer, Student> _students = new TreeMap<Integer, Student>();
  private Map<Integer, Professor> _professors = new TreeMap<Integer, Professor>();
  private Map<Integer, Staffer> _staffers = new TreeMap<Integer, Staffer>();
  private List<Course> _courses = new LinkedList<Course>();

  public Student addStudent(Student s) {
    _students.put(s.id(), s);
    return s;
  }

  public Professor addProfessor(Professor p) {
    _professors.put(p.id(), p);
    return p;
  }

  public Staffer addStaffer(Staffer s) {
    _staffers.put(s.id(), s);
    return s;
  }

  public Course addCourse(Course c) {
    _courses.add(c);
    return c;
  }


  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  void importFile(String filename)
    throws IOException, BadEntryException {

    BufferedReader in = new BufferedReader(new FileReader(filename));
    PersonParser p = new PersonParser(this, in);
    while (p.parsePerson()); // parse all people
  }
  
  void saveToFile(String filename)
    throws IOException, UnsupportedOperationException  {
  }

  public boolean lookupId(int id) {
    return _students.containsKey(id) || _professors.containsKey(id) || _staffers.containsKey(id);
  }
  
  public boolean isAdministrative(int id) {
    return _staffers.containsKey(id);
  }

  public boolean isProfessor(int id) {
    return _professors.containsKey(id);
  }

  public boolean isStudent(int id) {
    return _students.containsKey(id);
  }

  public boolean isRepresentative(int id) {
    if (!isStudent(id)) return false;
    for (Course c : _courses)
      if (c.hasRepresentative(id)) return true;

    return false;
  }

  public Collection<Person> people() {
    Collection<Person> allPeople = new ArrayList<Person>();
    allPeople.addAll(_students.values());
    allPeople.addAll(_staffers.values());
    allPeople.addAll(_professors.values());
    return allPeople;
  }

  public Person getPersonById(int id) {
    Person p;
    p = getStafferById(id);
    if (p != null) return p;

    p = getStudentById(id);
    if (p != null) return p;

    p = getProfessorById(id);
    return p;
  }

  public Staffer getStafferById(int id) {
    return _staffers.get(id);
  }

  public Student getStudentById(int id) {
    return _students.get(id);
  }

  public Professor getProfessorById(int id) {
    return _professors.get(id);
  }

  boolean hasCourse(String name) {
    for (Course c : _courses) {
      if (c.name().equals(name))
        return true;
    }

    return false;
  }

  Course getCourseByName(String name) {
    for (Course c : _courses) {
      if (c.name().equals(name))
        return c;
    }

    return null;
  }

  public Collection<Person> getPersonByName(String name) {
    Collection<Person> result = new ArrayList<Person>();

    for (Person p : people())
      if (name.equals(p.name()))
        result.add(p);

    return result;
  }

  public Discipline getDiscipline(String name) {
    for (Course c : _courses)
      if (c.hasDiscipline(name))
        return c.getDiscipline(name); 
    
    return null;
  }

  public Project getProject(String disciplineName, String projectName) throws DisciplineNotFoundException {
    boolean foundDiscipline = false;
    for (Course c : _courses) {
      if (c.hasDiscipline(disciplineName)) {
        foundDiscipline = true;
        Discipline d = c.getDiscipline(disciplineName);
        if (d.hasProject(projectName)) 
          return d.getProject(projectName);
      }
    }

    if (!foundDiscipline) throw new DisciplineNotFoundException(disciplineName);
    return null;
  }
}
