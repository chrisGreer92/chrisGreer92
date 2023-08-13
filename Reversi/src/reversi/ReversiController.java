package reversi;

public class ReversiController implements IController {

    IModel model;
    IView view;
    int maxWidth,maxHeight;


    @Override
    public void initialise(IModel model, IView view) {
        this.model = model;
        this.view = view;

        maxWidth = model.getBoardWidth() - 1;
        maxHeight = model.getBoardHeight() - 1;

    }

    @Override
    public void startup() {

        resetBoard();
        model.setFinished(false);

        //Set player 1 as starting player
        model.setPlayer(1);
        // Set up the feedbacks
        setTurnFeedback();

    }

    public void setTurnFeedback(){

        int currentNo = model.getPlayer();
        int nextNo = 3 - currentNo;

        String currentStr = currentNo == 1 ? "White" : "Black";
        String nextStr = nextNo == 1 ? "White" : "Black";

        view.feedbackToUser(currentNo,currentStr + " player – choose where to put your piece");
        view.feedbackToUser(nextNo,nextStr + " player – not your turn");

    }

    public void resetBoard(){

        // Not sure if needed but below logic should always put tiles in the middle
        for (int w = 0 ; w <= maxWidth ; w++) {
            for (int h = 0 ; h <= maxHeight ; h++){
                if (w == maxWidth/2 && h == maxHeight/2 || w == (maxWidth/2)+1 && h == (maxHeight/2)+1 )
                    model.setBoardContents(w,h,1);
                else if (w == (maxWidth/2) && h == maxHeight/2+1 || w == (maxWidth/2)+1 && h == (maxHeight/2))
                    model.setBoardContents(w,h,2);
                else
                    model.setBoardContents(w,h,0);
            }
        }

        view.refreshView();
    }

    @Override
    public void update() {

        int currentPlayer = model.getPlayer();

        view.refreshView();

        //Need to check here if any playable squares
        int[] next = findBest(currentPlayer);
        if (next[0] > 0) {
            setTurnFeedback();
            return;
        }

        // If not check whether the other player has any squares
        int otherPlayer = 3 - currentPlayer;
        int[] nextOther = findBest(otherPlayer);
        if (nextOther[0] > 0){
            model.setPlayer(otherPlayer);
            setTurnFeedback();
        }else{ //Otherwise there's no pieces left to play
            completeGame();
        }

    }

    public void completeGame(){

        int whiteCount=0, blackCount=0;
        String message;

        for (int x = 0; x <= maxWidth; x++){
            for (int y = 0; y <= maxHeight; y++){
                if (model.getBoardContents(x,y) == 1)
                    whiteCount++;
                if (model.getBoardContents(x,y) == 2)
                    blackCount++;
            }
        }

        if (whiteCount > blackCount){
            message = "White won. White "+whiteCount+" to Black "+blackCount+". Reset the game to replay.";
        } else if (whiteCount < blackCount) {
            message = "Black won. Black "+blackCount+" to White "+whiteCount+". Reset the game to replay.";
        } else {
            message = "Draw. Both players ended with "+blackCount+" pieces. Reset game to replay.";
        }

        view.feedbackToUser(1,message);
        view.feedbackToUser(2,message);

        model.setFinished(true);

    }

    @Override
    public void squareSelected(int player, int x, int y) {

        //Check if we're already finished
        if (model.hasFinished())
            return;

        //Check whether it's the correct player
        if (player != model.getPlayer()) {
            view.feedbackToUser(player, "It is not your turn!");
            return;
        }

        //Then whether that tile has already been played
        if (model.getBoardContents(x, y) != 0)
            return;

        //Then check if it's a valid play (i.e. would capture a piece)
        if (captureCount(x,y,player,false) == 0)
            return;

        //Otherwise Set the piece
        model.setBoardContents(x, y, player);
        captureCount(x,y,player,true);

        //once set, set as other players turn
        model.setPlayer(3 - player);
        update();

    }

    public int captureCount(int x, int y, int player, boolean flagCapture){

        int xAxis,yAxis,xOffset,yOffset;
        int maxTot = Math.max(maxHeight, maxWidth);
        int flagSurrounded, count, totalCount=0;
        int otherPlayer = 3-player;
        int capMax = maxHeight*maxWidth;
        int[] xCap = new int[capMax];
        int[] yCap = new int[capMax];

        for (xOffset=-1; xOffset <= 1 ; xOffset++){
            for (yOffset=-1; yOffset <=1; yOffset++){
                if (xOffset == 0 && yOffset == 0)
                    continue;

                flagSurrounded=0;
                count=0;
                for ( int i = 1 ; i < maxTot ; i++ ){
                    xAxis = x + i*xOffset;
                    yAxis = y + i*yOffset;
                    if (xAxis > maxWidth || xAxis < 0 || yAxis > maxHeight || yAxis < 0)
                        break;
                    if (model.getBoardContents(xAxis,yAxis) == 0)
                        break;
                    if(model.getBoardContents(xAxis, yAxis) == otherPlayer) {
                        xCap[(count + totalCount)] = xAxis;
                        yCap[(count + totalCount)] = yAxis;
                        count++;
                    }
                    if (model.getBoardContents(xAxis, yAxis) == player) {
                        flagSurrounded = 1;
                        break;
                    }
                }
                if (flagSurrounded == 0){ //Iterate through the array clearing the values we set
                    for (int i = totalCount; i < (totalCount + count) ; i++){
                        xCap[i] = 0;
                        yCap[i] = 0;
                    }
                } else
                    totalCount += count;
            }
            }

        if (flagCapture){
            for (int i = 0 ; i < capMax ; i++ ) {
                if (xCap[i] == 0 && yCap[i] == 0)
                    continue;
                model.setBoardContents(xCap[i],yCap[i], player);
            }
        }

        return totalCount;

    }

    public int[] findBest(int player) {

        int[] best = {0,-1,-1};

        for (int x = 0; x <= maxWidth; x++){
            for (int y = 0; y <= maxHeight; y++){
                if (model.getBoardContents(x,y) == 0) {
                    if (captureCount(x, y, player,false) > best[0]) {
                        best[0] = captureCount(x, y, player,false);
                        best[1] = x;
                        best[2] = y;
                    }
                }
            }
        }

        return best;
    }

    @Override
    public void doAutomatedMove(int player) {

        int[] best = findBest(player);

        if (best[0] > 0)
           squareSelected(player,best[1],best[2]);

    }
}
