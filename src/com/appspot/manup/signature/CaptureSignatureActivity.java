package com.appspot.manup.signature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public final class CaptureSignatureActivity extends Activity
{
    private static final String TAG = CaptureSignatureActivity.class.getSimpleName();

    private static final int BITMAP_FILE_QUALITY = 100;

    private static final int MENU_SUBMIT = Menu.FIRST;
    private static final int MENU_CLEAR = Menu.FIRST + 1;
    private static final int MENU_SETTINGS = Menu.FIRST + 2;

    private final class MyView extends View
    {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;

        public MyView(Context c)
        {
            super(c);

            mClearCanvas = true;
            mBitmap = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            super.onSizeChanged(w, h, oldw, oldh);
            mCanvas.setBitmap(mBitmap = Bitmap.createBitmap(w, h,
                    Bitmap.Config.ARGB_8888));
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y)
        {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y)
        {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
            {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up()
        {
            mPath.lineTo(mX, mY);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
            mClearCanvas = false;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

        public Bitmap getBitMap()
        {
            return mBitmap;
        }

    } // MyView

    private final class WriteToExternalStorage extends AsyncTask<Object, Void, Long>
    {
        @Override
        protected Long doInBackground(final Object... params)
        {
            final long id = (Long) params[0];
            final SignatureDatabase dataHelper = SignatureDatabase
                    .getInstance(CaptureSignatureActivity.this);
            final File imageFile = dataHelper.getImageFile(id);
            if (imageFile == null)
            {
                Log.w(TAG, "Image file cannot be retrieved from database");
                return null;
            } // if
            try
            {
                imageFile.createNewFile();
            } // try
            catch (final IOException e)
            {
                Log.w(TAG, "Error creating file", e);
                return null;
            } // catch
            final Bitmap b = (Bitmap) params[1];
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(imageFile);
                if (!b.compress(Bitmap.CompressFormat.PNG, BITMAP_FILE_QUALITY, fos))
                {
                    return null;
                } // if
            } // try
            catch (final IOException e)
            {
                Log.w(TAG, "Error writing file", e);
                return null;
            } // catch
            finally
            {
                if (fos != null)
                {
                    try
                    {
                        fos.close();
                    } // try
                    catch (final IOException e)
                    {
                        Log.w(TAG, "Error closing output stream", e);
                    } // catch
                } // if
            } // finally
            if (dataHelper.signatureCaptured(id))
            {
                return id;
            } // if
            return null;
        } // doInBackground

        @Override
        protected void onPostExecute(final Long id)
        {
            if (id != null)
            {
                setContentView(myView = new MyView(CaptureSignatureActivity.this));
            } // if
            else
            {
                Toast.makeText(CaptureSignatureActivity.this, "Write failed", Toast.LENGTH_SHORT)
                        .show();
            } // else
        } // onPostExecute

    } // WriteToExternalStorage

    private boolean mClearCanvas;
    private MyView myView;
    private Paint mPaint;
    // TODO: Replace fake student ID generation.
    private long studentId = System.currentTimeMillis();

    public CaptureSignatureActivity()
    {
        super();
    } // CaptureSignatureActivity

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(myView = new MyView(this));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
    } // onCreate

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_SUBMIT, 0, "Submit").setShortcut('7', 's');
        menu.add(0, MENU_CLEAR, 0, "Clear").setShortcut('3', 'c');
        menu.add(0, MENU_SETTINGS, 0, "Settings");
        return true;
    } // onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId())
        {
            case MENU_SUBMIT:
                onSubmit();
                return true;
            case MENU_CLEAR:
                setContentView(myView = new MyView(this));
                return true;
            case MENU_SETTINGS:
                startActivity(new Intent(this, SignaturePreferenceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

    private void onSubmit()
    {
        if (mClearCanvas)
        {
            Toast.makeText(this, "A signature is required", Toast.LENGTH_SHORT).show();
            return;
        } // if
        final SignatureDatabase dataHelper = SignatureDatabase.getInstance(this);
        // TODO: Replace fake student ID generation.
        final long id = dataHelper.addSignature(Long.toString(studentId++));
        new WriteToExternalStorage().execute(id, myView.getBitMap());
    } // onSubmit

} // CaptureSignatureActivity

