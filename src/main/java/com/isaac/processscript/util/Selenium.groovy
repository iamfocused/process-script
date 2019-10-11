package com.isaac.processscript.util

import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.OutputType
import org.openqa.selenium.Point
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import javax.imageio.ImageIO
import java.awt.image.BufferedImage


System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox");
System.setProperty("webdriver.gecko.driver", "/Users/eorionx/Downloads/selenium/geckodriver");

FirefoxOptions options = new FirefoxOptions();
options.setHeadless(true);
WebDriver driver = new FirefoxDriver(options);
//driver.manage().window().setSize(new Dimension(1366, 768))
driver.manage().window().maximize();
driver.get("https://metabase.eorionsolution.com/public/question/008e31a7-eadd-414a-b0fe-df602efd7e03");
WebDriverWait wait = new WebDriverWait(driver, 20);
WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/div[1]")));

// 获得webElement的位置和大小。
Point location = webElement.getLocation();
Dimension size = webElement.getSize();
// 创建全屏截图
TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(takesScreenshot.getScreenshotAs(OutputType.BYTES)));
// 截取webElement所在位置的子图
BufferedImage image = originalImage.getSubimage(location.getX(), location.getY(), size.getWidth(), size.getHeight());

println(size.getWidth()+"     "+size.getHeight());
ImageIO.write(image, "png", new File("/Users/eorionx/Downloads/selenium/demo.png"));
ImageIO.write(image, "BMP", new File("/Users/eorionx/Downloads/selenium/demo.bmp"));