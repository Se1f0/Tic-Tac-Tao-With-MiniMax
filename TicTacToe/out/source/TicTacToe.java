import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TicTacToe extends PApplet {

Square[][] grille;
int row, col, spaces,alpha = 255;
float x, y, w, h;
int player = 1;
boolean gameOver,gameOver2;
int winner,winner2;
boolean play;
int mode = 0, tempmode=0;  
int choix=0; 
boolean canDrawArc=false;
float startangle=0;
float endangle=0;
PImage bg;
PFont font;

float arcx,arcy,arcw,arch;
// play again button
float w_button = 150;
float h_button = 40;
float x_button = (425/2) - (w_button/2);
float y_button = 475;

public void setup() {
    font = createFont("PermanentMarker-Regular.ttf",32);
    textFont(font);
    bg = loadImage("bg.png");
    
    drawMenu();
}

public void draw() {
 if(!canDrawArc){
    if (mode > 0) {
        checkGameOver();
        if (gameOver == false){
          
            if (mode == 2 && player == 2) {
               
                bestIaTurn(); 
            }
            if(!canDrawArc){ 
              background(200);
              drawGride();
            }
        }
        else {
            play = false;
            if (winner == 1){
                background(23,206,244);
                //background(0,0,255);
            }
            else if (winner == 2){
                background(254,48,48);
                //background(255,0,0);
            }
            else{
                background(0);
            }
            drawGride();
            endOfGameText(winner);
            if(winner==1 || winner==2)
            drawLine(winner);
        }
        
    }
  }else{
    if(startangle<=endangle){
        fill(16,7,92);
        drawGride(); 
        noStroke();
        fill(16,7,92);
        arc(arcx, arcy, arcw, arch,startangle,endangle);
        stroke(0);
        delay(70);
        startangle=startangle+PI/2;
    }else{
            canDrawArc=false;
            endangle=0;
            startangle=0;
        }
    }
}

class Square{
    float xPos, yPos, s_width, s_height;
    int value;                
    
    Square(float x, float y, float w, float h){
        xPos = x;
        yPos = y;
        s_width = w;
        s_height = h;
        value = 0;
    }

    public void display(){
        fill(16,7,92);
        // stroke(63,72,204);
        stroke(47,56,176);
        strokeWeight(2);
        rect(xPos, yPos, s_width, s_height);
        stroke(0);
        strokeWeight(1);   
      
        if(value == 1){
            drawCross();
        }
        else if(value == 2){
            drawCircle();
        }    
    }
    public void click(float m_X, float m_Y,int mode){
        float mX = m_X;
        float mY = m_Y;
        if(mX > xPos && mX < xPos + s_width && mY > yPos && mY < yPos + s_height){
            if(value == 0 && play == true){
                if(player==1){
                    value = 1;
                    background(200);
                    player = 2;
                    drawGride();
                 
                }
                else{

                    if (mode == 1) {
                 canDrawArc=true;
                    endangle=2*PI;
        arcx=xPos+(s_width/2);
        arcy=yPos+(s_height/2);
        arcw=s_width-25-5-10-5+20;
        arch=s_height-25-5-10-5+20;
                        value = 2;
                        player = 1;   
                    }
                }
                spaces--;
            }
        }   
    }

    public void drawCross(){
        stroke(23,206,244);
        strokeWeight(10);
        line(xPos+20, yPos+20, xPos+(s_width-20), yPos+(s_height-20));
        line(xPos+20, yPos+(s_height-20), xPos+(s_width-20), yPos+20);
        stroke(255);
        strokeWeight(5);
        line(xPos+20, yPos+20, xPos+(s_width-20), yPos+(s_height-20));
        line(xPos+20, yPos+(s_height-20), xPos+(s_width-20), yPos+20);
        stroke(0);
        strokeWeight(1);
    }

    public void drawCircle(){
        
     
       noStroke();


        fill(254,48,48);
        ellipse(xPos+(s_width/2), yPos+(s_height/2), s_width-25, s_height-25);

     
        fill(255);
        ellipse(xPos+(s_width/2), yPos+(s_height/2), s_width-25-5, s_height-25-5);
        
     
        fill(254,48,48);
        ellipse(xPos+(s_width/2), yPos+(s_height/2), s_width-25-5-10, s_height-25-5-10);

        fill(16,7,92);
        ellipse(xPos+(s_width/2), yPos+(s_height/2), s_width-25-5-10-5, s_height-25-5-10-5);

        stroke(0);

        strokeWeight(1);
    }

    public int getValue(){
        return value;
    }
}

class Cordonee {
    private int i,j;

    Cordonee(int i,int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return this.i;
    }

    public int getJ() {
        return this.j;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void affiche() {
        println("{i:"+this.i+",j:"+this.j+"}");
    }
}
public void startGame(){
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

public void checkGameOver(){
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

public void checkWinner(){
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


public void bestIaTurn() {
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

public int minmax(boolean isMax,int niveau) {
   
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

public void drawMenu() {
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

public void drawChoose() {
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

public void drawGride() {
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

public void drawLine(int w){
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


public void mousePressed() {
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

public void keyPressed() {
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

public void endOfGameText(int win){
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

public void playAgainButton(){
    fill(200);
    stroke(200);
    rect(x_button, y_button, w_button, h_button); 
    stroke(0);
    fill(0);
    text("PLAY AGAIN", x_button+15, y_button+30);
    fill(255);
}

public void exitButton(){
    fill(200);
    stroke(0);
    rect(x_button, y_button+30, w_button, h_button); 
    stroke(0);
    fill(0);
    text("Exit", x_button+15, y_button+60);
    fill(255);
}

public void replayButton(){
    fill(200);
    stroke(0);
    rect(x_button, y_button-20, w_button, h_button); 
    stroke(0);
    fill(0);
    text("Play Again", x_button+15, y_button+10);
    fill(255);
}
  public void settings() {  size (425,550); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TicTacToe" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
