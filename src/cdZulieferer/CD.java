package cdZulieferer;

import java.lang.reflect.*;

import zuliefererInterface.Device;

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
    String[] options = null;
    try {
      Class thisClass = CD.class;
      Method[] methods = thisClass.getDeclaredMethods();
      int size = methods.length;
      options = new String[size];
      for (int i = 0; i < size; i++) {
        options[i] = methods[i].getName();
      }
    } catch (Throwable e) {
      System.out.println(e);
    }

    return options;
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
