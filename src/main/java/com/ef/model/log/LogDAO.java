package com.ef.model.log;

import com.ef.model.SearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
@Repository
public class LogDAO {

  private final String SELECT_IPS = "SELECT ip FROM log WHERE `date` BETWEEN ? AND ? GROUP BY ip HAVING COUNT(ip) > ?";
  private final String SELECT_LOGS = "SELECT ip, `date`, status FROM log WHERE `date` BETWEEN ? AND ? AND ip = ?";
  private final String INSERT_LOG = "INSERT INTO log(`date`, ip, operation, status, agent) VALUES (?,?,?,?,?)";
  private final String INSERT_LOG_COMMENT = "INSERT INTO log_comment(`date`, ip, comment) VALUES (?,?,?)";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<String> getIpsfromArgs(SearchParameters searchParameters) {
    RowMapper<String> rowMapper = new RowMapper<String>() {
      @Nullable
      @Override
      public String mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString("ip");
      }
    };
    return this.jdbcTemplate.query(SELECT_IPS, searchParameters.toObjectArray(), rowMapper);
  }

  public List<Log> getLogEntriesfromArgs(String ip, SearchParameters searchParameters) {
    return this.jdbcTemplate.query(SELECT_LOGS, new PreparedStatementSetter() {

      public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setObject(1, searchParameters.getStartDate());
        preparedStatement.setObject(2, searchParameters.getEndDate());
        preparedStatement.setObject(3, ip);
      }
    }, new RowMapper<Log>() {
      @Override
      public Log mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Log(resultSet);
      }
    });
  }

  public void addLogBatch(List<Object[]> logs) {
    jdbcTemplate.batchUpdate(INSERT_LOG, logs);
  }

  public void addLogComment(List<Log> logsList) {
    jdbcTemplate.batchUpdate(INSERT_LOG_COMMENT, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Log log = logsList.get(i);
        ps.setObject(1, log.getDate());
        ps.setString(2, log.getIp());
        ps.setString(3, log.getStatus());
      }

      @Override
      public int getBatchSize() {
        return logsList.size();
      }
    });
  }

}
