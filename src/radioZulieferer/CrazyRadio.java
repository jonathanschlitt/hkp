package radioZulieferer;

import zuliefererInterface.Device;

public class CrazyRadio implements Device {

  private int volume = 0;
  private boolean mute = true;
  private String[] channel;
  private int activeChannel;

  private boolean fm = true;

  @Override
  public void louder() {
    if (!mute)
      volume++;
    else
      throw new RuntimeException("Lautstaerke nicht anpassbar.");
  }

  @Override
  public void quieter() {
    if (!mute)
      volume--;
    else
      throw new RuntimeException("Lautstaerke nicht anpassbar");
  }

  @Override
  public void next() {
    activeChannel++;
  }

  @Override
  public void prev() {
    activeChannel--;
  }

  @Override
  public int getVolume() {
    return -1;
  }

  @Override
  public String[] getOptions() {
    return new String[] { "search", "mute", "AM", "FM" };
  }

  @Override
  public void chooseOption(String opt) {
    if (opt.equalsIgnoreCase("search")) {
      search();
    } else if (opt.equalsIgnoreCase("mute")) {
      mute();
    } else if (opt.equalsIgnoreCase("AM")) {
      changeToAM();
    } else if (opt.equalsIgnoreCase("FM")) {
      changeToFM();
    } else {
      throw new RuntimeException("Keine solche Option.");
    }
  }

  @Override
  public String play() {
    if (volume < 1 || volume > 3) {
      throw new RuntimeException("Volume falsch gesteuert.");
    } else {
      if (fm && channel != null) {
        if (!mute && activeChannel < channel.length)
          return "playing " + channel[activeChannel];
        else
          throw new RuntimeException("Kein Empfang");
      } else {
        return "chrchrrrrchrrchchrrrr";
      }
    }
  }

  public void mute() {
    mute = !mute;
  }

  public void search() {
    channel = new String[] { "FFH", "HR", "Bob" };
    activeChannel = 0;
  }

  public void changeToAM() {
    fm = false;
  }

  public void changeToFM() {
    fm = true;
  }

  @Override
  public String getInfoText() {
    return "Sie nutzen CrazyRadio";
  }
}