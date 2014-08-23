package com.github.mikhailerofeev.scholarm.local;

import android.app.Application;
import android.content.Context;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    public Application getApplication() throws IOException {
        Context context = mock(Context.class);
        Application application = mock(Application.class);
        doReturn(context).when(application).getApplicationContext();
        File tmpDir = new File(Files.createTempDirectory("files-dir").toUri());
        tmpDir.deleteOnExit();
        doReturn(tmpDir).when(application).getFilesDir();
        doReturn(tmpDir).when(context).getFilesDir();
        return application;
    }
}
