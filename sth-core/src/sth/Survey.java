package sth;

import java.io.Serializable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import sth.Project;

import sth.exceptions.IllegalSurveyCloseException;
import sth.exceptions.IllegalSurveyFinishException;
import sth.exceptions.IllegalSurveyOpenException;
import sth.exceptions.SurveyAlreadyCreatedException;
import sth.exceptions.FinishedSurveyException;
import sth.exceptions.SurveyNotEmptyException;
import sth.exceptions.SurveyNotFoundException;

// FIXME: can u, for fucks sake, draw the state-machine?
public class Survey 
  implements Serializable {

  private class SurveyResponse 
    implements Serializable {

    private Student _student;
    private int _hours;
    private String _comment;
    
    SurveyResponse(Student student, int hours, String comment) {
      _student = student;
      _hours = hours;
      _comment = comment;
    }

     Student student() { return _student; }
     int hours() { return _hours; }
     String comment() { return _comment; }
  }

  private Set<Integer> _answeredIds = new TreeSet<>();
  private Collection<SurveyResponse> _responses = new LinkedList<>();
  private State _state;
  private Project _parentProject;
  private int _minHours = Integer.MAX_VALUE;
  private int _maxHours = Integer.MIN_VALUE;
  private int _sumHours = 0;


  abstract class State implements Serializable {
    void setState(State new_state) { _state = new_state; }
    void open() throws IllegalSurveyOpenException { throw new IllegalSurveyOpenException(_parentProject.name()); }
    void close() throws IllegalSurveyCloseException { throw new IllegalSurveyCloseException(_parentProject.name()); }
    void finish() throws IllegalSurveyFinishException { throw new IllegalSurveyFinishException(_parentProject.name()); }
    void cancel() throws FinishedSurveyException, SurveyNotEmptyException { throw new SurveyNotEmptyException(_parentProject.name()); }
    void addResponse(Student s, int hours, String comment) throws SurveyNotFoundException 
    { 
      throw new SurveyNotFoundException(_parentProject.name()); 
    }
    abstract String print(SurveyPrinter p);
    public String disciplineName() { return _parentProject.disciplineName(); }
    public String projectName() { return _parentProject.name(); }
  }

  public class Created extends State implements Serializable {
    void open() { setState(new Open()); }
    String print(SurveyPrinter p) { return p.print(this); }
    void cancel() { _parentProject.remSurvey(); }
  }

  public class Open extends State implements Serializable {
    void open() { }
    void close() { setState(new Closed()); }
    void cancel() throws SurveyNotEmptyException {
      if (!_answeredIds.isEmpty()) 
        throw new SurveyNotEmptyException(_parentProject.name());

      _parentProject.remSurvey();
    }
    void addResponse(Student s, int hours, String comment)
    { 
      if (_parentProject.hasSubmissionFrom(s) && !_answeredIds.contains(s.id())) {
        _minHours = (hours < _minHours) ? hours : _minHours;
        _maxHours = (hours > _maxHours) ? hours : _minHours;
        _sumHours += hours;
        _answeredIds.add(s.id());
        _responses.add(new SurveyResponse(s, hours, comment));
      }
    }
    String print(SurveyPrinter p) { return p.print(this); }
  }

  public class Closed extends State implements Serializable {
    void open() { setState(new Open()); }
    void cancel() { open(); }
    void close() {}
    void finish() { setState(new Finished()); }
    String print(SurveyPrinter p) { return p.print(this); }
  }

  public class Finished extends State implements Serializable {
    void finish() {}
    void cancel() throws FinishedSurveyException { throw new FinishedSurveyException(_parentProject.name());}
    //void open() throws FinishedSurveyException { throw new FinishedSurveyException(_parentProject.name());}
    //void close() throws FinishedSurveyException { throw new FinishedSurveyException(_parentProject.name());} not sure
    String print(SurveyPrinter p) { return p.print(this); }
    public int minHours() { return _minHours; }
    public int maxHours() { return _maxHours; }
    public int medHours() { 
      if (responsesNumber() == 0) 
        return 0; // this will never happen
      return _sumHours / responsesNumber(); 
    }
    public int submissionNumber() { return _parentProject.submissionNumber(); }
    public int responsesNumber() { return _responses.size(); }
  }

  Survey(Project parentProject) {
    _parentProject = parentProject;
    if (_parentProject.isOpen())
      _state = new Created();
    else
      _state = new Open();
  }

  void close() throws IllegalSurveyCloseException {
    _state.close();
  }

  void finish() throws IllegalSurveyFinishException {
    _state.finish();
  }

  void open() throws IllegalSurveyOpenException {
    _state.open();
  }

  void cancel() throws SurveyNotEmptyException, FinishedSurveyException {
    _state.cancel();
  }


  void addResponse(Student s, int hours, String comment) 
    throws SurveyNotFoundException {
    _state.addResponse(s, hours, comment);
  }

  public String print(SurveyPrinter p) {
    return _state.print(p);
  }

}
