package sth;

public interface SurveyPrinter {
  public String print(Survey.Open s);
  public String print(Survey.Created s);
  public String print(Survey.Closed s);
  public String print(Survey.Finished s);
}
