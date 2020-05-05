package pl.test_tool;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Random;

public class RandomTool {
  public static String getRandomString () {
    return RandomTool.getRandomString(10);
  }

  public static String getRandomString (int count) {
    return RandomStringUtils.randomAlphabetic(count);
  }

  public static BigDecimal getNumber () {
    return BigDecimal.valueOf(new Random().nextLong()).abs();
  }

  public static Long id () {
    return Math.abs(new Random().nextLong());
  }

  public static Integer getNumberInteger () {
    return Math.abs(new Random().nextInt());
  }

}
