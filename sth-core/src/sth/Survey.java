package sth;

import java.util.Map;
import java.util.TreeMap;

public class Survey {
  private boolean _finished = false;
  private boolean _open;
  private Map<Student, IncompleteSurveyResponse> _responses = new TreeMap<Student, IncompleteSurveyResponse>();

  Survey(boolean isProjectOpen) {
    if (isProjectOpen) open = false;
    else open = true;
  }

  public String toString() { return "<<Survey :: to implement>>"; }
}
