package com.github.idankoblik.quark;

import com.github.idankoblik.quark.quark.JdaAutoListenerRegister;
import net.dv8tion.jda.api.JDABuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdaAutoListenerRegisterTest {

    @Test
    public void jdaAutoListenerRegisterTest() {
        JDABuilder mockJdaBuilder = mock(JDABuilder.class);

        List<Object> listeners = new ArrayList<>();
        when(mockJdaBuilder.addEventListeners(any())).then(ctxt -> {
            listeners.addAll(Arrays.asList(ctxt.getArguments()));
            return mockJdaBuilder;
        }).thenReturn(mockJdaBuilder);

        JdaAutoListenerRegister register = new JdaAutoListenerRegister(mockJdaBuilder);
        register.registerListeners(getClass().getPackageName() + ".listeners");

        assertEquals(1, listeners.size());
    }
}
