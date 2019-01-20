package ru.pschsch.pschschapps.githubwork.ExtraUtils;

public class ASHotKeys {
    private static final ASHotKeys instance = new ASHotKeys();

    public static ASHotKeys getInstance() {
        return instance;
    }

    private ASHotKeys() {
        String hotkey1 = "Ctrl + Alt + O - оптимизация неиспользуемого импорта";
        String hotkey2 = "Alt + Insert - создание метода";

    }
}
