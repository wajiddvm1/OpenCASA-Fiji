package data;

import java.io.File;

import ij.IJ;

public final class SettingsFile {

  private SettingsFile() {
  }

  public static File get() {
    String pluginsDirectory = IJ.getDirectory("plugins");
    if (pluginsDirectory != null) {
      File applicationDirectory = new File(pluginsDirectory).getParentFile();
      if (applicationDirectory != null) {
        return new File(applicationDirectory, "settings.config");
      }
    }
    return new File(System.getProperty("user.dir"), "settings.config");
  }
}