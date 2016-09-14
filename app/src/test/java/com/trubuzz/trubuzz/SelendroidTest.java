package com.trubuzz.trubuzz;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.URL;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.common.device.DeviceTargetPlatform;

/**
 * Created by king on 2016/9/12.
 */
public class SelendroidTest {

    @Test
    public void test() throws Exception {
        SelendroidCapabilities capa = SelendroidCapabilities.emulator("io.selendroid.androiddriver:0.17.0");
        capa.setPlatformVersion(DeviceTargetPlatform.ANDROID23);
        capa.setEmulator(true);
        capa.setModel("Nexus_4_API_23");

        WebDriver driver = new SelendroidDriver(new URL("http://localhost:5555/wd/hub"),capa);
        WebElement inputField = driver.findElement(By.id("my_text_field"));
        Assert.assertEquals("true", inputField.getAttribute("enabled"));
        inputField.sendKeys("Selendroid");
        Assert.assertEquals("Selendroid", inputField.getText());
        driver.quit();
    }

}
