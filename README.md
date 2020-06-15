[![web-tester version][0]][1] [![web-tester snapshot version][2]][3]

[![primeui-tester version][4]][5] [![primeui-tester snapshot version][6]][7]

# web-tester
The `web-tester` artifact provides you a API which helps you test your JSF-Page.
With this API it is easy to setup your test environment and send requests
against your [Axon.ivy Engine](https://developer.axonivy.com/download). 

## primeui-tester
If your JSF-Page contains [PrimeFaces ](https://www.primefaces.org/showcase/)
widgets, the `primeui-tester` gives you the possibility to interact with those
widgets and check if it's in the condition you expected it to be. 

# How to use in your project
The web-tester runs with [Selenide](https://selenide.org/),
[Selenium](https://selenium.dev/projects/) and [JUnit
5](https://junit.org/junit5/). Simply add this library to your dependencies in
the pom.xml:

```xml
<dependencies>
...
  <dependency>
    <groupId>com.axonivy.ivy.webtest</groupId>
    <artifactId>web-tester</artifactId>
    <version>8.0.1</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

Add a new test class to test your process (e.g.
[WebTestRegistrationForm.java](https://github.com/axonivy/project-build-examples/blob/master/compile-test/crmIntegrationTests/src_test/ch/ivyteam/integrationtest/WebTestRegistrationForm.java))
or a PrimeFaces widget (e.g.
[TestPrimeUi.java](primeui-tester/src/test/java/com/axonivy/ivy/webtest/primeui/TestPrimeUi.java)):

```java
@IvyWebTest
public class WebTest
{

  @Test
  public void registerNewCustomer()
  {
    open(EngineUrl.base());
    $(By.linkText("customer/register.ivp")).shouldBe(visible).click();
    $(By.id("form:firstname")).sendKeys("Unit");
    $(By.id("form:lastname")).sendKeys("Test");
    $(By.id("form:submit")).shouldBe(enabled).click();
    $(By.id("form:newCustomer")).shouldBe(visible, text("Unit Test"));
  }

  @Test
  public void testSelectOneMenu()
  {
    open("https://primefaces.org/showcase/ui/input/oneMenu.xhtml");
    SelectOneMenu selectOne = PrimeUi.selectOne(selectMenuForLabel("Basic:"));
    assertThat(selectOne.getSelectedItem()).isEqualTo("Select One");
    String ps4 = "PS4";
    selectOne.selectItemByLabel(ps4);
    assertThat(selectOne.getSelectedItem()).isEqualTo(ps4);
  }

}
```

## Changelog
- [web-tester](web-tester/CHANGELOG.md)
- [primeui-tester](primeui-tester/CHANGELOG.md)

## Authors

[ivyTeam](https://developer.axonivy.com/)

[![Axon.ivy](https://www.axonivy.com/hubfs/brand/axonivy-logo-black.svg)](http://www.axonivy.com)


## License
The Apache License, Version 2.0

[0]: https://img.shields.io/badge/web--tester-8.0.1-green
[1]: https://repo1.maven.org/maven2/com/axonivy/ivy/webtest/web-tester/
[2]: https://img.shields.io/badge/web--tester-8.0.2--SNAPSHOT-yellow
[3]: https://oss.sonatype.org/content/repositories/snapshots/com/axonivy/ivy/webtest/web-tester/
[4]: https://img.shields.io/badge/primeui--tester-7.0.0-green
[5]: https://repo1.maven.org/maven2/com/axonivy/ivy/webtest/primeui-tester/
[6]: https://img.shields.io/badge/primeui--tester-8.0.2--SNAPSHOT-yellow
[7]: https://oss.sonatype.org/content/repositories/snapshots/com/axonivy/ivy/webtest/primeui-tester/
