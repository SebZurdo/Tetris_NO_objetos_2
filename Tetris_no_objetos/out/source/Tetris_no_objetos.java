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

public class Tetris_no_objetos extends PApplet {

int M;
boolean fin = true;
int puntuacion;
int []posicionesx;
int []posicionesy;
int[] fllenas;
int cln;
int indp;
int [][][] colores;
int rc, gc, bc;
int rC = 0, gC = 0 , bC = 139;
int rI = 200, gI = 20 , bI = 60;
int rT = 139, gT = 69 , bT = 19;
int rL = 186;  
int gL = 85;   
int bL = 211;                           //Colores de cada uno de los tetronimos
int rOL = 255, gOL = 255 , bOL = 255;
int rS = 30, gS = 144 , bS = 255;
int rOS = 34, gOS = 139 , bOS = 34;
int rotaT = 2;
int limY,limY2;
float limX,limX2;
int crota,cfx,cfT;
int tColor;
int tRotation;
int cont,nivel;
int [] T = {464, 154, 184, 178};
int [] S = {180,408,180,408};
int [] OS = {306,240,306,240};          //Tetronimos dibujados en matrices 3x3, 2x2 y 4x4
int [] L = {480,402,120,294};
int [] OL = {312,420,456,150};
int [] I = {34952,61440,34952,61440};
int [] C = {15,15,15,15};
int r,g,b, orden;
int [] Forma;
int [] Forma2;
float cX = 181.7f;
int cY = 1;
float bbX;
int bX;
int bY;
boolean max = true;
int tope = 0;
public void setup(){
  
  posicionesx = new int[100];
  posicionesy = new int[100];
  colores = new int[10][20][3]; //Arreglo 3D para las colisiones
  fllenas = new int[20]; //Arreglo usado para analizar cuando una línea se llena
}

public void draw(){
  if(fin == true){
    background(150);
    fondo();
    textSize(20);
    fill(0);
    text("Puntuación",40,40);
    text(str(puntuacion),40,60);
    M = width/20;
    nivel = 1;
    stroke(192,192,192);
    pabajo();
    azarF();
    for (int i = 5; i < 16; ++i) {
      line(180,(i-5)*M,(width-(width/4)),(i-5)*M);
      line(180,(i+6)*M,(width-(width/4)),(i+6)*M);  //Cuadrícula
      line(i*M,0,i*M,height);
    }
    drawT();
    azarF();
    drawT();
    esh();
    suelo();
    paredT();
    fill(255);
    for (int g = 0; g < 20; ++g) {
      filafull(g);
    }
    for (int r = 0; r < 20; ++r) {
      if(fllenas[r] != 0){
        limpieza(r);
        puntuacion = puntuacion + 100;
      }
    }
    bbX = (cX-181.7f)/36;
    bX = PApplet.parseInt(bbX);
    if(cY == 1){
      bY = 0;
    }else{
      bY =(cY-1)/36;

    }
    gameover();
  }
}

public void azarF(){     //Función que genera un tetronimo al azar
  if(max == true){
    cX = 181.7f;
    tRotation = 0;
    cY = 1;
    bX = 0;
    bY = 0;
    orden = (int)random(7);
    switch(orden){
      case 0:
        Forma = C;
        r = rC;
        g = gC;
        b = bC;
        limY = 648;
        limY2 = 648;
        limX = 468.7f;
        limX2 = 468.7f;
        break;
    case 1:
        Forma = I;
        r = rI;
        g = gI;
        b = bI;
        limY = 684;
        limY2 = 576;
        limX = 504.7f;
        limX2 = 396.7f;
        break;
    case 2:
        Forma = T;
        r = rT;
        g = gT;
        b = bT;
        limY = 612;
        limY2 = 648;
        limX = 432.7f;
        limX2 = 469.7f;
        break;
    case 3:
        Forma = L;
        r = rL;
        g = gL;
        b = bL;
        limY = 612;
        limY2 = 648;
        limX = 432.7f;
        limX2 = 468.7f;
        break;
    case 4:
        Forma = OL;
        r = rOL;
        g = gOL;
        b = bOL;
        limY = 612;
        limY2 = 648;
        limX = 432.7f;
        limX2 = 468.7f;
        break;
    case 5:
        Forma = S;
        r = rS;
        g = gS;
        b = bS;
        limY = 648;
        limY2 = 612;
        limX = 468.7f;
        limX2 = 432.7f;
        break;
    case 6:
        Forma = OS;
        r = rOS;
        g = gOS;
        b = bOS;
        limY = 648;
        limY2 = 612;
        limX = 468.7f;
        limX2 = 432.7f;
        break;
    }
  }
  max = false;
  Forma2 = Forma;
}

public void drawT() { //Función que dibuja el tetronimo
  push();
  fill(r,g,b);
  if(Forma == I){
    for (int i = 0; i <= 15; i++) {
      if ((Forma[tRotation] & (1 << 15 - i)) != 0) {
        rect(((i % 4) * 143 / 4) + cX, (((i / 4) | 0) * 143 / 4) + cY , (143 / 4), (143 / 4));
      }
    }
  }else if(Forma == C){
    for (int i = 0; i <= 3; i++) {
      if ((Forma[tRotation] & (1 << 3 - i)) != 0) {
        rect(((i % 2) * 70 / 2) + cX, (((i / 2) | 0) * 70 / 2) + cY , (70 / 2), (70 / 2));
      }
    }
  }
  else{
    for (int i = 0; i <= 8; i++) {
      if ((Forma[tRotation] & (1 << 8 - i)) != 0) {
        rect(((i % 3) * 105 / 3) + cX, (((i / 3) | 0) * 105 / 3) + cY , (110 / 3), (110 / 3));
      }
    }
  }
 pop();
}

public void keyPressed() {     //Función para rotar y mover la figura (detectando límites)
  if (key == CODED) {
    if (keyCode == UP) {
      if(Forma ==T){
        if(tRotation == 1){
          mover("DER");
        }
      }
      if(Forma != C){
        if(Forma == I){
          if(cfx == 1){
            if(cX >= 433.7f){
              mover("IZQ");
              mover("IZQ");
              mover("IZQ");
            }
          }
          if(cfx == 0){
            if(cY > limY2){
              cY = limY2;
              tope = 1;
            }
            if(cX > limX){
              mover("IZQ");
            }
          }
        }else{
          if(cfx == 0){
            if(cX > limX){
              mover("IZQ");
            }
          }
          if(cfx == 1){
            if(cX > limX2){
              mover("IZQ");
            }
          }
        }
        tRotation ++;
        if(Forma == T){
          rotaT++;
        }
        crota++;
      }
    }
    tRotation = tRotation < 0 ? 3 : tRotation % 4;
  }
  if(keyCode == RIGHT){
    mover("DER");
    hitbox(bX,bY);
  }
  if(keyCode == LEFT){
    mover("IZQ");
    hitbox(bX,bY);
  }
  if(keyCode == DOWN){
    mover("DOWN");
    puntuacion = puntuacion + 1;
    hitbox(bX,bY);
  }
}

public void mover(String dir){          //Función para mover la figura
  if(limite(dir)){
    if(dir == "DER"){
      cX = cX+36;
    }
    if(dir == "IZQ"){
      cX = cX-36;
    }
    if(dir == "DOWN"){
      cY = cY + 36;
    }
  }
}


public boolean limite(String dir){         //Función para detectar límites y evitar que la figura se salga del tablero
  switch(dir){
    case "DER":
      if(Forma ==T){
        if(cfT ==0){
          if(tRotation == 1){
            if(cX > 397.7f){
              return false;
            }
          }else if(tRotation != 1){
            if(cX > 397.7f){
              return false;
            }
          }else{
            if(cX >limX2){
              return false;
            }
          }
        }else{
          if(cX>limX){
            return false;
          }
        }
      }else{
        if(cfx ==0){
          if(cX >limX2){
            return false;
          }
        }else{
          if(cX>limX){
            return false;
          }
        }
      }
      break;
    case "IZQ":
      if(Forma == T){
        if(tRotation == 1){
          if(cX < 181.7f){
            return false;
          }
        }else if(tRotation != 1){
          if(cX < 217.7f){
            return false;
          }
        }
      }else{
        if(cX<216.7f){
          return false;
        }
        break;
      }
    case "DOWN":
      if(cfx == 0){
        if(cY>limY){
          return false;
        }
      }else{
        if(cY>limY2){
          return false;
        }
      }
      break;
  }
  return true;
}

public void suelo(){         //Función que detecta cuando el tetronimo cae al suelo
  if((Forma != S) & Forma != OS){
    if(cfx ==0){
      if(cY >= limY){
        tope = 1;
        max = true;
        posicionesx[indp] = bX;
        posicionesy[indp] = bY;
        imprime(bX,bY);
        indp++;
      }
    }else{
      if(cY >= limY2){
        tope = 1;
        max = true;
        posicionesx[indp] = bX;
        posicionesy[indp] = bY;
        imprime(bX,bY);
        indp++;
      }
    }
  }else{
    if(cfx ==1){
      if(cY >= limY2){
        tope = 1;
        max = true;
        posicionesx[indp] = bX;
        posicionesy[indp] = bY;
        imprime(bX,bY);
        indp++;
      }
    }else{
      if(cY >= limY){
        tope = 1;
        max = true;
        posicionesx[indp] = bX;
        posicionesy[indp] = bY;
        imprime(bX,bY);
        indp++;
      }
    }
  }
}
public void pabajo(){          //Función que mueve el tetronimo hacia abajo cada cierto tiempo
  if(max == false){
    if(cont%(55-nivel) == 0){
      mover("DOWN");
      hitbox(bX,bY);
    }
    cont++;
  }
}

public boolean esh(){     //Función que sirve para determinar si algunos tetronimos están posicionados vertical u horizontalmente
  if(Forma != T){
    if(crota % 2 == 0){
      cfx = 1;
      return true;
    }
    cfx = 0;
    return false;
  }else{
    if(rotaT % 4==0){
      cfx = 1;
      return true;
    }
    cfx = 0;
    return false;
  }  
}

public boolean paredT(){        //Función para detectar la pared cuando el tetronimo es la T
  if((rotaT-1) % 4 ==0){
    cfT = 1;
    return true;
  }
  cfT = 0;
  return false;
}

public void fondo(){         //Creación del arreglo 3D
  for (int i = 0; i < 10; ++i) {
    for (int j = 0; j < 20; ++j) {
      rc = colores[i][j][0];
      gc = colores[i][j][1];
      bc = colores[i][j][2];
      fill(rc,gc,bc);
      rect((i*M)+180,j*M,M,M);
    
    }            
  }
}
public void imprime(int x,int y){ //Función que imprime el tetronimo al tocar el suelo
  y = y + 1;
  if(Forma == L){
    if(tRotation == 0){
      colores[x][y][0] = rL;
      colores[x][y][1] = gL;
      colores[x][y][2] = bL;
      colores[x+1][y][0] = rL;
      colores[x+1][y][1] = gL;
      colores[x+1][y][2] = bL;
      colores[x+2][y][0] = rL;
      colores[x+2][y][1] = gL;
      colores[x+2][y][2] = bL;
      colores[x][y+1][0] = rL;
      colores[x][y+1][1] = gL;
      colores[x][y+1][2] = bL;
    }else if(tRotation == 1){
      colores[x][y][0] = rL;
      colores[x][y][1] = gL;
      colores[x][y][2] = bL;
      colores[x+1][y][0] = rL;
      colores[x+1][y][1] = gL;
      colores[x+1][y][2] = bL;
      colores[x+1][y+1][0] = rL;
      colores[x+1][y+1][1] = gL;
      colores[x+1][y+1][2] = bL;
      colores[x+1][y+2][0] = rL;
      colores[x+1][y+2][1] = gL;
      colores[x+1][y+2][2] = bL;
    }else if(tRotation == 2){
      colores[x][y+1][0] = rL;
      colores[x][y+1][1] = gL;
      colores[x][y+1][2] = bL;
      colores[x+1][y+1][0] = rL;
      colores[x+1][y+1][1] = gL;
      colores[x+1][y+1][2] = bL;
      colores[x+2][y+1][0] = rL;
      colores[x+2][y+1][1] = gL;
      colores[x+2][y+1][2] = bL;
      colores[x+2][y][0] = rL;
      colores[x+2][y][1] = gL;
      colores[x+2][y][2] = bL;
    }else{
      colores[x][y][0] = rL;
      colores[x][y][1] = gL;
      colores[x][y][2] = bL;
      colores[x][y+1][0] = rL;
      colores[x][y+1][1] = gL;
      colores[x][y+1][2] = bL;
      colores[x][y+2][0] = rL;
      colores[x][y+2][1] = gL;
      colores[x][y+2][2] = bL;
      colores[x+1][y+2][0] = rL;
      colores[x+1][y+2][1] = gL;
      colores[x+1][y+2][2] = bL;
    }
  }else if(Forma == OL){
    if(tRotation == 0){
      rect(cX,cY,36,36);
      colores[x][y][0] = rOL;
      colores[x][y][1] = gOL;
      colores[x][y][2] = bOL;
      colores[x][y+1][0] = rOL;
      colores[x][y+1][1] = gOL;
      colores[x][y+1][2] = bOL;
      colores[x+1][y+1][0] = rOL;
      colores[x+1][y+1][1] = gOL;
      colores[x+1][y+1][2] = bOL;
      colores[x+2][y+1][0] = rOL;
      colores[x+2][y+1][1] = gOL;
      colores[x+2][y+1][2] = bOL;
    }else if(tRotation == 1){
      colores[x][y][0] = rOL;
      colores[x][y][1] = gOL;
      colores[x][y][2] = bOL;
      colores[x+1][y][0] = rOL;
      colores[x+1][y][1] = gOL;
      colores[x+1][y][2] = bOL;
      colores[x][y+1][0] = rOL;
      colores[x][y+1][1] = gOL;
      colores[x][y+1][2] = bOL;
      colores[x][y+2][0] = rOL;
      colores[x][y+2][1] = gOL;
      colores[x][y+2][2] = bOL;
    }else if(tRotation == 2){
      colores[x][y][0] = rOL;
      colores[x][y][1] = gOL;
      colores[x][y][2] = bOL;
      colores[x+1][y][0] = rOL;
      colores[x+1][y][1] = gOL;
      colores[x+1][y][2] = bOL;
      colores[x+2][y][0] = rOL;
      colores[x+2][y][1] = gOL;
      colores[x+2][y][2] = bOL;
      colores[x+2][y+1][0] = rOL;
      colores[x+2][y+1][1] = gOL;
      colores[x+2][y+1][2] = bOL;
    }else{
      colores[x][y+2][0] = rOL;
      colores[x][y+2][1] = gOL;
      colores[x][y+2][2] = bOL;
      colores[x+1][y+2][0] = rOL;
      colores[x+1][y+2][1] = gOL;
      colores[x+1][y+2][2] = bOL;
      colores[x+1][y+1][0] = rOL;
      colores[x+1][y+1][1] = gOL;
      colores[x+1][y+1][2] = bOL;
      colores[x+1][y][0] = rOL;
      colores[x+1][y][1] = gOL;
      colores[x+1][y][2] = bOL;
    }

  }else if(Forma ==S){
    if(tRotation == 0 || tRotation == 2){
      colores[x][y+1][0] = rS;
      colores[x][y+1][1] = gS;
      colores[x][y+1][2] = bS;
      colores[x][y+2][0] = rS;
      colores[x][y+2][1] = gS;
      colores[x][y+2][2] = bS;
      colores[x+1][y][0] = rS;
      colores[x+1][y][1] = gS;
      colores[x+1][y][2] = bS;
      colores[x+1][y+1][0] = rS;
      colores[x+1][y+1][1] = gS;
      colores[x+1][y+1][2] = bS;
    }else{
      colores[x][y][0] = rS;
      colores[x][y][1] = gS;
      colores[x][y][2] = bS;
      colores[x+1][y][0] = rS;
      colores[x+1][y][1] = gS;
      colores[x+1][y][2] = bS;
      colores[x+1][y+1][0] = rS;
      colores[x+1][y+1][1] = gS;
      colores[x+1][y+1][2] = bS;
      colores[x+2][y+1][0] = rS;
      colores[x+2][y+1][1] = gS;
      colores[x+2][y+1][2] = bS;
    }
  }else if(Forma ==OS){
    if(tRotation == 0 || tRotation == 2){
      colores[x][y][0] = rOS;
      colores[x][y][1] = gOS;
      colores[x][y][2] = bOS;
      colores[x][y+1][0] = rOS;
      colores[x][y+1][1] = gOS;
      colores[x][y+1][2] = bOS;
      colores[x+1][y+1][0] = rOS;
      colores[x+1][y+1][1] = gOS;
      colores[x+1][y+1][2] = bOS;
      colores[x+1][y+2][0] = rOS;
      colores[x+1][y+2][1] = gOS;
      colores[x+1][y+2][2] = bOS;
    }else{
      colores[x][y+1][0] = rOS;
      colores[x][y+1][1] = gOS;
      colores[x][y+1][2] = bOS;
      colores[x+1][y+1][0] = rOS;
      colores[x+1][y+1][1] = gOS;
      colores[x+1][y+1][2] = bOS;
      colores[x+1][y][0] = rOS;
      colores[x+1][y][1] = gOS;
      colores[x+1][y][2] = bOS;
      colores[x+2][y][0] = rOS;
      colores[x+2][y][1] = gOS;
      colores[x+2][y][2] = bOS;
    }
  }else if(Forma == I){
    if(tRotation == 0 || tRotation == 2){
      colores[x][y][0] = rI;
      colores[x][y][1] = gI;
      colores[x][y][2] = bI;
      colores[x][y+1][0] = rI;
      colores[x][y+1][1] = gI;
      colores[x][y+1][2] = bI;
      colores[x][y+2][0] = rI;
      colores[x][y+2][1] = gI;
      colores[x][y+2][2] = bI;
      colores[x][y+3][0] = rI;
      colores[x][y+3][1] = gI;
      colores[x][y+3][2] = bI;
    }else{
      colores[x][y][0] = rI;
      colores[x][y][1] = gI;
      colores[x][y][2] = bI;
      colores[x+1][y][0] = rI;
      colores[x+1][y][1] = gI;
      colores[x+1][y][2] = bI;
      colores[x+2][y][0] = rI;
      colores[x+2][y][1] = gI;
      colores[x+2][y][2] = bI;
      colores[x+3][y][0] = rI;
      colores[x+3][y][1] = gI;
      colores[x+3][y][2] = bI;
    }
  }else if(Forma == T){
    if(tRotation == 0){
      colores[x][y+1][0] = rT;
      colores[x][y+1][1] = gT;
      colores[x][y+1][2] = bT;
      colores[x+1][y+1][0] = rT;
      colores[x+1][y+1][1] = gT;
      colores[x+1][y+1][2] = bT;
      colores[x+1][y+2][0] = rT;
      colores[x+1][y+2][1] = gT;
      colores[x+1][y+2][2] = bT;
      colores[x+2][y+1][0] = rT;
      colores[x+2][y+1][1] = gT;
      colores[x+2][y+1][2] = bT;
    }else if(tRotation == 1){
      colores[x+1][y][0] = rT;
      colores[x+1][y][1] = gT;
      colores[x+1][y][2] = bT;
      colores[x+1][y+1][0] = rT;
      colores[x+1][y+1][1] = gT;
      colores[x+1][y+1][2] = bT;
      colores[x+1][y+2][0] = rT;
      colores[x+1][y+2][1] = gT;
      colores[x+1][y+2][2] = bT;
      colores[x+2][y+1][0] = rT;
      colores[x+2][y+1][1] = gT;
      colores[x+2][y+1][2] = bT;
    }else if(tRotation == 2){
      colores[x+1][y][0] = rT;
      colores[x+1][y][1] = gT;
      colores[x+1][y][2] = bT;
      colores[x+1][y+1][0] = rT;
      colores[x+1][y+1][1] = gT;
      colores[x+1][y+1][2] = bT;
      colores[x][y+1][0] = rT;
      colores[x][y+1][1] = gT;
      colores[x][y+1][2] = bT;
      colores[x+2][y+1][0] = rT;
      colores[x+2][y+1][1] = gT;
      colores[x+2][y+1][2] = bT;
    }else{
      colores[x+1][y][0] = rT;
      colores[x+1][y][1] = gT;
      colores[x+1][y][2] = bT;
      colores[x+1][y+1][0] = rT;
      colores[x+1][y+1][1] = gT;
      colores[x+1][y+1][2] = bT;
      colores[x][y+1][0] = rT;
      colores[x][y+1][1] = gT;
      colores[x][y+1][2] = bT;
      colores[x+1][y+2][0] = rT;
      colores[x+1][y+2][1] = gT;
      colores[x+1][y+2][2] = bT;
    }
  }else{
    colores[x][y][0] = rC;
    colores[x][y][1] = gC;
    colores[x][y][2] = bC;
    colores[x+1][y][0] = rC;
    colores[x+1][y][1] = gC;
    colores[x+1][y][2] = bC;
    colores[x][y+1][0] = rC;
    colores[x][y+1][1] = gC;
    colores[x][y+1][2] = bC;
    colores[x+1][y+1][0] = rC;
    colores[x+1][y+1][1] = gC;
    colores[x+1][y+1][2] = bC;
  }
  crota = 0;
  rotaT = 2;
}




public void hitbox(int x,int y){   //Función que imprime el tetronimo cuando detecta a otra figura debajo
  if(max == false){
    if(Forma == L){
      if(tRotation == 0){
        if(bY < 18){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+1][0] != 0 || colores[x+1][y+1][1] != 0 || colores[x+1][y+1][2] != 0) || (colores[x+2][y+1][0] != 0 || colores[x+2][y+1][1] != 0 || colores[x+2][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else if(tRotation == 1){
        if(bY< 17){
          if((colores[x+1][y+3][0] != 0 || colores[x+1][y+3][1] != 0 || colores[x+1][y+3][2] != 0) || (colores[x][y+1][0] != 0 || colores[x][y+1][1] != 0 || colores[x][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else if(tRotation == 2){
        if(bY<18){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+2][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+2][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
        
      }else{
        if(bY<17){
          if((colores[x][y+3][0] != 0 || colores[x][y+3][1] != 0 || colores[x][y+3][2] != 0) || (colores[x+1][y+3][0] != 0 || colores[x+1][y+3][1] != 0 || colores[x+1][y+3][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }
    }else if(Forma == OL){
      if(tRotation == 0){
        if(bY < 17){
          if(colores[x][y+3][0] != 0 || colores[x][y+3][1] != 0 || colores[x][y+3][2] != 0){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY);
            indp++;

          }else if((colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+1][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+2][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else if(tRotation == 1){
        if(bY< 17){
          if((colores[x][y+3][0] != 0 || colores[x][y+3][1] != 0 || colores[x][y+3][2] != 0) || (colores[x+1][y+1][0] != 0 || colores[x+1][y+1][1] != 0 || colores[x+1][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else if(tRotation == 2){
        if(bY<18){
          if((colores[x][y+1][0] != 0 || colores[x][y+1][1] != 0 || colores[x][y+1][2] != 0) || (colores[x+1][y+1][0] != 0 || colores[x+1][y+1][1] != 0 || colores[x+1][y+1][2] != 0) || (colores[x+2][y+2][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+2][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
        
      }else{
        if(bY<17){
          if((colores[x][y+3][0] != 0 || colores[x][y+3][1] != 0 || colores[x][y+3][2] != 0) || (colores[x+1][y+3][0] != 0 || colores[x+1][y+3][1] != 0 || colores[x+1][y+3][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }

        }
      }

    }else if(Forma ==S){
      if(tRotation == 0 || tRotation == 2){
        if(bY <17){
          if((colores[x][y+3][0] != 0 || colores[x][y+3][1] != 0 || colores[x][y+3][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else{
        if(bY < 18){
          if((colores[x][y+1][0] != 0 || colores[x][y+1][1] != 0 || colores[x][y+1][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+2][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+2][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }
    }else if(Forma ==OS){
      if(tRotation == 0 || tRotation == 2){
        if(bY <17){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+3][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else{
        if(bY < 18){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+1][0] != 0 || colores[x+2][y+1][1] != 0 || colores[x+2][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }
    }else if(Forma == I){
      if(tRotation == 0 || tRotation == 2){
        if(bY <19){
          if(colores[x][y+4][0] != 0 || colores[x][y+4][1] != 0 || colores[x][y+4][2] != 0){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else{
        if(bY <18){
          if((colores[x][y+1][0] != 0 || colores[x][y+1][1] != 0 || colores[x][y+1][2] != 0) || (colores[x+1][y+1][0] != 0 || colores[x+1][y+1][1] != 0 || colores[x+1][y+1][2] != 0) || (colores[x+2][y+1][0] != 0 || colores[x+2][y+1][1] != 0 || colores[x+2][y+1][2] != 0) || (colores[x+3][y+1][0] != 0 || colores[x+3][y+1][1] != 0 || colores[x+3][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }
    }else if(Forma == T){
      if(tRotation == 0){
        if(bY<18){
          if((colores[x][y+1][0] != 0 || colores[x][y+1][1] != 0 || colores[x][y+1][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+1][0] != 0 || colores[x+2][y+1][1] != 0 || colores[x+2][y+1][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-2);
            indp++;
          }
        }
      }else if(tRotation == 1){
        if(bY < 18){
          if((colores[x+1][y+3][0] != 0 || colores[x+1][y+3][1] != 0 || colores[x+1][y+3][2] != 0) || (colores[x+2][y+2][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+1][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else if(tRotation == 2){
        if(bY<19){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0) || (colores[x+2][y+2][0] != 0 || colores[x+2][y+2][1] != 0 || colores[x+2][y+2][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }else{
        if(bY < 18){
          if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+3][0] != 0 || colores[x+1][y+3][1] != 0 || colores[x+1][y+3][2] != 0)){
            max = true;
            posicionesx[indp] = bX;
            posicionesy[indp] = bY;
            imprime(bX,bY-1);
            indp++;
          }
        }
      }
    }else{
      if(bY < 18){
        if((colores[x][y+2][0] != 0 || colores[x][y+2][1] != 0 || colores[x][y+2][2] != 0) || (colores[x+1][y+2][0] != 0 || colores[x+1][y+2][1] != 0 || colores[x+1][y+2][2] != 0)){
          max = true;
          posicionesx[indp] = bX;
          posicionesy[indp] = bY;
          imprime(bX,bY-1);
          indp++;
        }  
      }
    }
  }
}

public void limpieza(int yei){   //Función que limpia líneas completas al estar llenas
  for (int i = 0; i < 10; ++i) {
    colores[i][yei][0] = 0;
    colores[i][yei][1] = 0;
    colores[i][yei][2] = 0;
  }
  for (int j = yei; j > 1; --j){
    for (int i = 0; i < 10; ++i) {
      colores[i][j][0] = colores[i][j-1][0];
      colores[i][j][1] = colores[i][j-1][1];
      colores[i][j][2] = colores[i][j-1][2];
    }
  }
}

public boolean filafull(int fila){  //booleano útil para determinar si una fila está llena o no
  if(colores[0][fila][2] != 0 && colores[1][fila][2] != 0 && colores[2][fila][2] != 0 && colores[3][fila][2] != 0 && colores[4][fila][2] != 0 && colores[5][fila][2] != 0 && colores[6][fila][2] != 0 && colores[7][fila][2] != 0 && colores[8][fila][2] != 0 && colores[9][fila][2] != 0){
    fllenas[fila] = 1;
    return true;
  }
  fllenas[fila] = 0;
  return false;
}

public void gameover(){ //Función que se ejecuta una vez termina el juego (No se permite la generación de más fichas)
  if(colores[0][0][2] != 0 || colores[1][0][2] != 0 || colores[2][0][2] != 0 || colores[3][0][2] != 0 || colores[4][0][2] != 0 || colores[5][0][2] != 0 || colores[6][0][2] != 0 || colores[7][0][2] != 0 || colores[8][0][2] != 0 || colores[9][0][2] != 0){
    fin = false;
    background(255);
    textSize(80);
    stroke(135);
    fill(0);
    text("Game Over",150,360);
    textSize(60);
    text("Puntuación =  "+str(puntuacion),120,420);
  }
}
  public void settings() {  size(720,720); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Tetris_no_objetos" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
