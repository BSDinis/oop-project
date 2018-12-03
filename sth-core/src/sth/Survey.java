package sth;

import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;
import sth.Project;

import sth.exceptions.IllegalSurveyCloseException;
import sth.exceptions.IllegalSurveyFinishException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyAlreadyCreatedException;
import sth.exceptions.SurveyFinishedException;
import sth.exceptions.SurveyNotEmptyException;
import sth.exceptions.SurveyNotFoundException;

public class Survey 
  implements Serializable {

  private Map<Student, IncompleteSurveyResponse> _responses = new TreeMap<Student, IncompleteSurveyResponse>();
  private State _state;
  private Project _parentProject;


  abstract class State {
    void setState(State new_state) { _state = new_state; }
    void open() throws IllegalSurveyOpenException { throw new IllegalSurveyOpenException(_parentProject.name()); }
    void close() throws IllegalSurveyCloseException { throw new IllegalSurveyCloseException(_parentProject.name()); }
    void finish() throws IllegalSurveyFinishException { throw new IllegalSurveyFinishException(_parentProject.name()); }
    void cancel() throws SurveyNotEmptyException { throw new SurveyNotEmptyException(_parentProject.name()); }
    void addResponse(Student s, int hours, String comment) throws SurveyNotFoundException 
    { 
      throw new SurveyNotFoundException(_parentProject.name()); 
    }
  }

  class Open extends State {
    void open() { }
    void close() { setState(new Closed()); }
    void cancel() throws SurveyNotEmptyException {
      if (_responses.size() > 0) 
        throw new SurveyNotEmptyException(_parentProject.name());

      _parentProject.remSurvey();
    }
    void addResponse(Student s, int hours, String comment)
    { 
      if (_parentProject.hasSubmissionFrom(s))
        _responses.put(s, new IncompleteSurveyResponse(hours, comment));
    }
  }

  class Closed extends State {
    void open() { setState(new Open()); }
    void cancel() { open(); }
    void close() {}
    void finish() { setState(new Finished()); }
  }

  class Finished extends State {
    void finish() {}
  }

  Survey(Project parentProject) {
    _parentProject = parentProject;
    if (_parentProject.isOpen())
      _state = new Closed();
    else
      _state = new Open();
  }

  public void close() throws IllegalSurveyCloseException {
    _state.close();
  }

  public void finish() throws IllegalSurveyFinishException {
    _state.finish();
  }

  public void open() throws IllegalSurveyOpenException {
    _state.open();
  }

  public void cancel() throws SurveyNotEmptyException {
    _state.cancel();
  }


  public void addResponse(Student s, int hours, String comment) 
    throws SurveyNotFoundException {
    _state.addResponse(s, hours, comment);
  }

  public String toString() { 
    return "<<Survey :: to implement>>"; 
  }
}
