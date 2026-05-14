package me.bingyue.fuckbiliads;

import java.lang.reflect.Method;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam;

public class MainHook extends XposedModule {
    private static final String TARGET_PACKAGE = "tv.danmaku.bili";
    private static final String SPLASH_CLASS = "tv.danmaku.bili.ui.splash.ad.model.Splash";

    @Override
    public void onPackageReady(PackageReadyParam param) {
        if (!TARGET_PACKAGE.equals(param.getPackageName())) {
            return;
        }

        try {
            Class<?> splashClass = param.getClassLoader().loadClass(SPLASH_CLASS);
            Method isValid = splashClass.getDeclaredMethod("isValid");
            if (isValid.getReturnType() != boolean.class) {
                return;
            }
            isValid.setAccessible(true);
            hook(isValid).intercept(new Hooker());
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static class Hooker implements XposedInterface.Hooker {
        @Override
        public Object intercept(XposedInterface.Chain chain) {
            return false;
        }
    }
}
