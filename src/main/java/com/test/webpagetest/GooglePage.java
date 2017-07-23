package com.test.webpagetest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GooglePage {
    // CSS and XPath locations of elements
    private static final String LOC_XPATH_FIRST_LINK_IN_SEARCH = ".//*[@id='rso']/div/div/div[1]/div/div/h3/a";
    private static final String LOC_CSS_TOP_PANEL_MENU = "a.q.qs";
    private static final String LOC_XPATH_FIRST_LINK_IN_IMAGES = ".//*[@id='rg_s']/div[1]/a";

    private final WebDriver driver;

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchGooglePage(String searchQuery) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            throw new IllegalArgumentException("Bad searchQuery");
        }

        driver.get("http://www.google.com/ncr");
        WebElement elem = driver.findElement(By.name("q"));
        elem.sendKeys(searchQuery + "\n");
        elem.submit();

        // Wait until the google page shows the result
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
    }

    // Get 1st link in search results
    public String getFirstLinkInSearch() {
        WebElement elem = driver.findElement(By.xpath(LOC_XPATH_FIRST_LINK_IN_SEARCH));
        String firstSearchLink = elem.getAttribute("href");
        if (firstSearchLink == null) {
            throw new NoSuchElementException("link in " + LOC_XPATH_FIRST_LINK_IN_SEARCH + " not found");
        }
        return firstSearchLink;
    }

    // Get all elements from the Top Panel and choose the required ("tabName")
    public void openTopPanelTab(String tabName) {
        if (tabName == null || tabName.isEmpty()) {
            throw new IllegalArgumentException("Bad tabName");
        }
        List<WebElement> topPanel = driver.findElements(By.cssSelector(LOC_CSS_TOP_PANEL_MENU));
        WebElement clickElem = null;
        for (WebElement elem : topPanel) {
            if (tabName.equals(elem.getText())) {
                clickElem = elem;
                break;
            }
        }
        if (clickElem == null) {
            throw new NoSuchElementException(tabName + " tab not found on page");
        }

        clickElem.click();
    }

    // Get 1st link in "Images"
    public String getFirstImageLink() {
        WebElement elem = driver.findElement(By.xpath(LOC_XPATH_FIRST_LINK_IN_IMAGES));
        String firstImageLink = elem.getAttribute("href");
        if (firstImageLink == null) {
            throw new NoSuchElementException("link in " + LOC_XPATH_FIRST_LINK_IN_IMAGES + " not found");
        }
        return firstImageLink;
    }
}
