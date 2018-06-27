package com.ef.utils;

import com.ef.model.SearchParameters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserUtils {

  private static final Pattern ARGUMENT_PATTERN
          = Pattern.compile("^[-]{0,2}(\\w+)[=](.*)$");
  private static final String DAILY = "DAILY";
  private static final String HOURLY = "HOURLY";
  public static final SimpleDateFormat DATETIME_FORMATTER =
          new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

  public static Map<String, String> argsToMap(String... args) {
    return Arrays.stream(args)
            .map(ARGUMENT_PATTERN::matcher)
            .filter(Matcher::matches)
            .collect(Collectors.toMap(m -> m.group(1), m -> m.group(2)));
  }

  public static Optional<SearchParameters> parseSearchParametersfromArgs(Map<String, String> argsMap) {

    if (!argsMap.containsKey("startDate") && !argsMap.containsKey("duration") && !argsMap.containsKey("threshold")) {
      return Optional.empty();
    }

    final Date startDate = Optional.of(argsMap.getOrDefault("startDate", ""))
            .filter(text -> !text.isEmpty())
            .flatMap((String value) -> {
              try {
                final Date parsedDate = DATETIME_FORMATTER.parse(value);
                return Optional.of(parsedDate);
              } catch (ParseException e) {
                return Optional.empty();
              }
            }).orElseThrow(() -> new RuntimeException(
                    "Argument startDate is missing or invalid. The format is yyyy-MM-dd.HH:mm:ss"
            ));

    final Integer duration = Optional.of(argsMap.getOrDefault("duration", ""))
            .filter(text -> !text.isEmpty())
            .flatMap((String value) -> {
              try {
                switch (value.toUpperCase()) {
                  case DAILY:
                    return Optional.of(Calendar.DATE);
                  case HOURLY:
                    return Optional.of(Calendar.HOUR);
                  default:
                    return Optional.empty();
                }
              } catch (IllegalArgumentException | NullPointerException ex) {
                return Optional.empty();
              }
            }).orElseThrow(() -> new RuntimeException(
                    "Argument duration is missing or invalid. Value should be 'hourly' or 'daily'"
            ));

    final Integer threshold = Optional.of(argsMap.getOrDefault("threshold", ""))
            .filter(text -> !text.isEmpty())
            .filter(text -> text.matches("\\d+"))
            .map(Integer::parseInt)
            .filter(val -> val > 0)
            .orElseThrow(() -> new RuntimeException(
                    "Argument threshold is missing or invalid. Specify a natural value no less than 0"
            ));

    return Optional.of(new SearchParameters(startDate, duration, threshold));
  }

  public static String parseFilePathfromArgs(Map<String, String> argsMap) {

    return Optional.of(argsMap.getOrDefault("accesslog", ""))
            .filter(text -> !text.isEmpty())
            .orElseThrow(() -> new RuntimeException(
                    "Argument accesslog is missing or invalid. Specify the log file path"
            ));

  }
}
