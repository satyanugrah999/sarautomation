package id.co.juaracoding.testng;

import id.co.juaracoding.util.TextUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TextUtilTest {

    private String palindromeSample;
    private String normalTextSample;

    @BeforeClass
    public void setUpClass() {
        System.out.println("=== Memulai Eksekusi TextUtilTest ===");
    }

    @BeforeMethod
    public void setUpMethod() {
        palindromeSample = "racecar";
        normalTextSample = "JuaraCoding";
    }

    @AfterMethod
    public void tearDownMethod() {
        palindromeSample = null;
        normalTextSample = null;
    }

    @Test
    public void is_palindrome_returns_true_for_racecar() {
        boolean actualResult = TextUtil.isPalindrome(palindromeSample);
        Assert.assertTrue(actualResult, "kata 'racecar' seharusnya terdeteksi sebagai palindrome");
    }

    @Test
    public void is_palindrome_returns_false_for_non_palindrome() {
        boolean actualResult = TextUtil.isPalindrome(normalTextSample);
        Assert.assertFalse(actualResult, "kata 'JuaraCoding' seharusnya tidak terdeteksi sebagai palindrome");
    }

    @Test
    public void is_palindrome_returns_false_for_null_input() {
        boolean actualResult = TextUtil.isPalindrome(null);
        Assert.assertFalse(actualResult, "input null seharusnya mengembalikan false pada pengecekan palindrome");
    }

    @Test
    public void count_vowels_returns_correct_count_for_normal_text() {
        int actualVowelCount = TextUtil.countVowels(normalTextSample);
        Assert.assertEquals(actualVowelCount, 5, "jumlah huruf vokal pada kata 'JuaraCoding' seharusnya 5");
    }

    @Test
    public void count_vowels_returns_zero_for_null_input() {
        int actualVowelCount = TextUtil.countVowels(null);
        Assert.assertEquals(actualVowelCount, 0, "jumlah huruf vokal untuk input null seharusnya 0");
    }
}
