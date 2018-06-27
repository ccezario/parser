package com.ef;

import com.ef.model.SearchParameters;
import com.ef.services.LogService;
import com.ef.utils.ParserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class Parser implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Parser.class);

  @Autowired
  LogService logService;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Parser.class);

    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Override
  public void run(String... args) throws Exception {

    Map<String, String> argsMap;

    if (args.length > 0) {
      argsMap = ParserUtils.argsToMap(args);

      SearchParameters searchParameters = ParserUtils.parseSearchParametersfromArgs(argsMap).get();

      log.info("Configuring Database...");

      logService.createDatabase();

      log.info("Loading Data...");

      logService.addLogsIntoDB(logService.getLogsFromFile(ParserUtils.parseFilePathfromArgs(argsMap)));

      logService.searchParameters(searchParameters);
    }

  }
}
