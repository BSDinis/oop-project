package sth;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;

import java.util.Comparator;

import java.util.Map;
import java.util.TreeMap;

import java.util.Collection;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.util.Locale;
import java.text.Collator;
import java.text.RuleBasedCollator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import sth.exceptions.BadEntryException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.DuplicatePersonException;
import sth.exceptions.DuplicateCourseException;
import sth.exceptions.DisciplineNotFoundException;


/**
 * School is where the information is stored and manipulated directly
 */
class School implements Serializable {

  /**
   * Id to assign comming numbers
   */
  private int _currentId = 100000; // Initial ID

  /**
   * Association between id's and students
   */
  private Map<Integer, Student> _students = new TreeMap<>();

  /**
   * Association between id's and professors
   */
  private Map<Integer, Professor> _professors = new TreeMap<>();

  /**
   * Association between id's and staffers
   */
  private Map<Integer, Staffer> _staffers = new TreeMap<>();

  /**
   * List of courses
   */
  private Map<String, Course> _courses = new TreeMap<>();


  /**
   * Reset the school to its original settings
   */
  private void reset() {
    _currentId = 100000;
    _students = new TreeMap<>();
    _professors = new TreeMap<>();
    _staffers = new TreeMap<>();
    _courses = new TreeMap<>();
  }

  /**
   * Add a student to the school
   *
   * @param Student
   * @return Student: allow for `Student s = school.addStudent(new Student(...)) constructs`
   * @throws DuplicatePersonException
   */
  Student addStudent(Student s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException();
    _students.put(s.id(), s);
    return s;
  }

  /**
   * Add a professor to the school
   *
   * @param Professor
   * @return Professor: allow for `Professor s = school.addProfessor(new Professor(...)) constructs`
   * @throws DuplicatePersonException
   */
  Professor addProfessor(Professor p) 
    throws DuplicatePersonException {
    if (lookupId(p.id())) throw new DuplicatePersonException();
    _professors.put(p.id(), p);
    return p;
  }

  /**
   * Add a staffer to the school
   *
   * @param Staffer
   * @return Staffer: allow for `Staffer s = school.addStaffer(new Staffer(...)) constructs`
   * @throws DuplicatePersonException
   */
  Staffer addStaffer(Staffer s) 
    throws DuplicatePersonException {
    if (lookupId(s.id())) throw new DuplicatePersonException();
    _staffers.put(s.id(), s);
    return s;
  }

  /**
   * Add a course to the school
   *
   * @param Course
   * @return Course: allow for `Course s = school.addCourse(new Course(...)) constructs`
   * @throws DuplicateCourseException
   */
  Course addCourse(Course c) 
    throws DuplicateCourseException {
    if (hasCourse(c.name())) throw new DuplicateCourseException();
    _courses.put(c.name(), c);
    return c;
  }


  /**
   * Reads from inputfile
   *
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  void importFile(String filename)
    throws IOException, BadEntryException {
    reset();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    PersonParser p = new PersonParser(this, in);
    while (p.parsePerson()); // parse all people
    in.close();
  }
  
  /**
   * Check if an id is taken
   *
   * @param id 
   * @return whether an id is taken or not
   */
  boolean lookupId(int id) {
    return _students.containsKey(id) || _professors.containsKey(id) || _staffers.containsKey(id);
  }
  
  /**
   * Check if an id corresponds to a staffer or not
   *
   * @param id 
   * @return whether an id is a staffer's id
   */
  boolean isAdministrative(int id) {
    return _staffers.containsKey(id);
  }

  /**
   * Check if an id corresponds to a professor or not
   *
   * @param id 
   * @return whether an id is a professor's id
   */
  boolean isProfessor(int id) {
    return _professors.containsKey(id);
  }

  /**
   * Check if an id corresponds to a student or not
   *
   * @param id 
   * @return whether an id is a student's id
   */
  boolean isStudent(int id) {
    return _students.containsKey(id);
  }

  /**
   * Check if an id corresponds to a representative or not
   *
   * @param id 
   * @return whether an id is a representative's id
   */
  boolean isRepresentative(int id) {
    if (!isStudent(id)) return false;

    Collection<Course> courseCollection = _courses.values();
    for (Course c : courseCollection)
      if (c.hasRepresentative(id)) return true;

    return false;
  }

  /**
   * Get all the people, ordered by id
   *
   * @return Collection of people
   */
  Collection<Person> people() {
    Collection<Person> allPeople = new TreeSet<Person>();
    allPeople.addAll(_students.values());
    allPeople.addAll(_staffers.values());
    allPeople.addAll(_professors.values());
    return allPeople;
  }

  /**
   * Get a person by their id
   *
   * @return Person
   */
  Person getPersonById(int id) {
    Person p;
    p = getStafferById(id);
    if (p != null) return p;

    p = getStudentById(id);
    if (p != null) return p;

    p = getProfessorById(id);
    return p;
  }

  /**
   * Get a staffer by their id
   *
   * @return Staffer
   */
  Staffer getStafferById(int id) {
    return _staffers.get(id);
  }

  /**
   * Get a representative by their id
   *
   * @return Student
   */
  Student getRepresentativeById(int id) {
    if (!isRepresentative(id)) return null;
    return _students.get(id);
  }

  /**
   * Get a student by their id
   *
   * @return Student
   */
  Student getStudentById(int id) {
    return _students.get(id);
  }

  /**
   * Get a professor by their id
   *
   * @return Professor
   */
  Professor getProfessorById(int id) {
    return _professors.get(id);
  }

  /**
   * Check whether a school has a course
   *
   * @return boolean
   */
  boolean hasCourse(String name) { return _courses.containsKey(name); }

  /**
   * Get a course by its name
   *
   * @return Course
   */
  Course getCourseByName(String name) { return _courses.get(name); }

  /**
   * Get people by a common name
   *
   * @return Collection of People
   */
  Collection<Person> getPersonByName(String name) {
    Collection<Person> result = new TreeSet<Person>(new Comparator<Person>() {
      public int compare(Person p1, Person p2) {
          Collator c = Collator.getInstance(Locale.getDefault());
          return c.compare(p1.name(), p2.name());
        }
    });

    for (Person p : people()) {
      if (p.name().contains(name))
        result.add(p);
    }

    return result;
  }

}
