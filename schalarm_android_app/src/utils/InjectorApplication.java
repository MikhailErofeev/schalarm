package utils;

import android.app.Application;
import android.content.Context;
import com.github.mikhailerofeev.scholarm.local.stuff.LocalQuestionBaseModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author m-erofeev
 * @since 23.08.14
 */
public class InjectorApplication extends Application {

    private static Context context;
    private static Injector injector;


    public static <T> T get(Class<T> type) {
        return injector.getBinding(type).getProvider().get();
    }

    public void onCreate() {
        injector = Guice.createInjector(new ApplicationModule(), new LocalQuestionBaseModule());
        context = getApplicationContext();
    }

    private class ApplicationModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Application.class).toInstance(InjectorApplication.this);
        }
    }
}
