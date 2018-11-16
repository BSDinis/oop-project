package sth;

import java.util.Map;
import java.util.TreeMap;
import sth.exceptions.ProjectNotOpenException;
import java.io.Serializable;

public class Project implements Serializable {
  private String _name;
  private String _description;
  private Map<Student, String> _submissions = new TreeMap<Student, String>();
  private Survey _survey = null;
  private boolean _open = true;

  Project(String name, String description) {
    _name = name; 
    _description = description;
  }

  Project(String name) { this(name, "empty description"); }

  public void close() { 
    _open = false; 
    if (hasSurvey()) _survey.open();
  }
  
  public void createSurvey() { 
      _survey = new Survey(_open); 
    }
  public boolean hasSurvey() { return _survey != null; }
  public Survey getSurvey() { return _survey; }

  public void acceptSubmission(Student student, String submission)
    throws ProjectNotOpenException {
    if (_open) 
      _submissions.put(student, submission);
    else 
      throw new ProjectNotOpenException(_name);
  }

  public Map<Student, String> getSubmissions() {
    return _submissions;
  }
}
