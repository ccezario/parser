package com.ef.model.log;

import com.ef.utils.HttpStatusCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Log {
  private String ip;
  private Date date;
  private String status;

  public Log() {
  }

  public Log(ResultSet resultSet) throws SQLException{
    this.ip = resultSet.getString("ip");
    this.date = resultSet.getTimestamp("date");
    this.status = HttpStatusCode.find(resultSet.getInt("status")).getDesc();
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
