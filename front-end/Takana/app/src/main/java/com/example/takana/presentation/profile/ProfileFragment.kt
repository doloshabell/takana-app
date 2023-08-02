package com.example.takana.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.takana.MainActivity
import com.example.takana.R
import com.example.takana.data.model.response.BaseResponse
import com.example.takana.data.model.response.GetDetailProfileResponse
import com.example.takana.data.model.response.LoginResponse
import com.example.takana.data.util.SPAllAccount
import com.example.takana.data.util.SessionManager
import com.example.takana.data.util.UserToken
import com.example.takana.databinding.FragmentProfileBinding
import com.example.takana.presentation.transaction.TransactionViewModel
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    private val viewModelTransaction by viewModels<TransactionViewModel>()
    lateinit var token: String
    lateinit var user: UserToken.User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        token = SessionManager.getToken(requireContext()).toString()
        user = UserToken.getObjectFromSharedPreferences(requireContext())!!
        viewModel.getDetailProfile(token, user.userId!!.toLong())
        viewModelGetDetailProfile()
    }

    private fun setupContent() {
        val user: UserToken.User? = UserToken.getObjectFromSharedPreferences(requireContext())
        binding.apply {
            tvEditProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                startActivity(intent)
            }

            tvLogout.setOnClickListener {
                viewModel.logOutUser(token)
                viewModelLogOut()
            }

            tvExportData.setOnClickListener {
                viewModelTransaction.downloadPdf(token)
                downloadDataPdf()
            }
        }
    }

    private fun viewModelLogOut() {
        viewModel.logOutResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processLogOut(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun processLogOut(data: LoginResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        SessionManager.clearDataSession(requireContext())
        SPAllAccount.clearDataAccount(requireContext())
        UserToken.clearDataUserToken(requireContext())
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    fun processError(msg: String?) {
        showToast(msg.toString())
        stopLoading()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun viewModelGetDetailProfile() {
        viewModel.getDetailProfileResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processGetDetail(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun processGetDetail(data: GetDetailProfileResponse?) {
        showToast(data?.message.toString())
        stopLoading()
        binding.apply {
            tvFullName.text = getString(R.string.one_string, data?.data?.fullName)
            tvUsername.text = getString(R.string.username_x, data?.data?.username)
        }
    }

    private fun downloadDataPdf() {
        viewModelTransaction.downloadPdfResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    processDownloadData(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }

                else -> {
                    stopLoading()
                }
            }
        }
    }

    private fun processDownloadData(data: ResponseBody?) {
        showToast("Success")
        stopLoading()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        savePdfToInterbal(data!!, "report_takana_file".plus(day).plus(month).plus(year).plus(".pdf"))
    }

    private fun savePdfToInterbal(body: ResponseBody, fileName: String) {
        try {
            val inputStream = body.byteStream()

            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)

            val fileOutputStream = FileOutputStream(file)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                fileOutputStream.write(buffer, 0, bytesRead)
            }
            fileOutputStream.close()
            inputStream.close()
            Toast.makeText(
                requireContext(),
                "File berhasil disimpan : $fileName",
                Toast.LENGTH_SHORT
            )
                .show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Gagal Menyimpan file", Toast.LENGTH_SHORT).show()
        }
    }
}