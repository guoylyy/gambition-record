package com.gambition.recorder;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;

@Module(
        injects = MainActivity.class
)
@SuppressWarnings("unused")
public class DaggerDependencyModule {

    private final Context context;

    DaggerDependencyModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    FFmpeg provideFFmpeg() {
        return FFmpeg.getInstance(context.getApplicationContext());
    }

}
