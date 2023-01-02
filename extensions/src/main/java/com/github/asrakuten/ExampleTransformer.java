package com.github.asrakuten;

import com.github.tomakehurst.wiremock.extension.responsetemplating.RequestLine;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.TextFile;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.extension.StubLifecycleListener;
import com.github.tomakehurst.wiremock.http.*;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import wiremock.com.github.jknack.handlebars.Handlebars;
import wiremock.com.github.jknack.handlebars.Helper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.io.FileNotFoundException;

public class ExampleTransformer extends ResponseTemplateTransformer {
  public ExampleTransformer() {
    super(false, Collections.emptyMap());
  }

  public ExampleTransformer(boolean global) {
    this(global, Collections.emptyMap());
  }

  public ExampleTransformer(boolean global, String helperName, Helper<?> helper) {
    this(global, ImmutableMap.of(helperName, helper));
  }

  public ExampleTransformer(boolean global, Map<String, Helper> helpers) {
    this(global, new Handlebars(), helpers, null, null);
  }

  public ExampleTransformer(
      boolean global,
      Handlebars handlebars,
      Map<String, Helper> helpers,
      Long maxCacheEntries,
      Set<String> permittedSystemKeys) {
    super(global, handlebars, helpers, maxCacheEntries, permittedSystemKeys);
  }

  @Override
  public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) {
    try {
      return loadFile(request, responseDefinition, files, parameters);
    } catch(FileNotFoundException e) {
      System.out.println("GT");

      return ResponseDefinitionBuilder.like(responseDefinition)
        .withBodyFile("wurl/missing.json")
        .build();
    }
  }

  public ResponseDefinition loadFile(Request request, ResponseDefinition responseDefinition, FileSource files, Parameters parameters) throws FileNotFoundException {
    return super.transform(request, responseDefinition, files, parameters);
  }

  @Override
  public String getName() {
    return "example";
  }
}
