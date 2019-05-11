package com.dicText;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DicTextActivity extends Activity {
    /** Called when the activity is first created. */
	LinearLayout ll,drawer;
	Button dic,dicp,dicm,pause,info,next,control,rev,fwd;
	TextView tv;
	TextToSpeech tts;
	String tdata,data="",a[],d[],display;
	float speed;
	EditText text,gap,from;
	StringTokenizer st;
	int n,pos,sec,count,i,tl,num,stop,log,page=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        speed=1.0f;
        ll=(LinearLayout)findViewById(R.id.home);
        drawer=(LinearLayout)findViewById(R.id.drawer);
        dic=(Button)findViewById(R.id.dic);
        dicp=(Button)findViewById(R.id.dicp);
        dicm=(Button)findViewById(R.id.dicm);
        info=(Button)findViewById(R.id.info);
        next=(Button)findViewById(R.id.next);
        control=(Button)findViewById(R.id.control);
        pause=(Button)findViewById(R.id.pause);
        rev=(Button)findViewById(R.id.rev);
        fwd=(Button)findViewById(R.id.fwd);
        from=(EditText)findViewById(R.id.from);
        text=(EditText)findViewById(R.id.dictate);
        gap=(EditText)findViewById(R.id.gap);
        tv=(TextView)findViewById(R.id.status);
        next.setVisibility(View.GONE);
        drawer.setVisibility(View.GONE);
        tts=new TextToSpeech(this,new TextToSpeech.OnInitListener() {
			
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if(!(status==TextToSpeech.ERROR)){
				tts.setLanguage(Locale.ENGLISH);
				}
			}
		});
        info.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dic.setVisibility(View.GONE);
				dicp.setVisibility(View.GONE);
				dicm.setVisibility(View.GONE);
				pause.setVisibility(View.GONE);
				from.setVisibility(View.GONE);
				text.setVisibility(View.GONE);
				gap.setVisibility(View.GONE);
				tv.setVisibility(View.GONE);
				control.setVisibility(View.GONE);
				ll.setBackgroundResource(R.drawable.page1);
				next.setVisibility(View.VISIBLE);
			}
		});
        next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				page++;
				pages();
				if(page==9)
				{
					page=1;
					dic.setVisibility(View.VISIBLE);
					dicp.setVisibility(View.VISIBLE);
					dicm.setVisibility(View.VISIBLE);
					pause.setVisibility(View.VISIBLE);
					from.setVisibility(View.VISIBLE);
					text.setVisibility(View.VISIBLE);
					gap.setVisibility(View.VISIBLE);
					tv.setVisibility(View.VISIBLE);
					control.setVisibility(View.VISIBLE);
					next.setVisibility(View.GONE);
				}
			}
		});
        dic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String temp="",fs="";
				display="";
				tdata=text.getText().toString();
				if(tdata.charAt(0)=='~')
				{
					text.setText(data);
					tdata=text.getText().toString();
				}
				data="";
				i=0;
				count=0;
				log=0;
				stop=0;
				if(pos==n)
					pos=0;
				try
				{
					pos=Integer.parseInt(from.getText().toString())-1;
				}
				catch(Exception e)
			    {
			    	Toast.makeText(getApplicationContext(), "Enter Line Number from where to begin Dictating",Toast.LENGTH_LONG).show();
			    	pos=0;
			    }
				try
				{
					sec=Integer.parseInt(gap.getText().toString())*1000;
				}
				catch(Exception e)
			    {
			    	Toast.makeText(getApplicationContext(), "Enter Number of Seconds",Toast.LENGTH_LONG).show();
			    }
				tdata=tdata.trim();
				while(log<(tdata.length()))
				{
					char ch=tdata.charAt(log);
					if(ch==' ')
					{
						if(tdata.charAt(log+1)==' ')
							log++;
						else
						{
							data=data+ch;
							log++;
						}
					}
					else
					{
						data=data+ch;
						log++;
					}
				}
				int l=data.length();
				if(l%25==0)
					n=l/25;
				else
					n=(l/25)+1;
				a=new String[n];
				d=new String [n];
				st= new StringTokenizer(data);
				num=st.countTokens();
				while(count<num)
				{
					if(i==n-1)
					{
						temp=temp+" "+st.nextToken();
						count++;
					}
					else
					{
						temp=temp+" "+st.nextToken();
						count++;
						tl=temp.length();
						if(tl>25)
						{
							d[i]=temp;
							temp=temp+" ";
							fs=temp;
							temp="";
							for(int j=0;j<tl;j++)
							{
								char ch=fs.charAt(j);
								if(ch=='.')
								{
									char ch1=fs.charAt(j-1);
									char ch2=fs.charAt(j+1);
									if(!(Character.isDigit(ch1)&& Character.isDigit(ch2)))
									{
										temp=temp+" full stop. ";
									}
								}
								else
									temp=temp+ch;
							}
							temp=temp.replaceAll(":"," colon ");
							temp=temp.replaceAll(","," comma, ");
							temp=temp.replaceAll("!"," exclaimation mark! ");
							temp=temp.replaceAll("\\?"," question mark? ");
							temp=temp.replaceAll(";"," semi colon; ");
							temp=temp.replaceAll("-"," hyphen ");
							temp=temp.replaceAll("\\("," open bracket ");
							temp=temp.replaceAll("\\)"," close bracket ");
							temp=temp.replaceAll("\\{"," open curly bracket ");
							temp=temp.replaceAll("\\}"," close curly bracket ");
							temp=temp.replaceAll("\\["," open square bracket ");
							temp=temp.replaceAll("\\]"," close square bracket ");
							temp=temp.replaceAll("\""," double inverted comma ");
							temp=temp.replaceAll("/"," slash ");
							temp=temp.replaceAll("\\*"," asterisk ");
							a[i]=temp;
							i++;
							temp="";
						}
					}
				}
				if(temp.equals(""))
					n--;
				else
				{
					d[n-1]=temp;
					tl=temp.length();
					temp=temp+" ";
					fs=temp;
					temp="";
					for(int j=0;j<tl;j++)
					{
						char ch=fs.charAt(j);
						if(ch=='.')
						{
							char ch1=fs.charAt(j-1);
							char ch2=fs.charAt(j+1);
							if(!(Character.isDigit(ch1)&& Character.isDigit(ch2)))
							{
								temp=temp+" full stop. ";
							}
						}
						else
							temp=temp+ch;
					}
					temp=temp.replaceAll(":"," colon ");
					temp=temp.replaceAll(","," comma, ");
					temp=temp.replaceAll("!"," exclaimation mark! ");
					temp=temp.replaceAll("\\?"," question mark? ");
					temp=temp.replaceAll(";"," semi colon; ");
					temp=temp.replaceAll("-"," hyphen ");
					temp=temp.replaceAll("\\("," open bracket ");
					temp=temp.replaceAll("\\)"," close bracket ");
					temp=temp.replaceAll("\\{"," open curly bracket ");
					temp=temp.replaceAll("\\}"," close curly bracket ");
					temp=temp.replaceAll("\\["," open square bracket ");
					temp=temp.replaceAll("\\]"," close square bracket ");
					temp=temp.replaceAll("\""," double inverted comma ");
					temp=temp.replaceAll("/"," slash ");
					temp=temp.replaceAll("\\*"," asterisk ");
					a[n-1]=temp;
				}
				tv.setText("Dictating");
				speak();
			}
        });
        dicp.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				speed=speed+0.1f;
				tts.setSpeechRate(speed);
			}
		});
        dicm.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				speed=speed-0.1f;
				tts.setSpeechRate(speed);
			}
		});
        control.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				control.setVisibility(View.GONE);
				drawer.setVisibility(View.VISIBLE);
				pause.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						tv.setText("Paused");
						stop=1;
						tts.stop();
						drawer.setVisibility(View.GONE);
						control.setVisibility(View.VISIBLE);
					}
				});
				rev.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						drawer.setVisibility(View.GONE);
						control.setVisibility(View.VISIBLE);
					}
				});
				fwd.setOnClickListener(new View.OnClickListener() {
	
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						drawer.setVisibility(View.GONE);
						control.setVisibility(View.VISIBLE);
					}
				});
			}
		});
    }
    private void speak()
    {
    	//runOnUiThread(new Runnable()
    	//{
			//@Override
			//public void run()
			//{
				HashMap<String,String> map=new HashMap<String,String>();
				map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"STRING");
					if(pos<n && stop==0)
					{
						if(pos==0)
						{
							tts.speak(a[pos],TextToSpeech.QUEUE_FLUSH,map);
							display=+(pos+1)+": "+d[pos]+"\n";
						}
						else
						{
							tts.speak(a[pos],TextToSpeech.QUEUE_ADD,map);
							display=display+(pos+1)+": "+d[pos]+"\n";
						}
						tts.playSilence(sec,TextToSpeech.QUEUE_ADD,map);
						text.setText("~\n"+display);
						pos++;
						speak();
					}
			//}
		//});
	}
    private void pages()
    {
    	switch(page)
    	{
    	case 2:
    		ll.setBackgroundResource(R.drawable.page2);
    		break;
    	case 3:
    		ll.setBackgroundResource(R.drawable.page3);
    		break;
    	case 4:
    		ll.setBackgroundResource(R.drawable.page4);
    		break;
    	case 5:
    		ll.setBackgroundResource(R.drawable.page5);
    		break;
    	case 6:
    		ll.setBackgroundResource(R.drawable.page6);
    		break;
    	case 7:
    		ll.setBackgroundResource(R.drawable.page7);
    		break;
    	case 8:
    		ll.setBackgroundResource(R.drawable.page8);
    		break;
    	default:
    		ll.setBackgroundResource(R.drawable.home1);
    	}
    }
}
