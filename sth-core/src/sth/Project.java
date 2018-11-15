package sth;

import java.util.Map;
import java.util.TreeMap;
import sth.exception.ProjectNotOpenException;

public class Project {
  private String _name;
  private String _description;
  private Map<Student, String> _submissions = new TreeMap<Student, String>();
  private Survey _survey = null;
  private boolean _open = true;

  public Project(String name, String description) {
    _name = name; 
    _description = description;
  }

  public Project(String name) { this(name, "empty description"); }

  public void close() { _open = false; }
  
  public void createSurvey() 
    throw ProjectNotOpenException { 
      if (_open) 
        _survey = new Survey(); 
      else 
        throw new ProjectNotOpenException(_name);
    }
  public boolean hasSurvey() { return _survey != null; }


  public void acceptSubmission(Student student, String submission)
    throws ProjectNotOpenException {
    if (_open) 
      _submissions.put(student, submission);
    else 
      throw new ProjectNotOpenException(_name);
  }

  public String toString() { return "<<Project :: to implement>>"; }
}
