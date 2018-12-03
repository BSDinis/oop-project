package sth;

import java.util.Map;
import java.util.TreeMap;
import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyAlreadyCreatedException;
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
  }
  
  public void createSurvey() throws SurveyAlreadyCreatedException { 
    if (hasSurvey()) 
      throw new SurveyAlreadyCreatedException(_name);

    _survey = new Survey(this); 
  }

  public boolean hasSurvey() { return _survey != null; }
  public Survey getSurvey() { return _survey; }
  void remSurvey() { _survey = null; }

  public void acceptSubmission(Student student, String submission)
    throws ProjectNotOpenException {
    if (_open) 
      _submissions.put(student, submission);
    else 
      throw new ProjectNotOpenException(_name);
  }

  public String name() { return _name; }
  public boolean isOpen() { return _open; }

  public Map<Student, String> getSubmissions() { return _submissions; }
  public boolean hasSubmissionFrom(Student s) { return (_submissions.containsKey(s)); }

}
