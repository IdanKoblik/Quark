package com.github.idankoblik.quark;

import com.google.common.reflect.ClassPath;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

/**
 * Class for auto registering JDA event listeners
 * without the need to pass every listener class.
 */
public class JdaAutoListenerRegister {

    private final JDABuilder builder;

    /**
     * @param builder - JDABuilder
     */
    public JdaAutoListenerRegister(JDABuilder builder) {
        this.builder = builder;
    }

    /**
     * Using listener class's package location to register them.
     * @param packageName - The package where listener class's located.
     */
    public void registerListeners(String packageName) {
        ClassLoader loader = getClass().getClassLoader();

        ClassPath classPath;
        try {
            classPath = ClassPath.from(loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(packageName)) {
            try {
                Class<?> clazz = Class.forName(classInfo.getName(), true, loader);
                if (ListenerAdapter.class.isAssignableFrom(clazz))
                    builder.addEventListeners(clazz.getDeclaredConstructor().newInstance());
            } catch (Throwable e) {
                e.getStackTrace();
            }
        }
    }
}
