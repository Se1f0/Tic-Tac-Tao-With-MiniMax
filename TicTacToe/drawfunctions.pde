void drawMenu() {
    background(255);
    
    stroke(0,alpha);
    textSize(50);
    fill(23,206,244,alpha);
    text("Tic",85,50);
    fill(254,48,48,alpha);
    text("Tac",170,50);
    fill(0,alpha);
    text("Toe",260,50);
    
    tint(255,alpha);
    image(bg, 0, 60, 425, 300);
    
    noFill();
    rect(62, 380, 310, 150,15);
    textSize(17);
    fill(0,alpha);
    text("Press 'P' for Player Vs Player mode\n\nPress 'A' for Player Vs IA mode",70,380+50);
}

void drawChoose() {
    stroke(0);
    fill(54,100,137);
    rect(62, 200, 300, 150,15);
    textSize(17);
    fill(255);
    if(tempmode==1)
      text("                  Player 1\n    Press 'X' to choose the cross\n    Press 'O' to choose the circle\n    Press 'Backspace' to go back",70,200+30);
    else
      text("           Who to start first\n    Press 'X' for player first\n    Press 'O' for IA first\n    Press 'Backspace' to go back",70,200+30);
}

void drawGride() {
    for(int i = 0; i < row; i++){
        for(int j = 0; j < col; j++){
            grille[i][j].display();
        }
    }
    if (player == 1 && !gameOver && !canDrawArc) {
        fill(23,206,244);
        text("X turn",180, 450-20);   
    }
    if (player == 2 && !gameOver && !canDrawArc) {
        fill(254,48,48);
        text("O turn",180, 450-20);
    }
    noFill();
    if (gameOver) {
        stroke(255);
    }
    else {
        stroke(0);
    }    
    rect(132, y_button-30, 165, 100,15);
    textSize(17);
    if (gameOver) {
        fill(255);
    }
    else {
        fill(0);
    }
    text("Press 'R' to replay\nPress 'E' to exit",140,y_button+5);
}

void drawLine(int w){
  int r = row;
    int c = col;
        
        
          if(w==1) stroke(23,206,244); else stroke(254,48,48);       
          strokeWeight(15);
        
    for (int i = 0; i < r; i++){
        if(grille[i][0].getValue() == w && grille[i][1].getValue() ==  w && grille[i][2].getValue() == w){
          
            line( grille[i][0].xPos,  grille[i][0].yPos+62,  grille[i][2].xPos+125,  grille[i][2].yPos +62);
            stroke(255);
            strokeWeight(10);
            line( grille[i][0].xPos,  grille[i][0].yPos+62,  grille[i][2].xPos+125,  grille[i][2].yPos +62);
        }
        
    }

              if(w==1) stroke(23,206,244); else stroke(254,48,48);       
          strokeWeight(15);

 
    // three in a column
    for (int i = 0; i < c; i++){
        if(grille[0][i].getValue() == w && grille[1][i].getValue() ==  w && grille[2][i].getValue() == w){
            line( grille[0][i].xPos+62,  grille[0][i].yPos,  grille[2][i].xPos+62,  grille[2][2].yPos+125);
            stroke(255);
            strokeWeight(10); 
            line( grille[0][i].xPos+62,  grille[0][i].yPos,  grille[2][i].xPos+62,  grille[2][2].yPos+125);
        }
        
    }


          if(w==1) stroke(23,206,244); else stroke(254,48,48);        
          strokeWeight(15);

    // top left to bottom right
    if(grille[0][0].getValue() == w && grille[1][1].getValue() ==  w && grille[2][2].getValue() == w){
        
        line( grille[0][0].xPos,  grille[0][0].yPos,  grille[2][2].xPos+125,  grille[2][2].yPos +125);
        stroke(255);
        strokeWeight(10);
        line( grille[0][0].xPos,  grille[0][0].yPos,  grille[2][2].xPos+125,  grille[2][2].yPos +125);
    }
    

          if(w==1) stroke(23,206,244); else stroke(254,48,48);        
          strokeWeight(15);

    // bottom left to top right
    if(grille[2][0].getValue() == w && grille[1][1].getValue() ==  w && grille[0][2].getValue() == w){
        
        line( grille[2][0].xPos,  grille[2][0].yPos+125,  grille[0][2].xPos+125,  grille[0][2].yPos);
        stroke(255);
        strokeWeight(10);
        line( grille[2][0].xPos,  grille[2][0].yPos+125,  grille[0][2].xPos+125,  grille[0][2].yPos);

    }
    strokeWeight(1);
    stroke(0);
}


void mousePressed() {
    if(!canDrawArc){
        if (mode != 0) {
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    grille[i][j].click(mouseX, mouseY,mode);
                }
            }   
        }
    }    
}

void keyPressed() {
    if (!canDrawArc) {
        if ((mode == 0) && ((key == 'P') || (key == 'p'))) {
            mode = -1;
            fill(255);
            alpha = 80;
            tempmode=1;
            drawMenu();
            drawChoose();
        }
        if ((mode == 0) && ((key == 'A') || (key == 'a'))) {
            mode = -1;
            fill(255);
            alpha = 80;
            tempmode=2;
            drawMenu();
            drawChoose();
        }
        if ((mode == -1) && ((key == 'X') || (key == 'x'))) {
            mode = tempmode;
            choix=1;
            startGame();
            alpha=255;
        }
        if ((mode == -1) && ((key == 'O') || (key == 'o'))) {
            mode = tempmode;
            choix=2;
            startGame();
            alpha=255;
        }
        if ((mode == -1) && (keyCode == BACKSPACE)) {
            mode = 0;
            alpha=255;
            drawMenu();
        }
        if ((mode > 0) && ((key == 'R') || (key == 'r'))) {
            if(choix==1)
                  choix=2;
            else 
                  choix=1;      
            startGame();
        }
        if ((mode > 0) && ((key == 'E') || (key == 'e'))) {
            mode =0;
            drawMenu();
        }   
    }    
}

void endOfGameText(int win){
   float xPosition = 140, yPosition = 450;
   stroke(255);
   textSize(20);

   if (win==1){
       text("Winner: Player 1", xPosition, yPosition-20);
    }
   if (win==2){
       text("Winner: Player 2", xPosition, yPosition-20);
    }
   if (win==0){
       fill(255);
       stroke(255);
       text("Game Over. No Winner", xPosition-35, yPosition-20);
    }
}

void playAgainButton(){
    fill(200);
    stroke(200);
    rect(x_button, y_button, w_button, h_button); 
    stroke(0);
    fill(0);
    text("PLAY AGAIN", x_button+15, y_button+30);
    fill(255);
}

void exitButton(){
    fill(200);
    stroke(0);
    rect(x_button, y_button+30, w_button, h_button); 
    stroke(0);
    fill(0);
    text("Exit", x_button+15, y_button+60);
    fill(255);
}

void replayButton(){
    fill(200);
    stroke(0);
    rect(x_button, y_button-20, w_button, h_button); 
    stroke(0);
    fill(0);
    text("Play Again", x_button+15, y_button+10);
    fill(255);
}