/*
 * Copyright (C) 2021 Axon Ivy AG
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.axonivy.ivy.webtest;

import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

class IvyWebTestExtension implements BeforeEachCallback, BeforeAllCallback
{
  
  static final String BROWSER_DEFAULT = "firefox";
  static final boolean HEADLESS_DEFAULT = true;
  static final String REPORT_FOLDER_DEFAULT = "target/selenide/reports";
  
  @Override
  public void beforeAll(ExtensionContext context) throws Exception
  {
    Configuration.browser = browser(context);
    Configuration.headless = headless(context);
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception
  {
    Configuration.reportsFolder = reportFolder(context);
    Selenide.open();
  }
  
  private boolean headless(ExtensionContext context)
  {
    return context.getConfigurationParameter("ivy.selenide.headless")
            .map(param -> BooleanUtils.toBoolean(param))
            .orElseGet(() -> findAnnotation(context).map(a -> a.headless())
                    .orElse(HEADLESS_DEFAULT));
  }
  
  private String browser(ExtensionContext context)
  {
    return context.getConfigurationParameter("ivy.selenide.browser")
            .orElseGet(() -> findAnnotation(context).map(a -> a.browser())
                    .orElse(BROWSER_DEFAULT));
  }
  
  private String reportFolder(ExtensionContext context)
  {
    String methodDir = context.getTestClass().map(c -> c.getName() + "/").orElse("") + 
            context.getTestMethod().map(m -> m.getName()).orElse("");
    String reportDir = context.getConfigurationParameter("ivy.selenide.reportfolder")
            .orElseGet(() -> findAnnotation(context).map(a -> a.reportFolder())
                    .orElse(REPORT_FOLDER_DEFAULT));
    return StringUtils.appendIfMissing(reportDir, "/") + methodDir;
  }
  
  private Optional<IvyWebTest> findAnnotation(ExtensionContext context)
  {
    return context.getTestClass().map(c -> c.getAnnotation(IvyWebTest.class));
  }

}
