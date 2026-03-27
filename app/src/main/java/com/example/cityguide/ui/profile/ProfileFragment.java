package com.example.cityguide.ui.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cityguide.R;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    // Объявление элементов интерфейса
    private EditText etPhone, etPassword, etConfirmPassword;
    private Button btnLogin, btnRegister;
    private ImageView ivProfilePhoto;
    private LinearLayout userInfoLayout, inputLayout;
    private TextView tvPhone;

    private boolean isLoginMode = true;
    private Uri imageUri;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PERMISSION_REQUEST_CODE = 100;

    // 🔹 Встроенные аватары
    private int[] avatars = {
            R.drawable.ic_avatar_cat,
            R.drawable.ic_avatar_dog,
            R.drawable.ic_avatar_bird,
            R.drawable.ic_avatar_fish,
            R.drawable.ic_avatar_rabbit
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализация элементов
        etPhone = root.findViewById(R.id.etPhone);
        etPassword = root.findViewById(R.id.etPassword);
        etConfirmPassword = root.findViewById(R.id.etConfirmPassword);
        btnLogin = root.findViewById(R.id.btnLogin);
        btnRegister = root.findViewById(R.id.btnRegister);
        ivProfilePhoto = root.findViewById(R.id.ivProfilePhoto);
        userInfoLayout = root.findViewById(R.id.userInfoLayout);
        tvPhone = root.findViewById(R.id.tvPhone);
        inputLayout = root.findViewById(R.id.inputLayout);

        // Обработчик кнопки "ВОЙТИ"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginOrRegister();
            }
        });

        // Обработчик кнопки "ЗАРЕГИСТРИРОВАТЬСЯ"
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMode();
            }
        });

        // 🔹 КЛИК ПО ФОТО ПРОФИЛЯ - выбор между галереей, камерой и аватарами
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        return root;
    }

    // 🔹 Показ диалога выбора (галерея, камера или аватары)
    private void showImagePickerDialog() {
        String[] options = {"Выбрать из галереи", "Сделать фото", "Выбрать аватар"};

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Выберите действие")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openGallery();
                    } else if (which == 1) {
                        openCamera();
                    } else {
                        showAvatarPicker();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    // 🔹 Открытие галереи
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 🔹 Открытие камеры
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Запрос разрешения
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    // 🔹 Показ выбора аватаров
    private void showAvatarPicker() {
        String[] avatarNames = {"🐱 Котик", "🐶 Собака", "🐦 Птичка", "🐟 Рыбка", "🐰 Кролик"};

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Выберите аватар")
                .setItems(avatarNames, (dialog, which) -> {
                    // Устанавливаем выбранный аватар
                    ivProfilePhoto.setImageResource(avatars[which]);
                    ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Toast.makeText(getContext(), "Аватар установлен!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    // 🔹 Обработка результата выбора фото
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null) {
                // Выбор из галереи
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            getContext().getContentResolver(), imageUri);
                    ivProfilePhoto.setImageBitmap(bitmap);
                    ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ошибка загрузки фото", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_REQUEST && data != null) {
                // Фото с камеры
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivProfilePhoto.setImageBitmap(bitmap);
                ivProfilePhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
    }

    // 🔹 Обработка разрешений
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Нужно разрешение на камеру", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Обработка входа или регистрации
    private void handleLoginOrRegister() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (phone.isEmpty()) {
            etPhone.setError("Введите номер телефона");
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Введите пароль");
            etPassword.requestFocus();
            return;
        }

        if (isLoginMode) {
            // РЕЖИМ ВХОДА
            if (validateLogin(phone, password)) {
                showProfile(phone);
                Toast.makeText(getContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Неверный номер или пароль", Toast.LENGTH_SHORT).show();
            }
        } else {
            // РЕЖИМ РЕГИСТРАЦИИ
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (confirmPassword.isEmpty()) {
                etConfirmPassword.setError("Подтвердите пароль");
                etConfirmPassword.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getContext(), "Пароль должен быть не менее 6 символов", Toast.LENGTH_SHORT).show();
                return;
            }

            if (registerUser(phone, password)) {
                Toast.makeText(getContext(), "Регистрация успешна! Теперь войдите", Toast.LENGTH_SHORT).show();
                etPhone.setText("");
                etPassword.setText("");
                etConfirmPassword.setText("");
                toggleMode();
            } else {
                Toast.makeText(getContext(), "Пользователь уже существует", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Переключение между входом и регистрацией
    private void toggleMode() {
        isLoginMode = !isLoginMode;

        if (isLoginMode) {
            btnRegister.setText("ЗАРЕГИСТРИРОВАТЬСЯ");
            btnLogin.setText("ВОЙТИ");
            etConfirmPassword.setVisibility(View.GONE);
        } else {
            btnRegister.setText("УЖЕ ЕСТЬ АККАУНТ? ВОЙТИ");
            btnLogin.setText("ЗАРЕГИСТРИРОВАТЬСЯ");
            etConfirmPassword.setVisibility(View.VISIBLE);
        }

        etPhone.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }

    // Показ профиля после успешного входа
    private void showProfile(String phone) {
        if (inputLayout != null) {
            inputLayout.setVisibility(View.GONE);
        } else {
            etPhone.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            etConfirmPassword.setVisibility(View.GONE);
        }

        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);

        userInfoLayout.setVisibility(View.VISIBLE);
        ivProfilePhoto.setVisibility(View.VISIBLE);
        tvPhone.setText("Номер: " + phone);
    }

    private boolean validateLogin(String phone, String password) {
        return phone.length() >= 10 && password.length() >= 6;
    }

    private boolean registerUser(String phone, String password) {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}