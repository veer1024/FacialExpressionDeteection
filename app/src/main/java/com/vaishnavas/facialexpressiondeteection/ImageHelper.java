package com.vaishnavas.facialexpressiondeteection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;


public class ImageHelper {
    public static Bitmap drawRectOnBitmap(Context context, Face thisface, Bitmap mbitmap){
     String expression="";
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
        if(thisface.getIsSmilingProbability()>=0.60){
            expression = "Smiling!";
        }
        else if(thisface.getIsSmilingProbability()<0.60 && thisface.getIsSmilingProbability()>=0.40){
            expression = "Cute and Small Smile!";
        }
        else if(thisface.getIsSmilingProbability() == -1.0){
            expression = "owww, you are so cute...";
        }
        else
            {
            expression = "not smily!";
        }

        drawTextOnBitmap(canvas,10,X/2,Y+10,Color.GREEN,expression);
       // printing circles on landmarks ,like eye,nose,mouth etc..
        for (Landmark landmark : thisface.getLandmarks()) {
            int cx = (int) (landmark.getPosition().x);
            int cy = (int) (landmark.getPosition().y);
            canvas.drawCircle(cx, cy, 1, paint);
        }

        return bitmap;
    }
    // this method is for writing about the expression with the image......
    public static void drawTextOnBitmap(Canvas canvas,int textSize,float x, float y,int color,String expression){
    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL_AND_STROKE);
    paint.setAntiAlias(true);
    paint.setColor(color);
    paint.setTextSize(textSize);
    canvas.drawText(expression,x,y,paint); // writing expression info
    }
}
