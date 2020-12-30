package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class NotePage {
    @FindBy(id="nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id="add-note")
    private WebElement addButton;

    @FindBy(id="edit-note")
    private List<WebElement> editButton;

    @FindBy(id="delete-note")
    private List<WebElement> deleteButton;

    @FindBy(id="noteTitle")
    private List<WebElement> titleList;

    @FindBy(id="noteDescription")
    private List<WebElement> descriptionList;

    @FindBy(id="note-title")
    private WebElement inputTitle;

    @FindBy(id="note-description")
    private WebElement inputDescription;

    @FindBy(id="noteSubmit")
    private WebElement submitButton;

    @FindBy(id="note-modal-submit")
    private WebElement submitModalButton;

    private final WebDriver driver;

    public NotePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public int getNotesSize() {
        return this.titleList.size();
    }

    public List<String> getDetail(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navNoteTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        List<String> detail = new ArrayList<>(List.of(titleList.get(0).getText(),
                descriptionList.get(0).getText()));
        return detail;

    }

    public void addNote(WebDriver driver, String title, String description, WebElement nav){
        WebDriverWait wait = new WebDriverWait(driver, 60);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navNoteTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addButton);

        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + title + "';", inputTitle);
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + description + "';", inputDescription);

        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", submitModalButton);
    }

    public void editNote(WebDriver driver, String title, String description){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navNoteTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navNoteTab);

        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0))));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editButton.get(0));

        wait.until(ExpectedConditions.elementToBeClickable(inputTitle));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + title + "';", inputTitle);

        wait.until(ExpectedConditions.elementToBeClickable(inputDescription));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + description + "';", inputDescription);

        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", this.submitModalButton);
    }

    public void deleteNote(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navNoteTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navNoteTab);

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", deleteButton.get(0));
    }

}
