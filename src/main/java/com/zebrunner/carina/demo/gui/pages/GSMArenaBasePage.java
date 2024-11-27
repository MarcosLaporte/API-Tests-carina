package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class GSMArenaBasePage extends AbstractPage {

    @FindBy(xpath = "//button[text()='Agree and proceed']")
    private ExtendedWebElement agreeAndProceedBtn;

    @FindBy(xpath = "//a[text()='%s']")
    private ExtendedWebElement linkElementByText;

    public GSMArenaBasePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies() {
        agreeAndProceedBtn.click();
    }

    public void selectLinkFromPhoneFinder(String linkText) {
        linkElementByText.format(linkText).click();
    }
}
