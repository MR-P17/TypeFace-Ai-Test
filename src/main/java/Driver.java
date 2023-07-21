import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;
import java.util.Map;

public class Driver {
    public static void main(String[] args) {
        WebDriver driver = SeleniumService.getWebDriver();
        String url = "https://www.healthline.com/mental-health";
        Map<String, List<WebElement>> allWebElementsTagWise = SeleniumService.getWebElement(url, driver);

        Map<String, List<ColorCombination>> allColorCombinationTagWise = SeleniumService.getAllColorsForTag(allWebElementsTagWise, driver);
        driver.quit();

        SeleniumService.printTagWiseFetchedColors(allColorCombinationTagWise);

        System.out.println("Completed!!");
    }


}
