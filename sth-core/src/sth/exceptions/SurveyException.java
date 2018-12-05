package sth.exceptions;

abstract class SurveyException extends Exception { 
  private String _projectName;
  private String _disciplineName;
  SurveyException(String disciplineName, String projectName) { 
    _disciplineName = disciplineName; 
    _projectName = projectName; 
  }
  public String getProjectName() { return _projectName; }
  public String getDisciplineName() { return _disciplineName; }
}
