package com.mjc.school.controller.command;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotations.CommandHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandDispatcher {
    private final Map<String, String> commandMap = new HashMap<>();

    public CommandDispatcher() {
        commandMap.put("1",  "readAll"  );
        commandMap.put("2",  "readAll"  );
        commandMap.put("3",  "readAll"  );
        commandMap.put("4",  "readById" );
        commandMap.put("5",  "readById" );
        commandMap.put("6",  "readById" );
        commandMap.put("7",  "create"   );
        commandMap.put("8",  "create"   );
        commandMap.put("9",  "create"   );
        commandMap.put("10", "update"   );
        commandMap.put("11", "update"   );
        commandMap.put("12", "update"   );
        commandMap.put("13", "deleteById");
        commandMap.put("14", "deleteById");
        commandMap.put("15", "deleteById");
        commandMap.put("16", "getTagsByNewsId");
        commandMap.put("17", "getAuthorByNewsId");
        commandMap.put("18", "getNewsByParams");
    }
    public Object dispatch(BaseController<?, ?, ?> controller, String commandName, List<Object> commandParams) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = BaseController.class.getDeclaredMethods();

        Method method = null;
        for (Method m : methods) {
            if (m.isAnnotationPresent(CommandHandler.class)) {
                System.out.println(m);
                if (m.getName().equals(commandMap.get(commandName))){
                    method = m;
                    break;
                }
            }
        }

        if (method == null) {
            throw new IllegalArgumentException("No method found for command: " + commandName);
        }

        // invoke the method on the controller with the specified parameters
        Object[] params = commandParams.toArray();
        return method.invoke(controller, params);
    }
}
