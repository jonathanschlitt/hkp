interface Device {

	public void louder();

	public void quieter();

	public int getVolume();

	public void next();

	public void prev();

	public String getInfoText();

	public String[] getOptions();

	public void chooseOption(String opt);

	public String play();

}
