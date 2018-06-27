package com.ef.model;

import java.util.Calendar;
import java.util.Date;

public class SearchParameters {

  private final Date startDate;

  private final Date endDate;

  private final Integer threshold;

  public SearchParameters(Date startDate, Integer duration, Integer threshold) {
    this.startDate = startDate;
    this.endDate = setEndDate(duration);
    this.threshold = threshold;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Integer getThreshold() {
    return threshold;
  }

  private Date setEndDate(Integer duration) {
    Calendar cal = Calendar.getInstance();

    cal.setTime(getStartDate());
    cal.add(duration, 1);

    return cal.getTime();
  }

  public Date getEndDate() {
    return endDate;
  }

  public Object[] toObjectArray() {
    Object[] object = new Object[3];
    object[0] = getStartDate();
    object[1] = getEndDate();
    object[2] = getThreshold();

    return object;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SearchParameters that = (SearchParameters) o;

    if (!startDate.equals(that.startDate)) return false;
    if (!endDate.equals(that.endDate)) return false;
    return threshold.equals(that.threshold);
  }

  @Override
  public int hashCode() {
    int result = startDate.hashCode();
    result = 31 * result + endDate.hashCode();
    result = 31 * result + threshold.hashCode();
    return result;
  }
}
