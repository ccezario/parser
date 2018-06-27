package com.ef.utils;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public class FileUtils {

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
