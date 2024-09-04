package ru.caloricity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;


@SpringBootTest
class CaloricityApplicationTests {
    ApplicationModules modules = ApplicationModules.of(CaloricityApplication.class);

    @Test
    void shouldBeCompliant() {
        modules.verify();
    }

    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void test() {
        System.out.print(characterReplacement("AABABBA", 1));
    }

    public int characterReplacement(String s, int k) {
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 1;
        int longest = 1;
        int localK = k;

        while (left < chars.length - 1) {
            if (right == chars.length) {
                int leftK = localK > 0 ? (localK < left + 1 ? localK : left + 1 - 1) : 0;
                var ii = Math.max(right - left + leftK, longest);
                return ii;
            }

            if (chars[left] == chars[right]) {
                right++;
            } else {
                if (localK > 0) {
                    localK--;
                    right++;
                } else {
                    longest = Math.max(right - left, longest);
                    left++;
                    right = left + 1;
                    localK = k;
                }
            }
        }

        return longest;
    }

}
