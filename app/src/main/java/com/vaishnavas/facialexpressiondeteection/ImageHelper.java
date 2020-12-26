package com.vaishnavas.facialexpressiondeteection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

import static com.vaishnavas.facialexpressiondeteection.MainActivity.size_of_faces;


public class ImageHelper {
    public static Bitmap drawRectOnBitmap(Context context, Face thisface, Bitmap mbitmap){
     String expression1="";
        String expression2="";
        String expression3="";
        // getting cordinates..
        float topX = thisface.getPosition().x;
        float topY = thisface.getPosition().y;
        float width =  thisface.getWidth();
        float height =  thisface.getHeight();
        Bitmap bitmap  = mbitmap.copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        // below line draw a rectangle on the face area of the image
        canvas.drawRect(topX,topY,topX+width,topY+height,paint);
        float X = topX + width;
        float Y = topY + height;
      // getting expressions of the face ....
      //  Toast.makeText(context,"" +thisface.getIsSmilingProbability(),Toast.LENGTH_LONG).show();
      if(size_of_faces==1) {
          if (thisface.getIsSmilingProbability() >= 0.60) {
              expression1 = "Smiling!";
          } else if (thisface.getIsSmilingProbability() < 0.60 && thisface.getIsSmilingProbability() >= 0.40) {
              expression1 = "Cute and Small Smile!";
          } else if (thisface.getIsSmilingProbability() == -1.0) {
              expression1 = "owww, you are so cute...";
          } else {
              expression1 = "not smily!";
          }
          float y = thisface.getEulerY();
          float z = thisface.getEulerZ();
          if (y > 10.00 && y < 25.00) {
              expression2 = "looking slidly right..";
          } else if (y > 25.00) {
              expression2 =  "looking towards right..";
          } else if (y > -25.00 && y < -10.00) {
              expression2 =  "looking slidly left..";
          } else if (y < -25.00) {
              expression2 =  "looking towards left..";
          } else {
              expression2 =  "\n" + "looking straight..";
          }
          if (z > 10.00 && z < 25.00) {
              expression3 = "\n" + "neck is slidly down towards left";
          } else if (z > 25.00) {
              expression3 =  "\n" + "neck is down towards left..";
          } else if (z > -25.00 && z < -1.00) {
              expression3 = "\n" + "neck is slidly down towards right..";
          } else if (z < -25.00) {
              expression3 =  "\n" + "neck is down towards right..";
          }
      }
       // Toast.makeText(context,"" +y + "and" +z, Toast.LENGTH_LONG).show();
        drawTextOnBitmap(canvas,10,X/50,Y+10,Color.GREEN,expression1,expression2,expression3);
       // printing circles on landmarks ,like eye,nose,mouth etc..
        for (Landmark landmark : thisface.getLandmarks()) {
            int cx = (int) (landmark.getPosition().x);
            int cy = (int) (landmark.getPosition().y);
            canvas.drawCircle(cx, cy, (float) 0.3, paint);
        }

        return bitmap;
    }
    // this method is for writing about the expression with the image......
    public static void drawTextOnBitmap(Canvas canvas,int textSize,float x, float y,int color,String expression1,String expression2,String expression3){
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    paint.setColor(color);
    paint.setTextSize(textSize);
    canvas.drawText(expression1,x,y,paint); // writing expression info
        canvas.drawText(expression2,x,y+8,paint); // writing expression info
        canvas.drawText(expression3,x,y+16,paint); // writing expression info
    }
}
