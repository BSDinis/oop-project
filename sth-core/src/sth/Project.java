package sth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import sth.exceptions.ProjectNotOpenException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyNotFoundException;
import sth.exceptions.SurveyAlreadyCreatedException;
import java.io.Serializable;

public class Project implements Serializable {
  private String _name;
  private Discipline _discipline;
  private String _description;
  private Map<Integer, String> _submissions = new TreeMap<>();
  private Survey _survey = null;
  private boolean _open = true;

  Project(String name, String description, Discipline discipline) {
    _name = name; 
    _description = description;
    _discipline = discipline;
  }

  Project(String name, Discipline discipline) {
    this(name, "empty description", discipline); 
  }

  void close() {
    _open = false; 
    if (hasSurvey()) {
      try {
        _survey.open();
      } catch (IllegalSurveyOpenException e) {
        e.printStackTrace();
      }
    }
  }
  
  void createSurvey() throws SurveyAlreadyCreatedException { 
    if (hasSurvey()) 
      throw new SurveyAlreadyCreatedException(disciplineName(), _name);

    _survey = new Survey(this); 
  }

  boolean hasSurvey() { return _survey != null; }

  Survey survey()
    throws SurveyNotFoundException
  { 
    if (!hasSurvey()) throw new SurveyNotFoundException(disciplineName(), _name);
    return _survey; 
  }

  void remSurvey() { _survey = null; }

  void acceptSubmission(Student student, String submission)
    throws ProjectNotOpenException {
    if (_open) 
      _submissions.put(student.id(), submission);
    else 
      throw new ProjectNotOpenException(disciplineName(), _name);
  }

  String name() { return _name; }

  String disciplineName() { return _discipline.name(); }

  public boolean isOpen() { return _open; }

  public Collection<String> getSubmissions() { 
    Collection<String> sub = new LinkedList<>();
    for (Map.Entry<Integer, String> entry : _submissions.entrySet())
      sub.add(entry.getKey() + "|" + entry.getValue());
    return sub; 
  }

  public boolean hasSubmissionFrom(Student s) { return (_submissions.containsKey(s.id())); }

  public int submissionNumber() { return _submissions.size(); }
}
