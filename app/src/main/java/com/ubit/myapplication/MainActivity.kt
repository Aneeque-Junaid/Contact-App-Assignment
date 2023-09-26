package com.ubit.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.ubit.myapplication.databinding.ActivityMainBinding
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract.RawContacts.Data
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val phoneNo = 1;
    lateinit var contactUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val selectContacts = findViewById<Button>(R.id.contactButton)

        selectContacts.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE;
            startActivityForResult(intent, phoneNo)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == phoneNo && resultCode == Activity.RESULT_OK) {
            contactUri = data!!.data!!

            getPhoneNumber()
        }
    }
    @SuppressLint("Range")
    private fun getPhoneNumber() {
        val projection =
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)
        val cursor = contentResolver.query(contactUri, projection, null, null, null);

        cursor?.use {
            if (it.moveToFirst()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                val phoneNumber = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                val nameText = findViewById<TextView>(R.id.contactName)
                val phoneText = findViewById<TextView>(R.id.contactNumber)

                nameText.text = name
                phoneText.text = phoneNumber

            }
        }
    }

}