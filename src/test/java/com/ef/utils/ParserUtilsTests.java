package com.ef.utils;

import com.ef.model.SearchParameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

@RunWith(SpringRunner.class)
public class ParserUtilsTests {

  @Test
  public void argsToMap_accesslog() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100");
    Assert.assertEquals(map.containsKey("accesslog"), true);
  }

  @Test
  public void argsToMap_startDate() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100");
    Assert.assertEquals(map.containsKey("accesslog"), true);
  }

  @Test
  public void argsToMap_duration() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100");
    Assert.assertEquals(map.containsKey("accesslog"), true);
  }

  @Test
  public void argsToMap_threshold() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100");
    Assert.assertEquals(map.containsKey("accesslog"), true);
  }

  @Test
  public void argsToMap_notFound() {
    Map<String, String> map =
            ParserUtils.argsToMap("");
    Assert.assertEquals(map.containsKey("accesslog"), false);
  }

  @Test(expected = RuntimeException.class)
  public void parseFilePathfromArgs_empty() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=");
    ParserUtils.parseFilePathfromArgs(map);
  }

  @Test
  public void parseFilePathfromArgs_filePath() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file");
    Assert.assertEquals(ParserUtils.parseFilePathfromArgs(map), "/path/to/file");
  }

  @Test
  public void parseFilePathfromArgs_notEmpty() {
    Map<String, String> map =
            ParserUtils.argsToMap("--accesslog=/path/to/file");
    Assert.assertEquals(ParserUtils.parseFilePathfromArgs(map).isEmpty(), false);
  }

  @Test
  public void parseSearchParametersfromArgs() throws ParseException {
    Map<String, String> map =
            ParserUtils.argsToMap("--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100");
    Assert.assertEquals(ParserUtils.parseSearchParametersfromArgs(map).get().equals(
            new SearchParameters(ParserUtils.DATETIME_FORMATTER.parse("2017-01-01.13:00:00"), Calendar.HOUR, 100)),
            true);
  }

  @Test
  public void parseSearchParametersfromArgs_empty() {
    Map<String, String> map = new HashMap<>();
    Assert.assertEquals(ParserUtils.parseSearchParametersfromArgs(map), Optional.empty());
  }

  @Test(expected = RuntimeException.class)
  public void parseSearchParametersfromArgs_withoutStartDate() {
    Map<String, String> map =
            ParserUtils.argsToMap("--duration=hourly", "--threshold=100");
    ParserUtils.parseSearchParametersfromArgs(map);
  }

  @Test(expected = RuntimeException.class)
  public void parseSearchParametersfromArgs_withoutDuration() {
    Map<String, String> map =
            ParserUtils.argsToMap("--startDate=2017-01-01.13:00:00", "--threshold=100");
    ParserUtils.parseSearchParametersfromArgs(map);
  }

  @Test(expected = RuntimeException.class)
  public void parseSearchParametersfromArgs_withoutThreshold() {
    Map<String, String> map =
            ParserUtils.argsToMap("--startDate=2017-01-01.13:00:00", "--duration=hourly");
    ParserUtils.parseSearchParametersfromArgs(map);
  }

  @Test(expected = RuntimeException.class)
  public void parseSearchParametersfromArgs_invalidDate() {
    Map<String, String> map =
            ParserUtils.argsToMap("--startDate=2099-9.99:0:0", "--duration=hourly", "--threshold=100");
    ParserUtils.parseSearchParametersfromArgs(map);
  }
}
