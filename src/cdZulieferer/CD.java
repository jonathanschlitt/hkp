package cdZulieferer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import fahrzeugHersteller.Device;

public class CD implements Device {
  private int volume;
  private int songNumber;
  private boolean cdInserted;
  Class<?> c = null;
  private boolean replayStatus;

  public CD() {

    this.volume = 10;
    this.songNumber = 0;
    this.cdInserted = false;
    this.replayStatus = true;
  }

  @Override
  public void louder() {

    if (volume < 20) {
      volume++;
    } else {

    }

    System.out.println("increased volume to " + volume);

  }

  @Override
  public void quieter() {
    System.out.println("decreased volume to " + volume);
    if (volume > 0) {
      volume--;
    }

  }

  @Override
  public int getVolume() {

    return volume;
  }

  @Override
  public void next() {
    if (songNumber < 20) {
      songNumber++;
    } else {
      songNumber = 0;
    }
    System.out.println("playing next Song " + songNumber);

  }

  @Override
  public void prev() {
    if (songNumber > 0) {
      songNumber--;
    } else {
      songNumber = 20;
    }
    System.out.println("playing prev Song " + songNumber);
  }

  @Override
  public String getInfoText() {
    System.out.println("get some info");
    return "Infos";
  }

  @Override
  public String[] getOptions() {
	// Get all methods names that are implemented via the interface as List
      final List<String> superMethodNames = new ArrayList<>();

      // Get all methods of the super class
      for (final Method method : this.getClass().getSuperclass().getMethods()) {
          superMethodNames.add(method.getName());
      }

      // Get all available methods of CDPlayer as List
      final List<String> cdPlayerMethodNames = new ArrayList<>();

      // Get all methods of UsbPlayer
      for (final Method method : this.getClass().getMethods()) {
          cdPlayerMethodNames.add(method.getName());
      }

      // Remove all methods that are implemented via the interface
      cdPlayerMethodNames.removeAll(superMethodNames);
      
      final List<String> deviceMethodNames = new ArrayList<>();
      
      for (final Method method : Device.class.getMethods()) {
      	deviceMethodNames.add(method.getName());
      }

      cdPlayerMethodNames.removeAll(deviceMethodNames);

      return cdPlayerMethodNames.toArray(new String[cdPlayerMethodNames.size()]);
  }

  @Override
  public void chooseOption(String s) {
    System.out.println(s);

  }

  @Override
  public String play() {
    if (!replayStatus) {
      replayStatus = true;
    }
    System.out.println("playing cool song...");

    return "cool song";
  }

  public void ejectCD() {

    if (cdInserted) {
      System.out.println("Ejecting CD...");
      cdInserted = false;
    } else {
      System.out.println("No CD to eject.");
    }

  }

  public void insertCD(String name) {
    if (!cdInserted) {
      System.out.println("Reading CD " + name + "...");
      cdInserted = true;
    } else {
      System.out.println("There is already a CD inserted.");
    }

  }

  public void pause() {
    if (replayStatus) {
      replayStatus = false;
      System.out.println("pausing replay");
    } else {
      System.out.println("replay is alread paused");

    }

  }
}
