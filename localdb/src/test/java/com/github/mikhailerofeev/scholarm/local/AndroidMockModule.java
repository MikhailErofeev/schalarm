package com.github.mikhailerofeev.scholarm.local;

import android.app.Application;
import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author m-erofeev
 * @since 22.08.14
 */
public class AndroidMockModule extends AbstractModule {

  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  public Application getApplication() {
    Context context = mock(Context.class);
    Application mock = mock(Application.class);
    doReturn(context).when(mock).getApplicationContext();
    return mock;
  }
}
