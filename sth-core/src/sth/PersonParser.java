package sth;

import java.io.IOException;
import java.io.BufferedReader;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

import sth.exceptions.BadEntryException;
import sth.exceptions.TooManyRepresentativesException;
import sth.exceptions.ProfessorAlreadyTeachingException;
import sth.exceptions.StudentAlreadyEnrolledException;
import sth.exceptions.AlienStudentException;
import sth.exceptions.DisciplineLimitReachedException;
import sth.exceptions.EnrollmentLimitReachedException;
import sth.exceptions.DuplicatePersonException;
import sth.exceptions.DuplicateCourseException;

class PersonParser {
  private LinkedList<List<String>> _lines = new LinkedList<List<String>>();
  BufferedReader _in;
  String _firstLine;
  School _school;

  PersonParser(School s, BufferedReader in) 
    throws IOException {
    _school = s;
    _in = in; 
    _firstLine = _in.readLine(); 
  }

  public boolean parsePerson() 
    throws IOException, BadEntryException{

    if (_firstLine == null) return false;

    _firstLine = acceptPersonParams(_firstLine);
    generatePerson();
    return true;
  }

  private void clear() { _lines = new LinkedList<List<String>>(); }

  private void generatePerson() 
    throws BadEntryException {
    List<String> header = _lines.getFirst();
    int id;

    try {
      id = Integer.parseUnsignedInt(header.get(1));
    } catch (NumberFormatException e) {
      throw new BadEntryException(reconstruct(header));
    }

    if (_school.lookupId(id)) 
      throw new BadEntryException(reconstruct(header));

    String phoneNumber = header.get(2);
    String name = header.get(3);

    if (isRepresentativeDescriptor(header.get(0)) || isStudentDescriptor(header.get(0))) {
      generateStudent(header.get(0), _lines.subList(1, _lines.size()), id, phoneNumber, name);
    }
    else if (isProfessorDescriptor(header.get(0))) {
      generateProfessor(header.get(0), _lines.subList(1, _lines.size()), id, phoneNumber, name);
    }
    else if (isStafferDescriptor(header.get(0))) {
      generateStaffer(header.get(0), _lines.subList(1, _lines.size()), id, phoneNumber, name);
    }
  }

  private void generateStaffer(String descriptor, List<List<String>> lines,
      int id, String phoneNumber, String name) 
    throws BadEntryException {

    if (lines.size() != 0) 
      throw new BadEntryException(reconstruct(descriptor, id, phoneNumber, name));
    
    try {
      _school.addStaffer(new Staffer(name, phoneNumber, id, _school));
    } catch (DuplicatePersonException e) {
      throw new BadEntryException(reconstruct(descriptor, id, phoneNumber, name));
    }
  }


  private Course generateCourseIfNeeded(String courseName) {
    try {
      return _school.addCourse(new Course(courseName, 7)); // magic number: max number of representatives
    }
    catch (DuplicateCourseException e) {
      return _school.getCourseByName(courseName);
    }
  }


  private Discipline generateDisciplineIfNeeded(Course c, String disciplineName) {
    if (c.hasDiscipline(disciplineName)) {
      return c.getDiscipline(disciplineName);
    }
    // magic number: the Ultimate Question of Life, The Universe and Everything is: what is the maximum number (in hex, obviously) of students in a hypothetical class of the OOP project; those philosophers would be happy
    return c.addDiscipline(new Discipline(disciplineName, 0x42, c));
  }

  private void generateProfessor(String descriptor, List<List<String>> lines,
      int id, String phoneNumber, String name) 
    throws BadEntryException {


    Professor p;
    try {
      p = _school.addProfessor(new Professor(name, phoneNumber, id, _school));
    } catch (DuplicatePersonException e) {
      throw new BadEntryException(reconstruct(descriptor, id, phoneNumber, name));
    }

    for (List<String> fields : lines) {
      String courseName = fields.get(0).substring(2);
      String disciplineName = fields.get(1);

      Course c = generateCourseIfNeeded(courseName);
      Discipline d = generateDisciplineIfNeeded(c, disciplineName);

      try {
        d.addProfessor(p);
      }
      catch (ProfessorAlreadyTeachingException e) {
        throw new BadEntryException(reconstruct(fields));
      }
    }
  }

  private void generateStudent(String descriptor, List<List<String>> lines,
      int id, String phoneNumber, String name) 
    throws BadEntryException {

    if (lines.size() > 6) 
      throw new BadEntryException(reconstruct(lines.get(8)));

    Student s;
    try {
      s = _school.addStudent(new Student(name, phoneNumber, id, _school));
    } catch (DuplicatePersonException e) {
      throw new BadEntryException(reconstruct(descriptor, id, phoneNumber, name));
    }

    for (List<String> fields : lines) {
      String courseName = fields.get(0).substring(2);
      String disciplineName = fields.get(1);

      Course c = generateCourseIfNeeded(courseName);

      if (s.getCourse() == null) {
        s.enrollInCourse(c);

        if (isRepresentativeDescriptor(descriptor)) {
          try {
            c.electRepresentative(s);
          }
          catch (TooManyRepresentativesException e) {
            throw new BadEntryException(reconstruct(fields)); 
          }
        }
      }
      else if (!courseName.equals(s.getCourse().name())) {
        throw new BadEntryException(reconstruct(fields));
      }

      Discipline d = generateDisciplineIfNeeded(c, disciplineName);
      
      try {
        d.enrollStudent(s);
      }
      catch (StudentAlreadyEnrolledException 
          | AlienStudentException 
          | EnrollmentLimitReachedException 
          | DisciplineLimitReachedException e) {
        throw new BadEntryException(reconstruct(fields));
      }
    }
  }

  // for throwing meaningful exceptions
  private String reconstruct(List<String> fields) {
    if (fields.size() == 0) return "";

    String result = fields.get(0);
    for (int i = 1; i < fields.size(); i++) {
      result += "|" + fields.get(i);
    }

    return result;
  }

  // special case
  private String reconstruct(String descriptor, int id, String phoneNumber, String name) {
    return reconstruct(Arrays.asList(descriptor, String.valueOf(id), phoneNumber, name));
  }

  /**
   * @param  String : first line
   * @throws BadEntryException
   * @return String : last line read
   */
  private String acceptPersonParams(String line)
      throws BadEntryException, IOException {
    clear();
    
    if (!acceptLine(line)) {
      // this was not a first line
      throw new BadEntryException(line);
    }

    do {
      line = _in.readLine();
    } while (line != null && acceptLine(line));

    return line;
  }

  private boolean acceptLine(String line) throws BadEntryException {
    List<String> fields = new ArrayList<String>(Arrays.asList(line.split("\\|")));

    if (_lines.size() == 0) {
      // test for beggining of the person
      if (goodHeaderLine(fields))
        _lines.add(fields);            
      else
        throw new BadEntryException(line);
    }
    else {
      if (goodBodyLine(fields))
        _lines.add(fields);
      else
        return false;
    }

    return true;
  }

  // checks if the fields are good for a person header line
  private boolean goodHeaderLine(List<String> fields) {
    if (fields.size() != 4) return false;

    if (!fields.get(0).equals("FUNCIONÁRIO") && !fields.get(0).equals("DELEGADO")
        && !fields.get(0).equals("ALUNO") && !fields.get(0).equals("DOCENTE")) {
      return false;
    }

    if (!fields.get(1).matches("[0-9]+"))
      return false;

    return true;
  }

  // checks if the fields are good for a person header line
  private boolean goodBodyLine(List<String> fields) {
    if (fields.size() != 2) return false;

    if (!fields.get(0).matches("^# .+"))
      return false;

    return true;
  }

  private boolean isRepresentativeDescriptor(String descriptor) {
    return descriptor.equals("DELEGADO");
  }
  private boolean isProfessorDescriptor(String descriptor) {
    return descriptor.equals("DOCENTE");
  }
  private boolean isStudentDescriptor(String descriptor) {
    return descriptor.equals("ALUNO");
  }
  private boolean isStafferDescriptor(String descriptor) {
    return descriptor.equals("FUNCIONÁRIO");
  }
}
