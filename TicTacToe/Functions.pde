void startGame(){
    player = choix; 
    play = true;
    spaces = 9;
    gameOver = false;
    winner = 0;
    row = 3; col = 3; spaces = 9; 
    grille = new Square[row][col]; 
    
    background(200);    
    
    x = 25;          
    y = 25;
    w = 125; h = 125;
    
    for(int i = 0; i < row; i++){
        for(int j = 0; j < col; j++){
            grille[i][j] = new Square(x, y, w, h);
            x += w; 
        }
        y += h;
        x = 25;
    }
}

void checkGameOver(){
    int r = row;
    int c = col;

    // three in a row
    for (int i = 0; i < r; i++){
        if(grille[i][0].getValue() == 1 && grille[i][1].getValue() ==  1 && grille[i][2].getValue() == 1){
            gameOver = true;
            winner = 1;
        }
        if(grille[i][0].getValue() == 2 && grille[i][1].getValue() ==  2 && grille[i][2].getValue() == 2){
            gameOver = true;
            winner = 2;
        }
    }
 
    // three in a column
    for (int i = 0; i < c; i++){
        if(grille[0][i].getValue() == 1 && grille[1][i].getValue() ==  1 && grille[2][i].getValue() == 1){
            gameOver = true;
            winner = 1;
        }
        if(grille[0][i].getValue() == 2 && grille[1][i].getValue() ==  2 && grille[2][i].getValue() == 2){
            gameOver = true;
            winner = 2;
        }
    }
 
    // top left to bottom right
    if(grille[0][0].getValue() == 1 && grille[1][1].getValue() ==  1 && grille[2][2].getValue() == 1){
        gameOver = true;
        winner = 1;
    }
    if(grille[0][0].getValue() == 2 && grille[1][1].getValue() ==  2 && grille[2][2].getValue() == 2){
        gameOver = true;
        winner = 2;
    }
 
    // bottom left to top right
    if(grille[2][0].getValue() == 1 && grille[1][1].getValue() ==  1 && grille[0][2].getValue() == 1){
        gameOver = true;
        winner = 1;
    }
    if(grille[2][0].getValue() == 2 && grille[1][1].getValue() ==  2 && grille[0][2].getValue() == 2){
        gameOver = true;
        winner = 2;
    }
    if (spaces == 0){
        gameOver = true;
    }
}

void checkWinner(){
    int r = row;
    int c = col;

    gameOver2 = false;
    winner2 = 0;

    // three in a row
    for (int i = 0; i < r; i++){
        if(grille[i][0].getValue() == 1 && grille[i][1].getValue() ==  1 && grille[i][2].getValue() == 1){
            gameOver2 = true;
            winner2 = -1;
        }
        if(grille[i][0].getValue() == 2 && grille[i][1].getValue() ==  2 && grille[i][2].getValue() == 2){
            gameOver2 = true;
            winner2 = 2;
        }
    }
 
    // three in a column
    for (int i = 0; i < c; i++){
        if(grille[0][i].getValue() == 1 && grille[1][i].getValue() ==  1 && grille[2][i].getValue() == 1){
            gameOver2 = true;
            winner2 = -1;
        }
        if(grille[0][i].getValue() == 2 && grille[1][i].getValue() ==  2 && grille[2][i].getValue() == 2){
            gameOver2 = true;
            winner2 = 2;
        }
    }
 
    // top left to bottom right
    if(grille[0][0].getValue() == 1 && grille[1][1].getValue() ==  1 && grille[2][2].getValue() == 1){
        gameOver2 = true;
        winner2 = -1;
    }
    if(grille[0][0].getValue() == 2 && grille[1][1].getValue() ==  2 && grille[2][2].getValue() == 2){
        gameOver2 = true;
        winner2 = 2;
    }
 
    // bottom left to top right
    if(grille[2][0].getValue() == 1 && grille[1][1].getValue() ==  1 && grille[0][2].getValue() == 1){
        gameOver2 = true;
        winner2 = -1;
    }
    if(grille[2][0].getValue() == 2 && grille[1][1].getValue() ==  2 && grille[0][2].getValue() == 2){
        gameOver2 = true;
        winner2 = 2;
    }
    if (spaces == 0){
        gameOver2 = true;
    }
}


void bestIaTurn() {
    Integer myInf = Integer.MIN_VALUE;
    int bestScore = myInf;
    Cordonee bestMove = new Cordonee(99,99);

    for(int i = 0; i < row; i++){
        for(int j = 0; j < col; j++){
            if (grille[i][j].getValue() == 0) {
                grille[i][j].value = 2;spaces--;
                    int score = minmax(false,0);
                    if (score > bestScore) {
                        bestScore = score;
                    
                        bestMove.setI(i);bestMove.setJ(j);
                    }
                        
                grille[i][j].value = 0;  spaces++;  
            }
        }
        
    }


    canDrawArc=true;
    endangle=2*PI;
    arcx= grille[bestMove.getI()][bestMove.getJ()].xPos+( grille[bestMove.getI()][bestMove.getJ()].s_width/2);
    arcy= grille[bestMove.getI()][bestMove.getJ()].yPos+( grille[bestMove.getI()][bestMove.getJ()].s_height/2);
    arcw= grille[bestMove.getI()][bestMove.getJ()].s_width-25-5-10-5+20;
    arch= grille[bestMove.getI()][bestMove.getJ()].s_height-25-5-10-5+20;

    grille[bestMove.getI()][bestMove.getJ()].value = 2;

    player = 1;
    spaces--;
}

int minmax(boolean isMax,int niveau) {
   
  checkWinner();
   
    if (gameOver2) {
   
        if(winner2==2){
            return (10-niveau);
        }  
        if(winner2==-1){ 
            return -(10+niveau);
        }
        return 0;   
    }

    if (isMax) {
        Integer myInf = Integer.MIN_VALUE;
        int bestScore = myInf;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if (grille[i][j].getValue() == 0) {
                    grille[i][j].value = 2;  spaces--;  
                    int score = minmax(false,niveau+1);
                    grille[i][j].value = 0;  spaces++;  
                    
                    bestScore = Math.max(bestScore, score);
      
                }
            }
        }
        return bestScore;
    }
    else {
        Integer myInf = Integer.MAX_VALUE;
        int bestScore = myInf;
        
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if (grille[i][j].getValue() == 0) {
                    grille[i][j].value = 1;  spaces--;  
                    int score = minmax(true,niveau+1);
                    grille[i][j].value = 0;  spaces++;  
                    bestScore = Math.min(bestScore, score);
                                  
              }
            }
        }
        return bestScore;
    }
}

