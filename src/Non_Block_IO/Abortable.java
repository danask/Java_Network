package Non_Block_IO;



// For exit

public class Abortable
{
	public boolean done = false;
	
	public Abortable()
	{
		init();
	}
	
	public void init()
	{
		done = false;
	}
	
	public boolean isDone()
	{
		return done;
	}


}

