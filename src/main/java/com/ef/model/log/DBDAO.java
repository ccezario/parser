package com.ef.model.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class DBDAO {

  private final String CREATE_LOG = "CREATE TABLE IF NOT EXISTS log (`id` SERIAL UNIQUE NOT NULL PRIMARY KEY, `ip` VARCHAR(15) NULL, `date` TIMESTAMP NOT NULL, `operation` VARCHAR(50) NULL, `status` INTEGER NULL, `agent` VARCHAR(500) NULL)";
  private final String CREATE_LOG_COMMENT = "CREATE TABLE IF NOT EXISTS log_comment (`id` SERIAL UNIQUE NOT NULL PRIMARY KEY, `ip` VARCHAR(15) NULL, `date` TIMESTAMP NOT NULL, `comment` VARCHAR(200) NULL)";
  private final String DELETE_LOG = "DELETE FROM log";
  private final String DELETE_LOG_COMMENT = "DELETE FROM log_comment";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void execute() {
    jdbcTemplate.execute(CREATE_LOG);
    jdbcTemplate.execute(CREATE_LOG_COMMENT);
    jdbcTemplate.execute(DELETE_LOG);
    jdbcTemplate.execute(DELETE_LOG_COMMENT);
  }
}
