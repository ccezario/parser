package com.ef.services;

import com.ef.model.SearchParameters;
import com.ef.model.log.DBDAO;
import com.ef.model.log.LogDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Service
public class LogService {

  private static final Logger log = LoggerFactory.getLogger(LogService.class);

  @Autowired
  LogDAO logDAO;
  @Autowired
  DBDAO dbDAO;

  public void addLogsIntoDB(List<Object[]> logs) {
    logDAO.addLogBatch(logs);
  }

  public List<Object[]> getLogsFromFile(String filePath) {
    try {

      List<Object[]> lines = new LinkedList<>();
      Files.lines(Paths.get(filePath)).forEach((line) -> {
        lines.add(line.split("\\|"));
      });

      return lines;
    } catch (IOException ex) {
      log.error(String.format("Error reading file %s", filePath));
      return null;
    }
  }

  public void searchParameters(SearchParameters searchParameters) {
    logDAO.getIpsfromArgs(searchParameters).forEach(value -> findIpsEntries(value, searchParameters));
  }

  private void findIpsEntries(String ip, SearchParameters searchParameters) {
    log.info(String.format("Find IP: %s, fetching data to log_comment table...", ip));
    logDAO.addLogComment(logDAO.getLogEntriesfromArgs(ip, searchParameters));
  }

  public void createDatabase() {
    dbDAO.execute();
  }

}
