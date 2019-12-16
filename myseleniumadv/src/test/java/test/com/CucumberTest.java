/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 *
 * @author Z_Wong 25
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"} ,
        features="src/Features/",
        dryRun = false
)
public class CucumberTest {
    
}
