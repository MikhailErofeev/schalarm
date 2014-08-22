package com.github.mikhailerofeev.scholarm.local.stuff;

import com.github.mikhailerofeev.scholarm.api.services.QuestionsService;
import com.github.mikhailerofeev.scholarm.local.services.QuestionsServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class GuiceModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(QuestionsService.class).to(QuestionsServiceImpl.class);
  }
}
