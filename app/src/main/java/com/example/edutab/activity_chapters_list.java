package com.example.edutab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class activity_chapters_list extends AppCompatActivity implements
        TextToSpeech.OnInitListener{
        RecyclerView recyclerView;
        TextView textView;
        prefmanager pref;
        Toolbar toolbar;
        WebView webview;
        ImageView banner;
    private static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_chapters);
         pref = new prefmanager(this);
        tts = new TextToSpeech(this, this);
         textView = findViewById(R.id.title);
        webview = findViewById(R.id.webview);
         toolbar = findViewById(R.id.toolbar);
         banner = findViewById(R.id.banner);
        setSupportActionBar(toolbar);

        Log.d("pref",pref.getKeyPrimaryLocale()+"");

        String subject  = getIntent().getStringExtra("subject");

        setText();

        if (subject != null && subject.equals("maths")) {
            String[] chapters = new String[1000];
            if(pref.getKeyPrimaryLocale().equals("hi"))
            {
                chapters = new String[]{"अपनी संख्याओं की जानकारी", "पूर्ण संख्याएँ", "संख्याओं के साथ खेलना", "आधारभूत ज्यामितीय अवधारणाएं", "प्रारंभिक आकारों को समझना", "पूर्णांक", "भिन्न", "दशमलव", "आंकड़ों का प्रबंधन", "क्षेत्रमिति", "बीजगणित", "अनुपात और समानुपात ", "सममिति", "प्रायोगिक ज्यामिति"};
            }
            else {
                chapters = new String[]{"Knowing Our Numbers", "Whole Numbers", "Playing with Numbers", "Basic Geometrical Ideas", "Understanding Elementary Shapes", "Integers", "Fractions", "Decimals", "Data Handling", "Mensuration", "Algebra", "Ratio and Proportion", "Symmetry", "Practical Geometry"};
            }
            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

        if (subject != null && subject.equals("geo")) {
            String[] chapters = new String[1000];
            if(pref.getKeyPrimaryLocale().equals("hi"))
            {
                chapters = new String[]{"सौरमंडल से पृथ्वी", "ग्लोब: अक्षांश एवं देशांतर", "पृथ्वी की गतियां", "मानचित्र", "पृथ्वी के प्रमुख परिमंडल", " पृथ्वी के प्रमुख स्थलमंडल", "हमारा देश: भारत", " भारत: जलवायु, वसस्पति तथा वन्य प्राणी"};
            }
            else {
                chapters = new String[]{"The Earth In the Solar System", "Globe Latitudes and Longitudes", "Motions of the Earth", "Maps", "Major Domains of the Earth", "Major Landforms of the Earth", "Our Country India", "India Climate Vegetation and Wildlife"};
            }

            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

        if (subject != null && subject.equals("his")) {
            String[] chapters = new String[1000];
            if(pref.getKeyPrimaryLocale().equals("hi"))
            {
                chapters = new String[]{"क्या, कब, कहाँ और कैसे?","आरंभिक मानव की खोज में", "भोजन संग्रह से उत्पादन तक", " आरंभिक नगर", "क्या बताती हैं हमें किताबें और कब्रें", " राज्य, राजा और एक प्राचीन गणराज्य", " नए प्रश्न नए विचार", "अशोक: एक अनोखा सम्राट जिसने युद्ध का त्याग किया", " खुशहाल गावँ और समृद्ध शहर", "व्यापारी, राजा और तीर्थयात्री", "नए साम्राज्य और राज्य", "इमारतें, चित्र तथा किताबें"};
            }
            else {
                chapters = new String[]{"WHAT, WHERE, HOW AND WHEN?","ON THE TRAIL OF THE EARLIEST PEOPLE", "FROM GATHERING TO GROWING FOOD","IN THE EARLIEST CITIES", "WHAT BOOKS AND BURIALS TELL US", "KINGDOMS, KINGS AND AN EARLY REPUBLIC", "NEW QUESTIONS AND IDEAS", "ASHOKA, THE EMPEROR WHO GAVE UP WAR", "VITAL VILLAGES, THRIVING TOWNS", "TRADERS, KINGS AND PILGRIMS", "NEW EMPIRES AND KINGDOMS", "BUILDINGS, PAINTINGS AND BOOKS"};
            }
            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

    if (subject != null && subject.equals("pol")) {
        String[] chapters = new String[1000];
        if(pref.getKeyPrimaryLocale().equals("hi"))
        {
            chapters = new String[]{"विविधता की समझ", "विविधता एवं भेदभाव", "सरकार क्या है?", "लोकतांत्रिक सरकार के मुख्य तत्व", "पंचायती राज", " गाँव का प्रशासन", "नगर प्रशासन", "ग्रामीण क्षेत्र में आजीविका", "शहरी क्षेत्र में आजीविका"};
        }
        else {
            chapters = new String[]{"Understanding Diversity", "Diversity and Discrimination", "What is Government?", "Key Elements of a Democratic Government", "Panchayati Raj", "Rural Administration", "Urban Administration", "Rural Livelihoods", "Urban Livelihoods"};
        }

        ArrayList<String> chaptersList = new ArrayList();
        chaptersList.addAll(Arrays.asList(chapters));

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
        recyclerView.setAdapter(adapter);
    }
        if (subject != null && subject.equals("sci")) {
            String[] chapters = new String[1000];
            if(pref.getKeyPrimaryLocale().equals("hi"))
            {
                chapters = new String[]{"भोजन: यह कहाँ से आता है?", "भोजन के घटक", "तंतु से वस्त्र तक", "वस्तुओं के समूह बनाना", "पदार्थो का पृथक्करण", "हमारे चारों ओर के परिवर्तन", "पौधो को जानिए", "शरीर में गति", "सजीव एवं उनका परिवेश", "गति एवं दूरिओं का मापन", "प्रकाश – छायाएँ एवं परावर्तन", "विधुत तथा परिपथ", "चुंबकों द्वारा मनोरंजन", "जल", "हमारे चारों ओर वायु", "कचरा – संग्रहण एवं निपटान"};
            }
            else {
                chapters = new String[]{"Food: Where Does It Come From?", "Components of Food", "Fibre to Fabric", "Sorting Materials into Groups", "Separation of Substances", "Changes Around Us", "Getting to Know Plants", "Body Movement", "The Living Organisms Characteristics", "Motion and Measurement of Distances", "Light, Shadows and Reflections", "Electricity and Circuits", "Fun with Magnets", "Water", "Air Around Us", "Garbage In Garbage Out"};
            }
            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }


        if (subject != null && subject.equals("eng")) {
            String[] chapters = {"A Tale of Two Birds", "The Friendly Mongoose", "The Shepherd Treasure", "The Old-Clock Shop", "Tansen", "The Monkey and the Crocodile", "The Wonder Called Sleep", "A Pact with the Sun","What Happened to the Reptiles","A Strange Wrestling Match","Who Did Patrick’s Homework? & A House, A Home","How the Dog Found Himself a New Master! & The Kite","Taro’s Reward & The Quarrel","An Indian – American Woman in Space: Kalpana Chawla & Beauty","A Different Kind of School & Where Do All the Teachers Go?","Who I Am & The Wonderful Words","Fair Play","A Game of Chance & Vocation","Desert Animals & What If","The Banyan Tree"};
            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

        if (subject != null && subject.equals("sans")) {
            String[] chapters = {"अकारान्त-नपुंसकलिङ्ग:", "अकारान्त-पुँल्लिङ्ग:", "अङ्गुलीयकं प्राप्तम् (पञ्चमी-षष्ठीविभक्तिः)", "अहह आः च", "आकारान्त-स्त्रीलिङ्ग:", "कृषिकाः कर्मवीराः", "क्रीडास्पर्धा (सर्वनामप्रयोगः)", "दशमः त्वम् असि (संख्यावाचिपदानि)","पुष्पोत्सवः (सप्तमी-विभक्तिः)","बकस्य प्रतीकारः (अव्ययप्रयोगः)","मातुलचन्द्र!! (बालगीतम्)","लोकमङ्गलम्","वृक्षाः","समुद्रतटः (तृतीया-चतुर्थीविभक्तिः)","सूक्तिस्तबकः"};

            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

        if (subject != null && subject.equals("hin")) {
            String[] chapters ={"अवधपुरी में राम", "जंगल और जनकपुर", "दो वरदान", "राम का वन गमन", "चित्रकूट में भरत", "दंडक वन में दस वर्ष", "सोने का हिरन", "सीता की खोज","राम और सुग्रीव","लंका में हनुमान","लंका विजय","राम का राज्याभिषेक","कलम","किताब","घर","पतंग","भालू","झरना","धनुष","रुमाल","कक्षा","गुब्बारा","पर्वत","हमारा घर","कपडे की दूकान","फूल","बातचीत","शिलॉन्ग से फ़ोन","तितली","ईश्वरचंद्र विद्यासागर","प्रदर्शनी","चिट्ठी","अंगुलिमाल","यात्रा की तैयारी","हाथी","डॉक्टर","जयपुर से पत्र","बढे चलो","ब्यर्थ की शंका","गधा और सियार","वह चिड़िया जो","बचपन"," नादान दोस्त","चाँद से थोड़ी सी गप्पें","अक्षरों का महत्व","पार नज़र के","साथी हाथ बढ़ाना","ऐसे – ऐसे","टिकट एल्बम","झांसी की रानी","जो देखकर भी नहीं देखते","संसार पुस्तक है","मैं सबसे छोटी होऊं","लोकगीत","नौकर","वन के मार्ग में","साँस – साँस में बांस"};
            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }
        if (subject != null && subject.equals("eng_grammar")) {
            String[] chapters = {"Sentences","Parts of speech","Noun","Tenses","Articles","Comparisons","Modals","Verbs","Prepositions","Voices"};

            ArrayList<String> chaptersList = new ArrayList();
            chaptersList.addAll(Arrays.asList(chapters));

            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            chapterListAdapter adapter = new chapterListAdapter(chaptersList, this);
            recyclerView.setAdapter(adapter);
        }

        if (subject != null && subject.equals("gk")) {
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);

            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("file:///android_asset/home.html");
            //setContentView(view);
        }
}

    @Override
    protected void onResume() {
        super.onResume();
        setText();
    }

    private void setText() {
        if(pref.getKeyPrimaryLocale().equals("hi")){
            if(pref.getKeyPrimarySubject().equals("maths")){
                textView.setText("गणित");
                banner.setImageResource(R.drawable.ic_maths_banner);
            }
            if(pref.getKeyPrimarySubject().equals("geo")){
                textView.setText("भूगोल");
                banner.setImageResource(R.drawable.ic_geo_banner);
            }
            if(pref.getKeyPrimarySubject().equals("his")){
                textView.setText("इतिहास");
                banner.setImageResource(R.drawable.ic_history_banner);
            }
            if(pref.getKeyPrimarySubject().equals("pol")){
                textView.setText("राजनीति विज्ञान");
                banner.setImageResource(R.drawable.ic_pol_banner);
            }
            if(pref.getKeyPrimarySubject().equals("sci")){
                textView.setText("विज्ञान");
                banner.setImageResource(R.drawable.ic_sci_banner);
            }
            if(pref.getKeyPrimarySubject().equals("eng")){
                textView.setText("अंग्रेज़ी");
                banner.setImageResource(R.drawable.ic_english_banner);
            }
            if(pref.getKeyPrimarySubject().equals("sans")){
                textView.setText("संस्कृत");
                banner.setImageResource(R.drawable.ic_sanskrit_banner);
            }
            if(pref.getKeyPrimarySubject().equals("hin")){
                textView.setText("हिन्दी");
                banner.setImageResource(R.drawable.ic_hindi_banner);
            }
            if(pref.getKeyPrimarySubject().equals("gk")){
                textView.setText("सामान्य ज्ञान");
                banner.setImageResource(R.drawable.ic_gk_banner);
            }
            if(pref.getKeyPrimarySubject().equals("eng_grammar")){
                textView.setText("अंग्रेज़ी व्याकरण");
                banner.setImageResource(R.drawable.ic_grammer_banner);
            }
        }

        if(pref.getKeyPrimaryLocale().equals("en")){
            if(pref.getKeyPrimarySubject().equals("maths")){
                textView.setText("Maths");
                banner.setImageResource(R.drawable.ic_maths_banner);
            }
            if(pref.getKeyPrimarySubject().equals("geo")){
                textView.setText("Geography");
                banner.setImageResource(R.drawable.ic_geo_banner);
            }
            if(pref.getKeyPrimarySubject().equals("his")){
                textView.setText("History");
                banner.setImageResource(R.drawable.ic_history_banner);
            }
            if(pref.getKeyPrimarySubject().equals("pol")){
                textView.setText("Political Science");
                banner.setImageResource(R.drawable.ic_pol_banner);
            }
            if(pref.getKeyPrimarySubject().equals("sci")){
                textView.setText("Science");
                banner.setImageResource(R.drawable.ic_sci_banner);
            }
            if(pref.getKeyPrimarySubject().equals("eng")){
                textView.setText("English");
                banner.setImageResource(R.drawable.ic_english_banner);
            }
            if(pref.getKeyPrimarySubject().equals("sans")){
                textView.setText("Sanskrit");
                banner.setImageResource(R.drawable.ic_sanskrit_banner);
            }
            if(pref.getKeyPrimarySubject().equals("hin")){
                textView.setText("Hindi");
                banner.setImageResource(R.drawable.ic_hindi_banner);
            }
            if(pref.getKeyPrimarySubject().equals("gk")){
                textView.setText("General Knowledge");
                banner.setImageResource(R.drawable.ic_gk_banner);
            }
            if(pref.getKeyPrimarySubject().equals("eng_grammar")){
                textView.setText("English Grammar");
                banner.setImageResource(R.drawable.ic_grammer_banner);
            }
        }
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("hi","IN"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("");
            }

        } else { Log.e("TTS", "Initilization Failed!");}

    }

    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public static void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}
