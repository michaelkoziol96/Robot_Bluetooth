//definicja pinów
//połączenia między pinami Arduino i mostka L293D
#define enableLewo 5    //1 L293D
#define enablePrawo 6   //9 L293D
#define in1Lewo 7       //2 L293D
#define in2Lewo 8       //7 L293D
#define in1Prawo 9      //10 L293D
#define in2Prawo 10     //15 L293D
                        //3 L293D --> lewy silnik zworka tył
                        //4,5 L293D --> GND
                        //6 L293D --> lewy silnik zworka przod
                        //8 L293D --> +9V
                        //11 L293D --> prawy silnik zworka tył
                        //12,13  L293D --> GND
                        //14 L293D --> prawy silnik zworka przód
                        //16 L293D --> +5V

int predkoscPrawo, predkoscLewo;
String  odbior, lewySilnik, prawySilnik;
char command;
boolean lt,lp, pp, pt;

//setup - funkcja wykonywana przy uruchomieniu
void setup() 
{
   //rozpoczęcie komunikacji przez UART
    Serial.begin(9600); 
    
   //redukcja mechanicznej różnicy w prędkościach obrotowych silników
    predkoscLewo=240;
    predkoscPrawo=255;
    
    //ustawienia początkowe pinów
    pinMode(enablePrawo, OUTPUT);
    pinMode(enableLewo, OUTPUT);
    pinMode(in1Lewo, OUTPUT);
    pinMode(in2Lewo, OUTPUT);
    pinMode(in1Prawo, OUTPUT);
    pinMode(in2Prawo, OUTPUT);
  
    analogWrite(enableLewo, 0);
    analogWrite(enablePrawo, 0);
    
    digitalWrite(in1Lewo, LOW);
    digitalWrite(in2Lewo, LOW);
    digitalWrite(in1Prawo, LOW);
    digitalWrite(in2Prawo, LOW);
}

//loop - pętla nieskończona
void loop()
{
  
    //sprawdzanie czy komunikacja dostępna
    if (Serial.available() > 0)
        {
          odbior = "";
        }
    //odczytywanie danych z portu szeregowego
      while(Serial.available() > 0)

      {
          command = ((byte)Serial.read());//odczytanie danych z portu szeregowego
          if (command == ':')
             {
                 break;
              }
         else
        {
            odbior += command;
         }
    delay(1);
    }
      odbierz();
      logika();
      delay(1);
      stopPrawo();
      stopLewo();
    
    }

//zdefiniowane funkcje programu
//
 //lewy silnik kręci się w przód   
 void lewoPrzod()
  {
    analogWrite(enableLewo, predkoscLewo);
    digitalWrite(in1Lewo, HIGH);
    digitalWrite(in2Lewo, LOW);
  }
  
 //lewy silnik kręci się w tył
void lewoTyl()
  {
     analogWrite(enableLewo, predkoscLewo);
     digitalWrite(in1Lewo, LOW);
     digitalWrite(in2Lewo, HIGH);
  }
 //prawy silnik kręci sięw przód 
void prawoPrzod ()
  {
      analogWrite(enablePrawo, predkoscPrawo);
      digitalWrite(in1Prawo, HIGH);
      digitalWrite(in2Prawo, LOW);
  }
 //prawy silnik kręci się w tył 
void prawoTyl ()
    {
      analogWrite(enablePrawo, predkoscPrawo);
      digitalWrite(in1Prawo, LOW);
      digitalWrite(in2Prawo, HIGH);
    }
//zatrzymanie lewego silnika 
void stopLewo()
  {
 
    digitalWrite(in1Lewo, LOW);
    digitalWrite(in2Lewo, LOW);
  
  }
//zatrzymanie prawego silnika 
void stopPrawo()
  {

      digitalWrite(in1Prawo, LOW);
      digitalWrite(in2Prawo, LOW);

  }

   
void odbierz()
  {
    //określenie, co zostało przesłane przez Bluetooth
    //przypisanie wartości zmiennym logicznym kontrolującym silniki
    if  (odbior=="LPT")
    {
      lp=true;
    }
    else if  (odbior=="LPN")
    {
       lp=false;
    }
    else if  (odbior=="LTT")
    {
       lt=true;
    }
    else if  (odbior=="LTN")
    {
       lt=false;
    }
    else if  (odbior=="PPT")
    {
      pp=true;
    }
    else if  (odbior=="PPN")
    {
      pp=false;
    }
    else if  (odbior=="PTT")
    {
       pt=true;
    }
    else if  (odbior=="PTN")
    {
      
     pt=false;
    }
    else 
    {
     pp=false; 
     pt=false;
     lp=false;
     lt=false;
    }
}
void logika()
{
  
  //wywołanie odpowiednich funkcji sreturących silnikami
        if(lp==true && lt==false )
        {
            lewoPrzod();
        }
        else if (lp==true && lt==true)
        {
          lewoPrzod();
          lt=false;
        }
        else if (lp==false && lt==true)
        {
            lewoTyl();
        }
        else if (lp==false && lt==false)
        {
           stopLewo();
        }
   
        if(pp==true && pt==false)
        {
           prawoPrzod();
        }
        else if(pp==true && pt==true)
        {
           prawoPrzod();
           pt=false;
        }
        else if (pp==false && pt==true)
        {
            prawoTyl();
        }
        else if (pp==false && pt==false)
        {
            stopPrawo();
        }
        else
        {
         stopPrawo();
         stopLewo(); 
        }
    
}



 


