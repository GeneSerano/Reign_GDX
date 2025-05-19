package com.southcentralpositronics.reign_gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtils {
    public static FileHandle getClassPath(String path) {
        return Gdx.files.classpath(path);
    }

    public static FileHandle getInternalPath(String path) {
        return Gdx.files.internal(path);
    }

    public static FileHandle getLocalPath(String path) {
        return Gdx.files.local(path);
    }

    public static FileHandle getExternalPath(String path) {
        return Gdx.files.external(path);
    }
}
