package com.example.caleb.mineseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView Text1= (TextView) findViewById(R.id.hyperText);
        Text1.setText(
                Html.fromHtml(
                        "<a href=\"http:http://www.cs.sfu.ca/CourseCentral/276/bfraser/index.html\">homePage</a> "));
        Text1.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
