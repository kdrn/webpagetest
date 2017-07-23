package com.test.webpagetest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GooglePageTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Set path to a FirefoxDriver
        System.setProperty("webdriver.gecko.driver", "C:\\tools\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void testCase() throws Exception {
        GooglePage google = new GooglePage(driver);

        // Open http://google.com/ncr and search for "selenium"
        google.searchGooglePage("selenium");

        //Check if 1st link contains domain "seleniumhq.org"
        String firstSearchLink = google.getFirstLinkInSearch();
        Assert.assertTrue("The actual link doesn't contain the expected", firstSearchLink.contains("seleniumhq.org"));

        // Navigate to "Images" tab
        google.openTopPanelTab("Images");

        // Check if link of the 1st image contains domain "seleniumhq.org"
        String firstImagesLink = google.getFirstImageLink();
        Assert.assertTrue("The actual link doesn't contain the expected", firstImagesLink.contains("seleniumhq.org"));

        //Navigate to "All" tab
        google.openTopPanelTab("All");

        //Check if 1st link in search results equals to previous 1st link in search results.
        String firstLinkInAllTab = google.getFirstLinkInSearch();
        Assert.assertTrue("Actual and previous links not matching", firstLinkInAllTab.equals(firstSearchLink));
    }

    @After
    public void shutdown() {
        driver.close();
    }
}
