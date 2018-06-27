package com.ef.utils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class FileUtils {

  /**
   * If the parseLogFile is specified then it returns a valid path of
   * the argument. If the path of the log file is wrong a {@link RuntimeException}
   * is thrown
   *
   * @param argsMap application arguments
   * @return An {@link Optional} with the {@link File} instance to the log file
   */
  public static Optional<File> getLogFile(Map<String, String> argsMap) {

    if (!argsMap.containsKey("logfile")) {
      return Optional.empty();
    }

    return Optional.of(argsMap.get("logfile"))
            .map(File::new)
            .filter(File::canRead)
            .map(Optional::of)
            .orElseThrow(() -> new RuntimeException("The specified log file cannot be read"));

  }

}
