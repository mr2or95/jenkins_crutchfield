#----------------------------------
# Test to log into www.crutchfield.com
#----------------------------------
    
@RunWith(Cucumber.class)
 
Feature: Log into Crutchfield Account
This test logs into an existing account on the crutchfield.com website
and then we take a look at the rewards entitled.

Scenario: Verify that user is able to login and redeems reward points if points are available
	# Given member logs into the website https://www.crutchfield.com with mr2or95@comcast.net username and Yes1Heard password
	Given member logs into the website with username and password 
		| website  | https://www.crutchfield.com |
		| login	   | mr2or95@comcast.net		 |
		| passw	   | Yes1Heard					 | 
	And successfully logs in
	When member checks to make sure reward points are available
	And member selects an available reward redemption
	Then member is given the reward when submitted
 
      
   