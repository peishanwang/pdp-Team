package edu.neu.ccs.cs5010;

/**
 * Enum Gender-will take just 3 values-MALE, FEMALE, Other.
 */
public enum Gender {
  MALE("M"), FEMALE("F"), Other("O");

  /**
   * Gender constructor.
   * @param gender string
   */
  Gender(String gender) {
    this.genderStr = gender;
  }

  @Override
  public String toString() {
    return this.genderStr;
  }

  /**
   * method to return enum from String.
   * @param genderStr gender value string
   * @return enum associated
   */
  public static Gender fromString(String genderStr) {
    for (Gender g : Gender.values()) {
      if (g.toString().equalsIgnoreCase(genderStr)) {
        return g;
      }
    }
    return null;
  }

  private final String genderStr;
}