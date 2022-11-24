package cdZulieferer;

import zuliefererInterface.Device;

public class CrazyCDPlayer implements Device {

  private String[][] titles;
  private int playingCD = -1;
  private int playingTitle;
  private boolean open = false;

  private enum volumeValue {
    quiet, normal, loud
  };

  private volumeValue volume = volumeValue.normal;

  public CrazyCDPlayer() {
    String[] klassik = { "Beethoven", "Mozart", "Dvorak" };
    String[] pop = { "Waka Waka", "In the air tonight" };
    String[] rock = { "Smoke on the water", "Highway to hell", "Ace of spades", "Welcome to the jungle" };

    titles = new String[3][];
    titles[0] = klassik;
    titles[1] = pop;
    titles[2] = rock;
  }

  @Override
  public void louder() {
    if (volume == volumeValue.quiet)
      volume = volumeValue.normal;
    else if (volume == volumeValue.normal)
      volume = volumeValue.loud;
  }

  @Override
  public void quieter() {
    if (volume == volumeValue.loud)
      volume = volumeValue.normal;
    else if (volume == volumeValue.normal)
      volume = volumeValue.quiet;
  }

  @Override
  public void next() {
    if (playingCD >= 0 && !open)
      if (playingTitle < titles[playingCD].length)
        playingTitle++;
  }

  @Override
  public void prev() {
    if (playingCD >= 0 && !open)
      if (playingTitle > 0)
        playingTitle--;
  }

  @Override
  public int getVolume() {
    if (volume == volumeValue.quiet)
      return -1;
    else if (volume == volumeValue.normal)
      return 0;
    else if (volume == volumeValue.loud)
      return 1;
    else
      return 99;
  }

  @Override
  public String[] getOptions() {
    return new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
  }

  @Override
  public void chooseOption(String opt) {
    if (opt.equalsIgnoreCase("1"))
      openClose();
    else if (opt.equalsIgnoreCase("2"))
      play();
    else if (opt.equalsIgnoreCase("3"))
      prev();
    else if (opt.equalsIgnoreCase("4"))
      next();
    else if (opt.equalsIgnoreCase("5"))
      last();
    else if (opt.equalsIgnoreCase("6"))
      first();
    else if (opt.equalsIgnoreCase("7"))
      louder();
    else if (opt.equalsIgnoreCase("8"))
      quieter();
    else if (opt.equalsIgnoreCase("9"))
      getVolume();
  }

  @Override
  public String play() {
    if (playingCD >= 0 && !open)
      return titles[playingCD][playingTitle];
    else
      return "";
  }

  public void first() {
    if (playingCD >= 0 && !open)
      playingTitle = 0;
  }

  public void last() {
    if (playingCD >= 0 && !open)
      playingTitle = titles[playingCD].length;
  }

  public void openClose() {
    if (open) {
      playingCD++;
      if (playingCD >= titles.length)
        playingCD = 0;
      open = false;
      playingTitle = 0;
    } else {
      open = true;
    }
  }

  // neue Methode zum Umgang mit Optionen
  public String getOptionText(int i) {
    if (i == 1)
      return "openClose";
    else if (i == 2)
      return "play";
    else if (i == 3)
      return "prev";
    else if (i == 4)
      return "next";
    else if (i == 5)
      return "last";
    else if (i == 6)
      return "first";
    else if (i == 7)
      return "louder";
    else if (i == 8)
      return "quieter";
    else if (i == 9)
      return "volume";
    else
      return "";
  }

  @Override
  public String getInfoText() {

    return "You are using CrazyCDPlayer";
  }

}