package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialTab;

    @FindBy(id="add-credential")
    private WebElement addButton;

    @FindBy(id="edit-credential")
    private List<WebElement> editButton;

    @FindBy(id="delete-credential")
    private List<WebElement> deleteButton;

    @FindBy(id="credentialUrl")
    private List<WebElement> urlList;

    @FindBy(id="credentialUsername")
    private List<WebElement> usernameList;

    @FindBy(id="credentialPassword")
    private List<WebElement> PasswordList;

    @FindBy(id="credential-url")
    private WebElement inputUrl;

    @FindBy(id="credential-username")
    private WebElement inputUsername;

    @FindBy(id="credential-password")
    private WebElement inputPassword;

    @FindBy(id="credentialSubmit")
    private WebElement submitButton;

    @FindBy(id="credential-modal-submit")
    private WebElement submitModalButton;

    private final WebDriver driver;

    public CredentialPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getCredentialsSize() {
        return this.urlList.size();
    }

    public List<String> getDetail(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        List<String> detail = new ArrayList<>(List.of(urlList.get(0).getText(),
                usernameList.get(0).getText(), PasswordList.get(0).getText()));
        return detail;

    }

    public String getViewablePass(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialTab);

        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0))));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editButton.get(0));
        List<WebElement> passList = driver.findElements(By.id("credential-password"));

        return passList.get(0).getText();

    }

    public void addCredential(WebDriver driver, String url, String username, String password, WebElement nav){
        WebDriverWait wait = new WebDriverWait(driver, 60);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialTab);

        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", addButton);

        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + url + "';", inputUrl);
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + username + "';", inputUsername);
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + password + "';", inputPassword);

        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", submitModalButton);
    }

    public void editCredential(WebDriver driver, String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialTab);

        wait.until(ExpectedConditions.elementToBeClickable((editButton.get(0))));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", editButton.get(0));

        wait.until(ExpectedConditions.elementToBeClickable(inputUrl));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + url + "';", inputUrl);

        wait.until(ExpectedConditions.elementToBeClickable(inputUsername));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + username + "';", inputUsername);

        wait.until(ExpectedConditions.elementToBeClickable(inputPassword));
        ((JavascriptExecutor)driver).executeScript("arguments[0].value='" + password + "';", inputPassword);

        wait.until(ExpectedConditions.elementToBeClickable(this.submitModalButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", this.submitModalButton);
    }

    public void deleteCredential(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(navCredentialTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialTab);

        wait.until(ExpectedConditions.elementToBeClickable(deleteButton.get(0)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", deleteButton.get(0));
    }
}
