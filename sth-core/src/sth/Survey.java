package sth;

import java.util.Map;
import java.util.TreeMap;
import java.lang.UnsupportedOperationException;

public class Survey {
  private Map<Student, IncompleteSurveyResponse> _responses = new TreeMap<Student, IncompleteSurveyResponse>();

  Survey(boolean isProjectOpen) {
    if (isProjectOpen) open = false;
    else open = true;
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

  public String toString() { return "<<Survey :: to implement>>"; }
}
