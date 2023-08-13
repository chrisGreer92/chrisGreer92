package reversi;

public class SimpleModel implements IModel
{
	int [][] boardContents;
	int width;
	int height;
	int player;
	boolean finished;
	IView view;
	IController controller;
	
	@Override
	public void initialise(int width, int height, IView view, IController controller)
	{
		this.width = width;
		this.height = width;
		this.view = view;
		this.controller = controller;
		boardContents = new int[width][height];
	}

	@Override
	public void clear(int value)
	{
		for ( int x = 0 ; x < width ; x++ )
			for ( int y = 0 ; y < width ; y++ )
				boardContents[x][y] = value;
	}

	@Override
	public int getBoardWidth()
	{
		return width;
	}

	@Override
	public int getBoardHeight()
	{
		return height;
	}

	@Override
	public int getBoardContents(int x, int y)
	{
		return boardContents[x][y];
	}

	@Override
	public void setBoardContents(int x, int y, int value)
	{
		boardContents[x][y] = value;
	}

	@Override
	public void setPlayer(int player)
	{
		this.player = player;
	}

	@Override
	public int getPlayer()
	{
		return player;
	}

	@Override
	public boolean hasFinished()
	{
		return finished;
	}

	@Override
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
}
