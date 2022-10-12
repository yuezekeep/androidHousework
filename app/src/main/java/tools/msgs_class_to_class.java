package tools;


import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

public class msgs_class_to_class {
   public static Intent sned_msgs(Activity msgs_from_activity, Class msgs_to_activity, String msgs_name, String msgs){
      Intent intent = new Intent(msgs_from_activity,msgs_to_activity);
      intent.putExtra(msgs_name,msgs);
      return intent;
   }
   public static void get_msgs(Intent intent,String msgs_name, EditText text){
      String msgs = intent.getStringExtra(msgs_name);
      if(msgs!=null) {
         text.setText(msgs);
      }
   }
}
