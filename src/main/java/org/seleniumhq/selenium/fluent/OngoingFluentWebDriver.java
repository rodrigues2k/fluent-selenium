package org.seleniumhq.selenium.fluent;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OngoingFluentWebDriver extends FluentBase {

    public OngoingFluentWebDriver(WebDriver delegate, List<WebElement> currentElements) {
        super(delegate);
        addAll(currentElements);
    }

    @Override
    protected OngoingFluentWebDriver getSubsequentFluentWebDriver() {
        return this;
    }

    protected WebElement findIt(By by) {
        WebElement result = get(0).findElement(by);
        clear();
        add(result);
        return result;
    }

    @Override
    protected List<WebElement> findThem(By by) {
        List<WebElement> results = get(0).findElements(by);
        clear();
        addAll(results);
        return results;
    }


    // All these have peer equivalents in the WebElement interface
    // ===========================================================

    // These though, don't return void as they do in WebElement

    public OngoingFluentWebDriver click() {
        for (WebElement webElement : this) {
            webElement.click();
        }
        return getSubsequentFluentWebDriver();
    }

    /**
     *  Use this instead of clear() to clear an WebElement
     */

    public OngoingFluentWebDriver clearField() {
        for (WebElement webElement : this) {
            webElement.clear();
        }
        return getSubsequentFluentWebDriver();
    }

    /**
     * Clear of Array that is the list of current WebElements
     *
     * Not to be confused with clearField() that maps to the WebElement.clear() method.
     */
    public void clear() {
        super.clear();
    }


    public OngoingFluentWebDriver submit() {
        get(0).submit();
        return getSubsequentFluentWebDriver();
    }

    // These are as they would be in the WebElement API

    public OngoingFluentWebDriver sendKeys(CharSequence... keysToSend) {
        get(0).sendKeys(keysToSend);
        return getSubsequentFluentWebDriver();
    }

    public String getTagName() {
        return get(0).getTagName();
    }

    public boolean isSelected() {
        boolean areSelected = true;
        for (WebElement webElement : this) {
            areSelected = areSelected & webElement.isSelected();
        }
        return areSelected;
    }

    public boolean isEnabled() {
        boolean areEnabled = true;
        for (WebElement webElement : this) {
            areEnabled = areEnabled & webElement.isEnabled();
        }
        return areEnabled;
    }

    public boolean isDisplayed() {
        boolean areDisplayed = true;
        for (WebElement webElement : this) {
            areDisplayed = areDisplayed & webElement.isDisplayed();
        }
        return areDisplayed;
    }

    public Point getLocation() {
        return get(0).getLocation();
    }

    public Dimension getSize() {
        return get(0).getSize();
    }

    public String getCssValue(String cssName) {
        return get(0).getCssValue(cssName);
    }

    public String  getAttribute(String attr) {
        return get(0).getAttribute(attr);
    }

    public String getText() {
        return get(0).getText();
    }


}
