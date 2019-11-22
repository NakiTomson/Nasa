package com.example.dmiryz.nasa.layouts

import android.Manifest
import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.davemorrissey.labs.subscaleview.ImageSource
import com.example.dmiryz.nasa.R
import com.google.android.material.snackbar.Snackbar
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.activity_photo_acivity.*
import java.io.IOException


class PhotoAcivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_WRITE_STORAGE = 3333
    private val EXTRA_URL = "EXTRA_URL"


    private var isToolbarVisible = false

    private var photo: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_acivity)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.title = "";
        setSupportActionBar(toolbar)


        ImageLoader.getInstance().loadImage(
            getIntent().getStringExtra(EXTRA_URL),
            object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(
                    imageUri: String?,
                    view: View?,
                    loadedImage: Bitmap
                ) {
                    if (!isFinishing) {
                        photo = loadedImage
                        image.setImage(ImageSource.cachedBitmap(loadedImage))
                        findViewById<View>(R.id.progress).visibility = View.GONE
                    }
                }
            })

        toolbar.post {
            if (!isFinishing) {
                hideActionBar()
            }
        }

        image.setOnClickListener { v: View? ->
            if (photo != null && !isToolbarVisible) {
                showActionBar()
            }
        }

        image.setOnTouchListener { view: View?, motionEvent: MotionEvent ->
            if (motionEvent.action != MotionEvent.ACTION_DOWN
                && motionEvent.action != MotionEvent.ACTION_UP
            ) {
                if (isToolbarVisible) {
                    hideActionBar()
                }
            }
            false
        }
    }


    private fun hideActionBar() {
        toolbar.animate().translationY((-toolbar.height).toFloat()).setDuration(300).start()
        isToolbarVisible = false
    }

    private fun showActionBar() {
        toolbar.animate().translationY(0F).setDuration(300).start()
        isToolbarVisible = true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_photo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_set_wallpaper -> {
                setWallpaper()
                hideActionBar()
                return true
            }
            R.id.action_share -> {
                performSharing()
                hideActionBar()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun performSharing() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val path = MediaStore.Images.Media.insertImage(
                contentResolver,
                photo,
                intent.getStringExtra(EXTRA_URL),
                ""
            )
            val uri: Uri = Uri.parse(path)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_WRITE_STORAGE
                )
            }
        }
    }

    private fun setWallpaper() {
        val wallpaperManager =
            WallpaperManager.getInstance(applicationContext)
        try {
            wallpaperManager.setBitmap(photo)
            Snackbar.make(
                image,
                getString(R.string.set_as_wallpaper_completed),
                Snackbar.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_WRITE_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performSharing()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
















