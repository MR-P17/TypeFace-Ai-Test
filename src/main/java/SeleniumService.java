import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SeleniumService {

    public static WebDriver getWebDriver(){
        System.setProperty("webdriver.chrome.driver","/Users/prashant/Desktop/chromedriver");
        WebDriver driver =  new ChromeDriver();
        return driver;
    }

    public static Map<String, List<WebElement>> getWebElement(String url, WebDriver driver){
        Map<String, List<WebElement>> allWebElementsTagWise = new HashMap<>();
        List<String> allTagNames = Arrays.asList("a", "button", "input", "h1", "li", "ul", "header", "footer");
        driver.get(url);
        driver.manage().window().maximize();
        for(String tagName : allTagNames) {
            List<WebElement> webElements = driver.findElements(By.tagName(tagName));
            allWebElementsTagWise.put(tagName, webElements);
        }
        return allWebElementsTagWise;
    }

    public static Map<String, List<ColorCombination>> getAllColorsForTag(Map<String, List<WebElement>> allWebElementsTagWise, WebDriver driver){
        Map<String, List<ColorCombination>> allColorCombinationTagWise = new HashMap<>();
        Actions action  = new Actions(driver);

        for(Map.Entry<String, List<WebElement>> entry: allWebElementsTagWise.entrySet()){
            String tagName = entry.getKey();
            List<WebElement> webElements = entry.getValue();
            List<ColorCombination> colorCombinations = new ArrayList<>();
            for(WebElement webElement: webElements){
                String color = webElement.getCssValue("color");
                String bgColor = webElement.getCssValue("background-color");

                //Todo: Debug perform();
                //action.moveToElement(webElement).perform();
                String hoverColor = webElement.getCssValue("color");

                //action.moveToElement(webElement).perform();
                String hoverBgColor = webElement.getCssValue("background-color");

                ColorCombination colorCombination = new ColorCombination(color, bgColor, hoverColor, hoverBgColor);
                colorCombinations.add(colorCombination);
            }
            List<ColorCombination> weightedColorCombinations = getWeightedColorCombination(colorCombinations);

            allColorCombinationTagWise.put(tagName, weightedColorCombinations);
        }
        return allColorCombinationTagWise;
    }

    public static List<ColorCombination> getWeightedColorCombination(List<ColorCombination> colorCombinations){
        List<ColorCombination> weightedColorCombinations = new ArrayList<>();
        int totalColorCombinations = colorCombinations.size();
        Map<String, Integer> colorCombinationWeightMap = new HashMap<>();
        for(ColorCombination colorCombination : colorCombinations){
            String key = String.join("$", colorCombination.getColor(), colorCombination.getBgColor(), colorCombination.getHoverColor(), colorCombination.getHoverBgColor());
            if(colorCombinationWeightMap.containsKey(key)){
                int frequency = colorCombinationWeightMap.get(key);
                colorCombinationWeightMap.put(key, frequency + 1);
            }else{
                colorCombinationWeightMap.put(key, 1);
            }
        }

        for(Map.Entry<String, Integer> entry: colorCombinationWeightMap.entrySet()){
            List<String> colorCodes = Arrays.asList(entry.getKey().split("\\$"));
            double weight = ((entry.getValue() *1.0)/totalColorCombinations)*100;
            ColorCombination colorCombination = new ColorCombination(colorCodes.get(0), colorCodes.get(1), colorCodes.get(2), colorCodes.get(3),weight);
            weightedColorCombinations.add(colorCombination);
        }

        List<ColorCombination> sortedColorCombination = weightedColorCombinations.stream().sorted(Comparator.comparing(ColorCombination::getWeight)).collect(Collectors.toList());
        Collections.reverse(sortedColorCombination);
        return  sortedColorCombination;
    }

    public static void printTagWiseFetchedColors(Map<String, List<ColorCombination>> allColorCombinationTagWise){
        for(Map.Entry<String, List<ColorCombination>> entry: allColorCombinationTagWise.entrySet()){
            System.out.println("Wighted color combination for :: "+ entry.getKey());
            System.out.println("------------------------------------------------");

            for(ColorCombination colorCombination: entry.getValue()){
                System.out.println("Color : "+ colorCombination.getColor() + "|  BackGround Color : "+ colorCombination.getBgColor() + " | Hover Color : "+ colorCombination.getHoverColor() + "| Hover Bg Color : " + colorCombination.getHoverBgColor() + "| Weight : " + colorCombination.getWeight());
            }
        }
    }
}