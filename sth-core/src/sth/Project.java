package sth;

import java.util.Map;
import java.util.TreeMap;
import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyNotFoundException;
import sth.exceptions.SurveyAlreadyCreatedException;
import java.io.Serializable;

public class Project implements Serializable {
  private String _name;
  private String _disciplineName;
  private String _description;
  private Map<Student, String> _submissions = new TreeMap<Student, String>();
  private Survey _survey = null;
  private boolean _open = true;

  Project(String name, String disciplineName, String description) {
    _name = name; 
    _disciplineName = disciplineName; 
    _description = description;
  }

  Project(String name, String disciplineName) { this(name, disciplineName, "empty description"); }

  public void close() {
    _open = false; 
    if (hasSurvey()) {
      try {
        _survey.open();
      } catch (IllegalSurveyOpenException e) {
        // ignore - this will never happen FIXME: confirm
      }
    }
  }
  
  public void createSurvey() throws SurveyAlreadyCreatedException { 
    if (hasSurvey()) 
      throw new SurveyAlreadyCreatedException(_name);

    _survey = new Survey(this); 
  }

  public boolean hasSurvey() { return _survey != null; }
  public Survey getSurvey()
    throws SurveyNotFoundException
  { 
    if (!hasSurvey()) throw new SurveyNotFoundException(_name);
    return _survey; 
  }
  void remSurvey() { _survey = null; }

  public void acceptSubmission(Student student, String submission)
    throws ProjectNotOpenException {
    if (_open) 
      _submissions.put(student, submission);
    else 
      throw new ProjectNotOpenException(_name);
  }

  public String name() { return _name; }
  public String disciplineName() { return _disciplineName; }
  public boolean isOpen() { return _open; }

  public Map<Student, String> getSubmissions() { return _submissions; }
  public boolean hasSubmissionFrom(Student s) { return (_submissions.containsKey(s)); }
  public int submissionNumber() { return _submissions.size(); }
}
