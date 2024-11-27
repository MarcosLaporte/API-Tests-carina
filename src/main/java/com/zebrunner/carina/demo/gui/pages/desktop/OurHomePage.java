package com.zebrunner.carina.demo.gui.pages.desktop;

import com.zebrunner.carina.demo.gui.pages.GSMArenaBasePage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OurHomePage extends GSMArenaBasePage {

    @FindBy(css = "#topsearch>input")
    private ExtendedWebElement searchInput;

    public OurHomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        this.getDriver().navigate().to("https://www.gsmarena.com");
    }



    public void search(String input) {
        searchInput.type(input);
        searchInput.sendKeys(Keys.ENTER);
    }


}
