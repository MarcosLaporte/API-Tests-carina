package com.zebrunner.carina.demo.gui.pages.desktop;

import com.zebrunner.carina.demo.gui.pages.GSMArenaBasePage;
import org.openqa.selenium.WebDriver;

public class ProductPage extends GSMArenaBasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }


    public void open() {
        getDriver().navigate().to("https://www.gsmarena.com/cat-phones-89.php");
    }
}
