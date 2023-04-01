package com.planner.utils.hash;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service("passwordHasher")
public class PasswordHasher {

  @Value("${hasher.rounds:10}")
  private int rounds;

  public String generateSalt() {
    return BCrypt.gensalt(rounds);
  }

  private static void generatePepper() {
    RandomStringGenerator randomStringGenerator =
        new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS,
                CharacterPredicates.ASCII_ALPHA_NUMERALS)
            .build();
    System.out.println(randomStringGenerator.generate(50));
  }

  public String hash(String password, String salt) {
    return BCrypt.hashpw(password, salt);
  }

  public boolean verifyHash(String plainPassword, String hashed) {
    return BCrypt.checkpw(plainPassword, hashed);
  }

}
