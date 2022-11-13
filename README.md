MAVEN:

Tools:
Selenium
TestNG
Rest Assured (for REST API)
DBUtils (for Database connection)
Logger (log4j2)
Reporting Tool (Extent Report)
WebDriverManager
ImageRecognition (AShot, also we can create our own)
CSVUtils - Reading CSV Files
Gson/Jackson (serialization/deserialization between JSON format and Java class)
Commons-IO

Structure of our Framework (Page Object Model)

OBJECT Classes - POJO (Plain Old Java Objects)
User (username, FirstName, LastName, Password, Email....)
Hero (HeroName, Class, Level, Username)
ApiError (status, error, exception, message, path)

DATA Classes (final classis with consts):
HeroClass - For Hero Class DropDown list // and similar for all drop down lists
PageUrlPaths - LOGIN_PAGE = "/signin";
ApiCalls - GET_USER =
CommonStrings - 

UTILS Classes (classis with static methods):
LoggerUtils
WebDriverUtils - extends LoggerUtils
PropertiesUtils - extends LoggerUtils
DateTimeUtils - all coversion, parsing, formatting DateTimes - extends LoggerUtils
RestApiUtils - 
ScreenShotUtils 


Pages:
- BasePageClass - all basic selenium methods not related to our application - extends LoggerUtils
	- CommonLoggedInPage (extends BasePageClass) - for all pages when User is logged in
		- HomePage (extends CommonLoggedInPage) - locators and methods that operates with web elements on that page
		- UsersPage
		- HeroesPage
		-...
	- CommonLoggedOutPage (extends BasePageClass) - for all pages when User is logged out
		- LoginPage - (extends CommonLoggedOutPage)
		- RegisterPage
		- ResetPasswordPage
	- DialogBox (extends BasePageClass)


LOW LEVEL - locators
MID LEVEL - methods for basic actions for each element
HI LEVEL - complex actions on that page

Resources:
properties file
	environment: local
	localBaseUrl = http://localhost:8080
	testBaseUrl = http://18.219.75.209:8080
	stageBaseUrl
	prodBaseUrl = http://www.samsara.com
	browser
	admin credentials
	root credentials
log4j2.properties


Tests:


- BaseTestClass - extends LoggerUtils
	- LoginTests
	- UsersTests
	- HeroesTests
	- AdminTests

Suites:
RegressionSuiteChrome
SanitySuiteFirefox
RestApiSuite

Listeners

DataProviders


Test Resources (test data):
documents
images




getBaseUrl() + PageUrlPaths.LOGIN_PAGE
