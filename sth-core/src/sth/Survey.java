package sth;

import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;
import java.lang.UnsupportedOperationException;

public class Survey 
  implements Serializable {

  private Map<Student, IncompleteSurveyResponse> _responses = new TreeMap<Student, IncompleteSurveyResponse>();
  private boolean _open;

  Survey(boolean isProjectOpen) {
    if (isProjectOpen) _open = false;
    else _open = true;
  }

  public void close() 
    throws UnsupportedOperationException {
    // FIXME
    
    throw new UnsupportedOperationException();
  }

  public void finish() 
    throws UnsupportedOperationException {
    // FIXME
    
    throw new UnsupportedOperationException();
  }

   
  public void open() 
    throws UnsupportedOperationException {
    // FIXME
    
    throw new UnsupportedOperationException();
  }

   
   
  public void addResponse(Student s, int hours, String comment) 
    throws UnsupportedOperationException {
    // FIXME
    
    throw new UnsupportedOperationException();
  }

  public String toString() { 
    return "<<Survey :: to implement>>"; 
  }
}
