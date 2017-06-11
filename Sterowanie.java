package sterowanie.robot;

import android.view.MotionEvent;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;


public class Sterowanie extends ActionBarActivity
{

    Button  btnR, btnLP, btnLT, btnPP, btnPT, btnStop;
    String adres = null;
    private ProgressDialog postep;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean polaczenie = false;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        adres = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        setContentView(R.layout.activity_sterowanie);


        btnLP = (Button)findViewById(R.id.BtnLewoPrzod);
        btnLT = (Button)findViewById(R.id.BtnLewoTyl);
        btnPP = (Button)findViewById(R.id.BtnPrawoPrzod);
        btnPT = (Button)findViewById(R.id.BtnPrawoTyl);
        btnR = (Button)findViewById(R.id.BtnRoz);

        new ConnectBT().execute();

        btnLT.setOnTouchListener(new View.OnTouchListener() {

        @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("LTT".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Błąd");
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("LTN".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Błąd");
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:

                        if (btSocket!=null)
                        {
                            try
                            {
                                btSocket.getOutputStream().write("LTN".toString().getBytes());
                            }
                            catch (IOException e)
                            {
                                msg("Błąd");
                            }
                        }
                    break;
            }
            return false;
        }



    });


        btnLP.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("LPT".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_UP:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("LPN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_CANCEL:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("LPN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                }
                return false;
            }



        });


        btnPT.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PTT".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_UP:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PTN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_CANCEL:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PTN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                }
                return false;
            }



        });


        btnPP.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PPT".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_UP:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PPN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                    case MotionEvent.ACTION_CANCEL:

                            if (btSocket!=null)
                            {
                                try
                                {
                                    btSocket.getOutputStream().write("PPN".toString().getBytes());
                                }
                                catch (IOException e)
                                {
                                    msg("Błąd");
                                }
                            }
                        break;
                }
                return false;
            }



        });

        btnR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Rozlacz();
            }
        });

    }

    private void Rozlacz()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Błąd");}
        }
        finish();

    }





    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }




    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            postep = ProgressDialog.show(Sterowanie.this,"Łączenie...","Proszę czekać!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //
        {
            try
            {
                if (btSocket == null || !polaczenie)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(adres);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Łączenie nie powiodło się!");
                finish();
            }
            else
            {
                msg("Połączono.");
                polaczenie = true;
            }
            postep.dismiss();
        }
    }
}
