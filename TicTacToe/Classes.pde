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

    void display(){
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
    void click(float m_X, float m_Y,int mode){
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

    void drawCross(){
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

    void drawCircle(){
        
     
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

    int getValue(){
        return value;
    }
}

class Cordonee {
    private int i,j;

    Cordonee(int i,int j) {
        this.i = i;
        this.j = j;
    }

    int getI() {
        return this.i;
    }

    int getJ() {
        return this.j;
    }

    void setI(int i) {
        this.i = i;
    }

    void setJ(int j) {
        this.j = j;
    }

    void affiche() {
        println("{i:"+this.i+",j:"+this.j+"}");
    }
}