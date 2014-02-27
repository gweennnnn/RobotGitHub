package established;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ButtonPressListener implements ButtonListener
{
	Robot subject;
	
	public ButtonPressListener(Robot r)
	{
		this.subject = r;
	}
	
	public void buttonPressed(Button b) {
		subject.buttonResponse(b);
	}

	public void buttonReleased(Button b) {
		// TODO Auto-generated method stub
		
	}
}
