interface PhoneInterface{
	int BUTTONS = 20;
	void sendCall();
	void receiveCall();
}

interface MobilePhoneInterface extends PhoneInterface{
	void sendSMS();
	void receiveSMS();
}

interface MP3Interface{
	public void play();
	public void stop();
}

class PDA {
	public int caculate(int x, int y){
		return x+y;
	}
}
