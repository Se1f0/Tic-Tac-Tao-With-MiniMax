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

void setup() {
    font = createFont("PermanentMarker-Regular.ttf",32);
    textFont(font);
    bg = loadImage("bg.png");
    size (425,550);
    drawMenu();
}

void draw() {
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

